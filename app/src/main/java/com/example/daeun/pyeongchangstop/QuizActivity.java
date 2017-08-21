package com.example.daeun.pyeongchangstop;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class QuizActivity extends AppCompatActivity {
    private static final String PATH = "/data/data/com.example.daeun.pyeongchangstop/";
    ImageView shortButton, selectButton, oxButton;
    TextView chance;
    MySQLiteOpenHelper myDB;
    SQLiteDatabase db;
    Cursor cursor;
    int count, dbidate, current;
    String dbdate;
    MainActivity mainActivity = (MainActivity) MainActivity.mainActivity;
    public static Activity quizactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        quizactivity = QuizActivity.this;

        File folder = new File(PATH + "databases");
        folder.mkdirs();
        File outFile = new File(PATH + "databases/quiz.db");

        if (outFile.length() <= 0) {
            AssetManager am = getResources().getAssets();
            InputStream is = null;
            try {
                is = am.open("quiz.db", AssetManager.ACCESS_BUFFER);
                long filesize = is.available();
                byte[] tempdata = new byte[(int) filesize];
                is.read(tempdata);
                is.close();
                outFile.createNewFile();
                FileOutputStream fo = new FileOutputStream(outFile);
                fo.write(tempdata);
                fo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        myDB = new MySQLiteOpenHelper(getApplicationContext(), "pcSTOP.db", null, 1);
        db = myDB.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM datetable", null);
        cursor.moveToFirst();
        dbdate = cursor.getString(1);

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

        if (current > dbidate) { // 하루 지나면 초기화
            db.execSQL("update datetable set usedate=date('now','localtime'), usrquiz=5 where _id=1;");
        }

        count = cursor.getInt(4);
        chance = (TextView) findViewById(R.id.chanceText);
        chance.setText(count + "");

        // 퀴즈 버튼 선택
        shortButton = (ImageView) findViewById(R.id.shortButton);
        shortButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               if (count == 0) {
                                                   Toast.makeText(getApplicationContext(), "하루 퀴즈 도전 횟수를 모두 사용하셨습니다.", Toast.LENGTH_SHORT).show();
                                               } else {
                                                   db.execSQL("update datetable set usrquiz=" + (count - 1) + " where _id=1;");
                                                   Intent intent = new Intent(QuizActivity.this, ShortQuizActivity.class);
                                                   startActivity(intent);
                                               }
                                           }
                                       }

        );

        selectButton = (ImageView) findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (count == 0) {
                                                    Toast.makeText(getApplicationContext(), "하루 퀴즈 도전 횟수를 모두 사용하셨습니다.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    db.execSQL("update datetable set usrquiz=" + (count - 1) + " where _id=1;");
                                                    Intent intent = new Intent(QuizActivity.this, SelectionQuizActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        }

        );

        oxButton = (ImageView) findViewById(R.id.oxButton);
        oxButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (count == 0) {
                                                Toast.makeText(getApplicationContext(), "하루 퀴즈 도전 횟수를 모두 사용하셨습니다.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                db.execSQL("update datetable set usrquiz=" + (count - 1) + " where _id=1;");
                                                Intent intent = new Intent(QuizActivity.this, NoticeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }

        );
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        mainActivity.finish();
        Intent intent = new Intent(QuizActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
