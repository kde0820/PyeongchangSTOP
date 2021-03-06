package com.example.daeun.pyeongchangstop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class OxQuizActivity extends AppCompatActivity {
    private static final String PATH = "/data/data/com.example.daeun.pyeongchangstop/";
    MySQLiteOpenHelper myDB;
    SQLiteDatabase db, quizdb;
    TextView quiz;
    RadioButton answerO, answerX;
    Cursor cursor, dcursor, ucursor;
    int usrlogin, point;
    Button submit;
    String answer;
    QuizActivity quizActivity = (QuizActivity) QuizActivity.quizactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ox_quiz);
        Util.setGlobalFont(this, getWindow().getDecorView(), "NanumGothic.ttf");

        myDB = new MySQLiteOpenHelper(getApplicationContext(), "pcSTOP.db", null, 1);
        db = myDB.getWritableDatabase();
        dcursor = db.rawQuery("SELECT * FROM datetable", null);
        dcursor.moveToFirst();
        usrlogin = dcursor.getInt(3); // 로그인 정보

        // 퀴즈 DB 정보
        quizdb = SQLiteDatabase.openDatabase(PATH + "databases/quiz.db", null, SQLiteDatabase.OPEN_READONLY);
        cursor = quizdb.rawQuery("SELECT * FROM ox", null);
        Random random = new Random();
        cursor.moveToPosition(random.nextInt(9));

        //문제 세팅
        quiz = (TextView) findViewById(R.id.OxQuestion);
        quiz.setText(cursor.getString(1));

        // 보기
        answerO = (RadioButton) findViewById(R.id.answerO);
        answerX = (RadioButton) findViewById(R.id.answerX);

        // 제출
        final AlertDialog.Builder builder = new AlertDialog.Builder(OxQuizActivity.this);
        submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizActivity.finish();
                if (answerO.isChecked()) {
                    answer = "Y";
                } else if (answerX.isChecked()) {
                    answer = "N";
                } else {
                    Toast.makeText(getApplicationContext(), "보기를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //정답 확인
                if (cursor.getString(2).equals(answer)) {
                    ucursor = db.rawQuery("SELECT * FROM usrtable", null);
                    ucursor.moveToPosition(usrlogin);
                    point = ucursor.getInt(7);
                    db.execSQL("update usrtable set point=" + (point + 10) + " where _id=" + (usrlogin + 1) + ";");

                    builder.setTitle("정답입니다!");
                    builder.setMessage("10point를 획득하셨습니다!\nOX 퀴즈를 더 푸시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(OxQuizActivity.this, OxQuizActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("다른 퀴즈 풀러가기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(OxQuizActivity.this, QuizActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.create().show();
                } else {
                    builder.setTitle("틀렸습니다!");
                    builder.setMessage("기회를 모두 사용하였습니다.\n다른 문제를 시도해보세요!");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(OxQuizActivity.this, QuizActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.create().show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        quizActivity.finish();
        Intent intent = new Intent(OxQuizActivity.this, QuizActivity.class);
        startActivity(intent);
        finish();
    }
}
