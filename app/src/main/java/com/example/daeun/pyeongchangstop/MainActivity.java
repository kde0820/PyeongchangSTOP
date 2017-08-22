package com.example.daeun.pyeongchangstop;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView quizButton, timeButton, noticeButton, storeButton;
    TextView name, pointText, idText;
    Button loginbutton;
    MySQLiteOpenHelper myDB;
    SQLiteDatabase db;
    Cursor ucursor, lcursor;
    int usrlogin;
    long pressedTime;
    public static Activity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = MainActivity.this;
        pressedTime = 0;
        Util.setGlobalFont(this, getWindow().getDecorView());

        // 잠금화면 서비스
        Intent intent = new Intent(getApplicationContext(), ScreenService.class);
        startService(intent);

        // 디비 정보 가져오기
        myDB = new MySQLiteOpenHelper(getApplicationContext(), "pcSTOP.db", null, 1);
        db = myDB.getWritableDatabase();

        lcursor = db.rawQuery("SELECT * FROM datetable", null);
        lcursor.moveToFirst();
        usrlogin = lcursor.getInt(3); // 로그인 여부 판단

        ucursor = db.rawQuery("SELECT * FROM usrtable", null); // 사용자 정보 가져오기


        // 로그인 여부(usrlogin)에 따라 상단 버튼, 텍스트 내용 변경
        idText = (TextView) findViewById(R.id.idText);
        loginbutton = (Button) findViewById(R.id.loginButton);
        if (usrlogin == -1) { // 로그인 하지 않은 경우
            loginbutton.setText("로그인");
            loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            findViewById(R.id.idText).setVisibility(View.GONE);
            findViewById(R.id.ID).setVisibility(View.GONE);
            findViewById(R.id.POINT).setVisibility(View.GONE);
            findViewById(R.id.pointText).setVisibility(View.GONE);
        } else {
            loginbutton.setText("마이페이지");
            loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(intent);
                }
            });

            findViewById(R.id.loginText).setVisibility(View.GONE);
            ucursor.moveToPosition(usrlogin);
            idText.setText(ucursor.getString(1));

            pointText = (TextView) findViewById(R.id.pointText);
            pointText.setText("" + ucursor.getInt(7));
        }

        // 각 버튼을 누르면 화면이 넘어감.
        quizButton = (ImageView) findViewById(R.id.quizButton);
        quizButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (usrlogin == -1) {
                                                    Toast.makeText(getApplicationContext(), "로그인을 하셔야 사용가능한 서비스입니다.",Toast.LENGTH_SHORT).show();
                                              } else {
                                                  Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                                                  startActivity(intent);
                                              }
                                          }
                                      }
        );

        timeButton = (ImageView) findViewById(R.id.timeButton);
        timeButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent = new Intent(MainActivity.this, TimeActivity.class);
                                              startActivity(intent);
                                          }
                                      }
        );

        noticeButton = (ImageView) findViewById(R.id.noticeButton);
        noticeButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                                                startActivity(intent);
                                            }
                                        }

        );

        storeButton = (ImageView) findViewById(R.id.storeButton);
        storeButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent = new Intent(MainActivity.this, StoreActivity.class);
                                               startActivity(intent);
                                           }
                                       }
        );
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - pressedTime < 1500) {
            finish();
            return;
        }
        Toast.makeText(getApplicationContext(), "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        pressedTime = System.currentTimeMillis();
    }
}
