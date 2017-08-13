package com.example.daeun.pyeongchangstop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText idText = (EditText) findViewById(R.id.idText);
        //디자인에 있는 아이디 텍스트안에 이름을 가지는 아이디값을 idText변수에 객체로 저장
        EditText passwordText = (EditText) findViewById(R.id.passwordText);
        Button loginButton = (Button) findViewById(R.id.loginbutton);
        Button registerButton = (Button) findViewById(R.id.registerButton);

        // 등록 버튼을 누르면, 레지스터액티비티로 감
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }
}
