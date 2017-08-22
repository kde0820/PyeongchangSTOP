package com.example.daeun.pyeongchangstop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by daeun on 2017-08-08.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    // 안드로이드에서 SQLite 데이터 베이스를 쉽게 사용할 수 있도록 도와주는 클래스
    public MySQLiteOpenHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 최초에 데이터베이스가 없을경우, 데이터베이스 생성을 위해 호출됨
        // (이미 데이터 베이스가 만들어 졌다면, 더이상 수정, 변경 불가. 다른 테이블을 이용하고 싶으면 새로 선언문을 날려야함. )
        // 테이블 생성하는 코드를 작성한다
        String sql = "create table usrtable(_id INTEGER PRIMARY KEY AUTOINCREMENT, userId text, name text, password text, age integer, telephone text, email text, point integer);";
        db.execSQL(sql);

        // 사용자 정보 테이블 생성 및 초기화
        ContentValues val = new ContentValues();
        val.put("userId","kde0820");
        val.put("name","kimdaeun");
        val.put("password","kkkk");
        val.put("age", 22);
        val.put("telephone", "01000000000");
        val.put("email", "naver");
        val.put("point", 0);
        db.insert("usrtable", null, val);

        // 날짜, 잠금해제 횟수, 로그인 정보 테이블 생성 및 초기화
        sql = "create table datetable(_id INTEGER PRIMARY KEY AUTOINCREMENT, usedate text, unlocktime integer, usrlogin integer, usrquiz integer)";
        db.execSQL(sql);
        db.execSQL("insert into datetable (usedate, unlocktime, usrlogin, usrquiz) values (date('now','localtime'), 0, -1, 5);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스의 버전이 바뀌었을 때 호출되는 콜백 메서드
        // 버전 바뀌었을 때 기존데이터베이스를 어떻게 변경할 것인지 작성한다
        // 각 버전의 변경 내용들을 버전마다 작성해야함
        String sql = "drop table usrtable;"; // 테이블 드랍
        db.execSQL(sql);
        sql = "drop table datetable";
        db.execSQL(sql);
        onCreate(db); // 다시 테이블 생성
    }

    //  select: 데이터베이스에 저장되어있는 데이터를 검색. select * from 테이블 명
    public int select(String idText, String passText) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from usrtable;", null);
        boolean b = c.moveToFirst();
        int flag = -1;

        while(b) {
            int primaryNum = c.getInt(0);
            String userId = c.getString(1);
            //  아이디와 db에 저장된 아이디가 같으면 primaryNum 리턴
            if(idText.equals(userId)){
                if(passText.equals(c.getString(3))){
                    flag = primaryNum;
                    return flag;
                } else{
                    return -2;
                }
            }
            b = c.moveToNext();
        }
        return flag;
    }

    //  insert: 삽입문 추가. insert into 테이블명 (필드1, 필드2) (값1, 값2)
    public void insert(String idText, String nameText, String passwordText, String ageText, String telText, String emailText) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into usrtable (userId, name, password, age, telephone, email) values ('"+idText+"', '"+nameText+"', '"+passwordText+"', '"+ageText+"', '"+telText+"', '"+emailText+"' );");
        db.close();
    }
}
