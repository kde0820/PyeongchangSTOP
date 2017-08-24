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

public class SelectionQuizActivity extends AppCompatActivity {
    private static final String PATH = "/data/data/com.example.daeun.pyeongchangstop/";
    MySQLiteOpenHelper myDB;
    SQLiteDatabase db, quizdb;
    TextView quiz;
    RadioButton answer1, answer2, answer3, answer4;
    Cursor cursor, dcursor, ucursor;
    int chance, usrlogin, point, answer;
    Button submit;
    QuizActivity quizActivity = (QuizActivity) QuizActivity.quizactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_quiz);
        Util.setGlobalFont(this, getWindow().getDecorView(), "NanumGothic.ttf");

        myDB = new MySQLiteOpenHelper(getApplicationContext(), "pcSTOP.db", null, 1);
        db = myDB.getWritableDatabase();
        dcursor = db.rawQuery("SELECT * FROM datetable", null);
        dcursor.moveToFirst();
        usrlogin = dcursor.getInt(3); // 로그인 정보

        // 퀴즈 DB 정보
        quizdb = SQLiteDatabase.openDatabase(PATH + "databases/quiz.db", null, SQLiteDatabase.OPEN_READONLY);
        cursor = quizdb.rawQuery("SELECT * FROM selection", null);
        Random random = new Random();
        cursor.moveToPosition(random.nextInt(16));

        // 문제 세팅
        chance = 2;
        quiz = (TextView) findViewById(R.id.selectionQuestion);
        quiz.setText(cursor.getString(1));

        // 보기 세팅
        answer1 = (RadioButton) findViewById(R.id.answer1);
        answer2 = (RadioButton) findViewById(R.id.answer2);
        answer3 = (RadioButton) findViewById(R.id.answer3);
        answer4 = (RadioButton) findViewById(R.id.answer4);

        answer1.setText(cursor.getString(3));
        answer2.setText(cursor.getString(4));
        answer3.setText(cursor.getString(5));
        answer4.setText(cursor.getString(6));

        // 제출
        final AlertDialog.Builder builder = new AlertDialog.Builder(SelectionQuizActivity.this);
        submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizActivity.finish(); // 이전 퀴즈 액티비티 종료
                if (answer1.isChecked()) {
                    answer = 1;
                } else if (answer2.isChecked()) {
                    answer = 2;
                } else if (answer3.isChecked()) {
                    answer = 3;
                } else if (answer4.isChecked()) {
                    answer = 4;
                } else {
                    Toast.makeText(getApplicationContext(), "보기를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cursor.getInt(2) == answer) {
                    ucursor = db.rawQuery("SELECT * FROM usrtable", null);
                    ucursor.moveToPosition(usrlogin);
                    point = ucursor.getInt(7);
                    db.execSQL("update usrtable set point=" + (point + 10) + " where _id=" + (usrlogin + 1) + ";");

                    builder.setTitle("정답입니다!");
                    builder.setMessage("10point를 획득하셨습니다!\n객관식 퀴즈를 더 푸시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SelectionQuizActivity.this, SelectionQuizActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("다른 퀴즈 풀러가기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SelectionQuizActivity.this, QuizActivity.class);
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
                                Intent intent = new Intent(SelectionQuizActivity.this, QuizActivity.class);
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

    @Override
    public void onBackPressed() {
        quizActivity.finish();
        Intent intent = new Intent(SelectionQuizActivity.this, QuizActivity.class);
        startActivity(intent);
        finish();
    }
}
