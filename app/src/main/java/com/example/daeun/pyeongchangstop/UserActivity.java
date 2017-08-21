package com.example.daeun.pyeongchangstop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    TextView nametext, pointtext;
    Button logoutbutton;
    MySQLiteOpenHelper myDB;
    SQLiteDatabase db;
    Cursor lcursor, ucursor;
    int usrlogin;
    MainActivity mainActivity = (MainActivity) MainActivity.mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        myDB = new MySQLiteOpenHelper(getApplicationContext(), "pcSTOP.db", null, 1);
        db = myDB.getWritableDatabase();

        lcursor = db.rawQuery("SELECT * FROM datetable", null);
        lcursor.moveToFirst();
        usrlogin = lcursor.getInt(3); // 로그인 여부 판단

        ucursor = db.rawQuery("SELECT * FROM usrtable", null); // 사용자 정보 가져오기
        ucursor.moveToPosition(usrlogin);
        nametext = (TextView) findViewById(R.id.nameText);
        pointtext = (TextView) findViewById(R.id.pointText);
        nametext.setText(ucursor.getString(1));
        pointtext.setText("보유 포인트: " + ucursor.getInt(7) + "point");

        logoutbutton = (Button) findViewById(R.id.logoutButton);
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("update datetable set usrlogin=-1 where _id=1;"); // 로그아웃 상태로 바꿈
                mainActivity.finish();
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent); // 새로운 메인 엑티비티 시작
                finish();
            }
        });

    }
}
