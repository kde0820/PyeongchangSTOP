package com.example.daeun.pyeongchangstop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;

public class LockScreenActivity extends AppCompatActivity {
    SeekBar slidingButton;
    MySQLiteOpenHelper myDB;
    SQLiteDatabase db;
    Cursor ucursor;
    Cursor cursor;
    Cursor dcursor;
    TextView content, title, apptext;
    String dbdate;
    int dbidate;
    int current;
    int dbcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_lock_screen);

        myDB = new MySQLiteOpenHelper(getApplicationContext(), "pcSTOP.db", null, 1);
        db = myDB.getWritableDatabase();
        ucursor = db.rawQuery("SELECT * FROM usrtable", null); // 사용자 정보 가져오기
        ucursor.moveToFirst();

        // 잠금해제 하루 제한을 위한 변수 dbdate, dbcount 세팅
        dcursor = db.rawQuery("SELECT * FROM datetable", null); // 잠금해제 정보 가져오기
        dcursor.moveToFirst();
        dbdate = dcursor.getString(1);
        dbcount = dcursor.getInt(2);

        StringTokenizer tokenizer = new StringTokenizer(dbdate, "-");
        while (tokenizer.hasMoreTokens()) {
            dbdate = tokenizer.nextToken();
        }
        dbidate = Integer.parseInt(dbdate);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String getTime = sdf.format(date);
        current = Integer.parseInt(getTime);

        // 잠금화면 포인트 획득 횟수에 따라 text 내용 보여주기
        apptext = (TextView) findViewById(R.id.appText);
        if (dbcount == 5) {
            apptext.setText("앱 실행하기");
        } else {
            apptext.setText("+ 5point");
        }

        // 디비에 저장된 컨텐츠 표시
        cursor = db.rawQuery("SELECT * FROM locktable", null); // 잠금화면 표시 내용 가져오기
        Random random = new Random();
        cursor.moveToPosition(random.nextInt(16));
        title = (TextView) findViewById(R.id.titleText);
        content = (TextView) findViewById(R.id.contentText);
        title.setText(cursor.getString(1));
        content.setText(cursor.getString(2));


        // 잠금해제 슬라이드 구현
        slidingButton = (SeekBar) findViewById(R.id.slidingButton);
        slidingButton.setProgress(55);
        slidingButton.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() >= 90) { // 잠금해제
                    finish();
                } else if (seekBar.getProgress() <= 10) { // 앱 실행
                    if (current > dbidate) { // 하루 지나면 unlocktime 초기화
                        db.execSQL("update datetable set usedate=date('now','localtime'), unlocktime=0 where _id=1;");
                    }

                    Toast.makeText(getApplicationContext(), (dbcount + 1) + "", Toast.LENGTH_SHORT).show();

                    if (dbcount == 5) {
                        Toast.makeText(getApplicationContext(), "하루 제한 초과", Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues value = new ContentValues();
                        value.put("point", ucursor.getInt(6) + 5);
                        db.update("usrtable", value, "_id=?", new String[]{String.valueOf(1)}); // 5 포인트 증가시켜 디비 업데이트
                        db.execSQL("update datetable set usedate=date('now','localtime'), unlocktime=" + (dbcount + 1) + " where _id=1;");
                    }


                    Intent intent = new Intent(LockScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    seekBar.setProgress(55);
                }
            }
        });

        slidingButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    slidingButton.setProgress(55);
                }
                return false;
            }
        });

    }
}