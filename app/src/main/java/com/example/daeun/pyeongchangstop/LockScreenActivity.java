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
    Cursor ucursor, cursor, dcursor;
    TextView content, title, unlockText;
    int dbidate, current, dbcount, usrlogin;
    String dbdate;


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
        dbidate = Integer.parseInt(dbdate); // DB에 저장된 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String getTime = sdf.format(date);
        current = Integer.parseInt(getTime); // 현재 날짜

        // 로그인 여부를 확인하기 위한 변수
        usrlogin = dcursor.getInt(3);

        // 잠금화면 포인트 획득 횟수에 따라 text 내용 보여주기
        unlockText = (TextView) findViewById(R.id.unlockText);
        if (dbcount == 5 || usrlogin == -1) { // 하루 제한을 넘었거나 로그인을 하지 않은경우
            unlockText.setText("잠금 해제");
        } else {
            unlockText.setText("+ 5point");
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
                    if (current > dbidate) { // 하루 지나면 unlocktime 초기화
                        db.execSQL("update datetable set usedate=date('now','localtime'), unlocktime=0 where _id=1;");
                    }
                    if (dbcount == 5) {
                        Toast.makeText(getApplicationContext(), "하루 제한 초과", Toast.LENGTH_SHORT).show();
                    } else if (usrlogin == -1) {
                        Toast.makeText(getApplicationContext(), "로그인 필요", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(getApplicationContext(), (dbcount + 1) + "", Toast.LENGTH_SHORT).show();
                        db.execSQL("update usrtable set point=" + (ucursor.getInt(7) + 5) + " where _id=" + (usrlogin + 1) + ";"); // 5 포인트 증가시켜 디비 업데이트
                        db.execSQL("update datetable set usedate=date('now','localtime'), unlocktime=" + (dbcount + 1) + " where _id=1;");
                        Toast.makeText(getApplicationContext(), "적립완료", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else if (seekBar.getProgress() <= 10) { // 앱 실행
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

    @Override
    public void onBackPressed() {
        return;
    }
}