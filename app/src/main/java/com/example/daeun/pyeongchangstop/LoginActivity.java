package com.example.daeun.pyeongchangstop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    MySQLiteOpenHelper myDB;
    SQLiteDatabase db;
    MainActivity mainActivity = (MainActivity) MainActivity.mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Util.setGlobalFont(this, getWindow().getDecorView());

        final EditText idText = (EditText) findViewById(R.id.usrid);
        //디자인에 있는 아이디 텍스트안에 이름을 가지는 아이디값을 idText변수에 객체로 저장
        final EditText passwordText = (EditText) findViewById(R.id.usrpass);
        Button loginButton = (Button) findViewById(R.id.loginbutton);
        Button registerButton = (Button) findViewById(R.id.registerButton);

        myDB = new MySQLiteOpenHelper(getApplicationContext(), "pcSTOP.db", null, 1);
        db = myDB.getWritableDatabase();


        // 등록 버튼을 누르면, 레지스터액티비티로 감
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        // 로그인 버튼 누르면 DB에 로그인 정보 저장
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int logIndex = myDB.select(idText.getText().toString(), passwordText.getText().toString());
//                Toast.makeText(getApplicationContext(), logIndex + "", Toast.LENGTH_SHORT).show();
                if (logIndex == -1) {
                    Toast.makeText(getApplicationContext(), "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }else if(logIndex == -2){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    db.execSQL("update datetable set usrlogin=" + (logIndex - 1) + " where _id=1;");
                    mainActivity.finish(); // 이전의 메인 엑티비티 종료
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent); // 새로운 메인 엑티비티 시작
                    finish();
                }
            }
        });
    }
}
