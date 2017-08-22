package com.example.daeun.pyeongchangstop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;

public class ShortQuizActivity extends AppCompatActivity {
    private static final String PATH = "/data/data/com.example.daeun.pyeongchangstop/";
    MySQLiteOpenHelper myDB;
    SQLiteDatabase db, quizdb;
    TextView question;
    Cursor cursor, dcursor, ucursor;
    EditText answer;
    int chance, usrlogin, point;
    Button submit;
    QuizActivity quizActivity = (QuizActivity) QuizActivity.quizactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_quiz);

        myDB = new MySQLiteOpenHelper(getApplicationContext(), "pcSTOP.db", null, 1);
        db = myDB.getWritableDatabase();
        dcursor = db.rawQuery("SELECT * FROM datetable", null);
        dcursor.moveToFirst();
        usrlogin = dcursor.getInt(3); //로그인 정보
        Log.e("usrlogin: ", usrlogin + "");
        // 퀴즈 DB정보
        quizdb = SQLiteDatabase.openDatabase(PATH + "databases/quiz.db", null, SQLiteDatabase.OPEN_READONLY);
        cursor = quizdb.rawQuery("SELECT * FROM description", null);
        Random random = new Random();
        cursor.moveToPosition(random.nextInt(9));

        chance = 3;
        question = (TextView) findViewById(R.id.shortQuestion);
        question.setText(cursor.getString(1));

        answer = (EditText) findViewById(R.id.shortAnswer);
        final AlertDialog.Builder builder = new AlertDialog.Builder(ShortQuizActivity.this);
        submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizActivity.finish(); // 이전 퀴즈 액티비티 종료
                if (cursor.getString(2).equals(answer.getText().toString())) {
                    ucursor = db.rawQuery("SELECT * FROM usrtable", null);
                    ucursor.moveToPosition(usrlogin);
                    point = ucursor.getInt(7);
                    db.execSQL("update usrtable set point=" + (point + 10) + " where _id=" + (usrlogin + 1) + ";");

                    builder.setTitle("정답입니다!");
                    builder.setMessage("10point를 획득하셨습니다!\nbtn_short 퀴즈를 더 푸시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ShortQuizActivity.this, ShortQuizActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("다른 퀴즈 풀러가기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ShortQuizActivity.this, QuizActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.create().show();
                } else {
                    builder.setTitle("틀렸습니다!");
                    if (chance > 0) {
                        chance--;
                        builder.setMessage("남은 기회는 " + chance + "번 입니다.\n다시 시도하시겠습니까?");
                        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                    } else {
                        builder.setMessage("기회를 모두 사용하였습니다.\n다른 문제를 시도해보세요!");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ShortQuizActivity.this, QuizActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                    builder.create().show();
                }
            }
        });


    }
}
