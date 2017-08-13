package com.example.daeun.pyeongchangstop;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private MySQLiteOpenHelper helper;
    String dbName = "st_file.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "SQLite"; // Log 에 사용할 tag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.idText);
        //디자인에 있는 아이디 텍스트안에 이름을 가지는 아이디값을  idText변수에 객체로 저장
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText passwordcheckText = (EditText) findViewById(R.id.passwordchackText);
        final EditText ageText = (EditText) findViewById(R.id.ageText);
        final EditText telText = (EditText) findViewById(R.id.telText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idText.getText().toString().length()==0){
                    Toast.makeText(RegisterActivity.this,"아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    idText.requestFocus();
                    return;
                }
                if(nameText.getText().toString().length()==0){
                    Toast.makeText(RegisterActivity.this,"이름을 입력하세요", Toast.LENGTH_SHORT).show();
                    nameText.requestFocus();
                    return;
                }
                if(passwordText.getText().toString().length()==0){
                    Toast.makeText(RegisterActivity.this,"비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    passwordText.requestFocus();
                    return;
                }
                if(passwordcheckText.getText().toString().length()==0){
                    Toast.makeText(RegisterActivity.this,"다시 한 번 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    passwordcheckText.requestFocus();
                    return;
                }
                if(!passwordText.getText().toString().equals(passwordcheckText.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    passwordText.setText("");
                    passwordcheckText.setText("");
                    passwordText.requestFocus();
                    return;
                }

                if(ageText.getText().toString().length()==0){
                    Toast.makeText(RegisterActivity.this,"나이를 입력하세요", Toast.LENGTH_SHORT).show();
                    ageText.requestFocus();
                    return;
                }
                if(telText.getText().toString().length()==0){
                    Toast.makeText(RegisterActivity.this,"전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    telText.requestFocus();
                    return;
                }
                if(emailText.getText().toString().length()==0){
                    Toast.makeText(RegisterActivity.this,"이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                    emailText.requestFocus();
                    return;
                }

                insert(nameText, passwordText, ageText, telText, emailText);
                Toast.makeText(getApplicationContext(),"insert",Toast.LENGTH_LONG).show();
            }
        });

        helper = new MySQLiteOpenHelper(
                this,  // 현재 화면의 제어권자
                dbName,// db 이름
                null,  // 커서팩토리-null : 표준커서가 사용됨
                dbVersion);       // 버전

        try {
//         // 데이터베이스 객체를 얻어오는 다른 간단한 방법
//         db = openOrCreateDatabase(dbName,  // 데이터베이스파일 이름
//                          Context.MODE_PRIVATE, // 파일 모드
//                          null);    // 커서 팩토리
//
//         String sql = "create table mytable(id integer primary key autoincrement, name text);";
//        db.execSQL(sql);

            db = helper.getWritableDatabase(); // 읽고 쓸수 있는 DB
            //db = helper.getReadableDatabase(); // 읽기 전용 DB select문
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(tag, "데이터베이스를 얻어올 수 없음");
            finish(); // 액티비티 종료
        }

        //insert (); // insert 문 - 삽입추가

        //select(); // select 문 - 조회

        //update(); // update 문 - 수정변경

        //delete(); // delete 문 - 삭제 행제거

        //select();

    }

    //  필드 안의 값을 삭제. delete from 테이블 이름 where 필드명="값"
    void delete() {
        db.execSQL("delete from mytable4 where id=2;");
        Log.d(tag, "delete 완료");
    }

    //  필드 안의 값을 업데이트. update 테이블이름 set 필드명="변경할 값" where 필드값=해당값;
    void update() {
        db.execSQL("update mytable4 set name='Park' where id=5;");
        Log.d(tag, "update 완료");
    }

    //  select: 데이터베이스에 저장되어있는 데이터를 검색. select * from 테이블 명
    void select() {
        Cursor c = db.rawQuery("select * from mytable4;", null);
        while(c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            Log.d(tag,"id:"+id+",name:"+name);
        }
    }

    //  insert: 삽입문 추가. insert into 테이블명 (필드1, 필드2) (값1, 값2)
    void insert( EditText nameText, EditText passwordText, EditText ageText, EditText telText, EditText emailText) {
        //  테이블 만들기. create table 테이블명(필드 속성, ....)
        String sql = "create table mytable4(id integer primary key autoincrement, name text, password text, age integer, tel text, email text);";
        db.execSQL(sql);
        //db.execSQL("insert into mytable4 (name, password) values ('"+nameText+"', '"+passwordText+"' );");
        db.execSQL("insert into mytable4 (name, password) values ('"+nameText+"', '"+passwordText+"', '"+ageText+"', '"+telText+"', '"+emailText+"' );");
        Log.d(tag, "insert 성공~!");
    }

}
