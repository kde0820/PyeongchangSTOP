package com.example.daeun.pyeongchangstop;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class QuizActivity extends AppCompatActivity {
    private static final String PATH = "/data/data/com.example.daeun.pyeongchangstop/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        File folder = new File(PATH + "databases");
        folder.mkdirs();
        File outFile = new File(PATH + "databases/quiz.db");

        if (outFile.length() <= 0) {
            AssetManager am = getResources().getAssets();
            InputStream is = null;
            try {
                is = am.open("quiz.db", AssetManager.ACCESS_BUFFER);
                long filesize = is.available();
                byte [] tempdata = new byte[(int)filesize];
                is.read(tempdata);
                is.close();
                outFile.createNewFile();
                FileOutputStream fo = new FileOutputStream(outFile);
                fo.write(tempdata);
                fo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH + "databases/quiz.db", null, SQLiteDatabase.OPEN_READONLY);
        Cursor c = db.rawQuery("SELECT * FROM description", null);
        c.moveToFirst();
        TextView quiztext = (TextView) findViewById(R.id.quizText);
        quiztext.setText(c.getString(1));
    }
}
