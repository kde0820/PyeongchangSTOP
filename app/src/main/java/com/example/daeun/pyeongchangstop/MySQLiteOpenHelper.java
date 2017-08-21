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

        // 잠금화면 컨텐츠 테이블 생성 및 초기화
        sql = "create table locktable(_id INTEGER PRIMARY KEY AUTOINCREMENT, title text, content text)";
        db.execSQL(sql);
        db.execSQL("insert into locktable (title, content) values ('알파인 스키','알파인 스키는 뒤꿈치가 고정된 바인딩을 장착한 스키를 타고 눈 덮인 슬로프를 내려오는 스포츠입니다. 경기종목은 크게 스피드(속도) 종목과 테크니컬(기술) 종목, 2가지로 구성됩니다.');");
        db.execSQL("insert into locktable (title, content) values ('바이애슬론','바이애슬론은 서로 다른 종목인 크로스컨트리 스키와 사격이 결합된 경기입니다. 선수들은 총을 등에 맨 채로 스키를 타고 일정 거리를 주행하며, 정해진 사격장에서 사격을 합니다.');");
        db.execSQL("insert into locktable (title, content) values ('봅슬레이','봅슬레이는 방향을 조종할 수 있는 썰매를 타고 얼음으로 만든 트랙을 활주하는 경기입니다. 봅슬레이 종목은 남자 4인승, 2인승, 여자 2인승 총 3개의 종목이 진행됩니다. 선수들은 세계선수권대회와 올림픽에서 총 4차례 활주하며 그 시간을 합산해 순위를 결정합니다.');");
        db.execSQL("insert into locktable (title, content) values ('크로스컨트리 스키','크로스컨트리 스키는 눈 쌓인 들판을 달려 빠른 시간 내에 완주하는 경기입니다. 경주 코스는 오르막, 평지, 내리막 비율이 1/3씩 구성되어 있으며, 선수들은 클래식과 프리 주법을 사용해야 합니다.');");
        db.execSQL("insert into locktable (title, content) values ('컬링','컬링은 한 경기 내에서 4인이 한 팀으로 구성되며, 각 팀이 번갈아 가며 스톤을 던집니다. 이 때 두 명 이상의 선수가 스톤의 이동 경로를 따라 함께 움직이며 ‘브룸’이라 불리는 솔을 이용해 ‘스톤’의 진로와 속도를 조절합니다.');");
        db.execSQL("insert into locktable (title, content) values ('피겨 스케이팅','피겨 스케이팅은 음악에 맞추어 스케이트를 신고 빙판 위를 활주하며 다양한 동작으로 기술의 정확성과 아름다움을 겨루는 빙상경기입니다. 올림픽에서는 피겨 스케이팅 싱글, 피겨 스케이팅 아이스댄스와 피겨 스케이팅 페어, 피겨 스케이팅 팀 이벤트 등 총 5개의 종목이 진행됩니다.');");
        db.execSQL("insert into locktable (title, content) values ('프리스타일 스키','프리스타일 스키는 선수들이 슬로프를 자유롭게 활강하면서 공중곡예를 통해 예술성을 겨루는 경기로 설원의 서커스라고 불리기도 합니다. 속도를 겨루는 알파인 스키와는 달리 백플립, 트위스트 등 선수들의 화려한 공중 기술을 볼 수 있는 것이 가장 큰 특징입니다.');");
        db.execSQL("insert into locktable (title, content) values ('아이스 하키','아이스 하키는 빙상에서 스케이트를 착용한 6명으로 구성된 두 팀이 고무 원판의 퍽을 스틱으로 쳐서 상대팀의 골에 넣는 경기이며, 동계올림픽에서는 남자, 여자 총 2개의 종목이 진행됩니다.');");
        db.execSQL("insert into locktable (title, content) values ('루지','루지는 발을 전방으로 향하고 얼굴을 하늘로 향한 자세로 소형 썰매를 타고 1,000 - 1,500m를 활주하는 스포츠입니다. 선수 각각 한 명(싱글) 또는 두 명(더블) 씩 출발하며, 개인 종목은 이틀 동안 4번 주행한 기록을 합산하여 순위를 결정합니다.');");
        db.execSQL("insert into locktable (title, content) values ('노르딕 복합','노르딕 복합은 크로스컨트리 스키와 스키점프를 함께 치르는 경기입니다. 높은 기술과 대담성을 필요로 하는 스키 점프와 강인한 체력을 필요로 하는 크로스컨트리 스키 경기를 모두 치러야 하므로 스키 경기 중에서도 매우 어려운 종목입니다.');");
        db.execSQL("insert into locktable (title, content) values ('쇼트트랙 스피드 스케이팅','쇼트트랙 스피드 스케이팅은 111.12m 아이스링크 위에서 스케이트 경주를 펼치는 빙상 경기입니다. 기존의 400m의 트랙에서 경주하는 스피드 스케이팅에 비해 짧은 트랙에서 경기를 하기 때문에 쇼트트랙 스피드 스케이팅이라고도 하나, 주로 줄여서 쇼트트랙이라 칭하고 있습니다.');");
        db.execSQL("insert into locktable (title, content) values ('스켈레톤','스켈레톤은 머리를 앞에 두고 엎드린 자세로 1,200m 이상 경사진 얼음 트랙을 질주하는 경기입니다. 유일하게 썰매 종목 중 남녀 개인종목으로 이루어져 있으며 어깨, 무릎을 이용하여 조종을 합니다.');");
        db.execSQL("insert into locktable (title, content) values ('스키점프','스키점프는 스키를 타고 급경사면(35 - 37°)을 90Km/h 이상으로 활강하여 내려오다 도약대로부터 착지까지 가장 멀리, 그리고 안정적으로 비행해서 착지하는 경기입니다. 활강과 비행 모습이 아름다워 스키 경기의 꽃이라고 불리기도 합니다.');");
        db.execSQL("insert into locktable (title, content) values ('스노보드','스노보드는 보드를 이용하여 슬로프를 질주하는 종목으로 1960년대 미국에서 스포츠로 발전하여 세계 각국에서 남녀노소 누구나 즐길 수 있는 스포츠로 발전되었으며 1998년 일본 나가노 동계올림픽대회에서 정식종목으로 채택되었습니다.');");
        db.execSQL("insert into locktable (title, content) values ('스피드 스케이팅','스피드 스케이팅은 스케이트를 신은 2명의 선수가 동시에 출발하여 400m 의 아이스링크 트랙 위에서 속도를 겨루는 빙상경기이며, 400m의 코스는 인코스와 아웃코스로 구분하며, 2인 1조의 주자가 1주 할 때마다 정해진 교차 구역에서 서로 활주로를 바꾸게 됩니다.');");

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
        sql = "drop table locktable";
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
