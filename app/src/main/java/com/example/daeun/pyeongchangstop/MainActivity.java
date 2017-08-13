package com.example.daeun.pyeongchangstop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView quizButton, timeButton, noticeButton, pointButton, facilityButton, userButton;
    MySQLiteOpenHelper myDB;
    SQLiteDatabase db;
    Cursor ucursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new MySQLiteOpenHelper(getApplicationContext(), "pcSTOP.db", null, 1);
        db = myDB.getWritableDatabase();
        ucursor = db.rawQuery("SELECT * FROM usrtable", null); // 사용자 정보 가져오기
        ucursor.moveToFirst();

        TextView name = (TextView) findViewById(R.id.usrName);
        name.setText(ucursor.getString(1));

        Intent intent = new Intent(getApplicationContext(), ScreenService.class);
        startService(intent); // 잠금화면 서비스 시작

        TextView textView = (TextView) findViewById(R.id.usrPoint);
        textView.setText("현재 포인트: " + ucursor.getInt(6));


        Button loginbutton = (Button) findViewById(R.id.loginpage);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // 각 버튼을 누르면 화면이 넘어감.
        quizButton = (ImageView) findViewById(R.id.quizButton);
        quizButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                                              startActivity(intent);
                                          }
                                      }
        );

        timeButton = (ImageView) findViewById(R.id.timeButton);
        timeButton.setOnClickListener(new View.OnClickListener(){
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent = new Intent(MainActivity.this, TimeActivity.class);
                                              startActivity(intent);
                                          }
                                      }
        );

        noticeButton = (ImageView) findViewById(R.id.noticeButton);
        noticeButton.setOnClickListener(new View.OnClickListener(){
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                                                startActivity(intent);
                                            }
                                        }

        );

        pointButton = (ImageView) findViewById(R.id.pointButton);
        pointButton.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent = new Intent(MainActivity.this, PointActivity.class);
                                               startActivity(intent);
                                           }
                                       }

        );

        facilityButton = (ImageView) findViewById(R.id.facilityButton);
        facilityButton.setOnClickListener(new View.OnClickListener(){
                                              @Override
                                              public void onClick(View v) {
                                                  Intent intent = new Intent(MainActivity.this, FacilityActivity.class);
                                                  startActivity(intent);
                                              }
                                          }
        );

        userButton = (ImageView) findViewById(R.id.userButton);
        userButton.setOnClickListener(new View.OnClickListener(){
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent = new Intent(MainActivity.this, UserActivity.class);
                                              startActivity(intent);
                                          }
                                      }
        );
        //

    }
}
