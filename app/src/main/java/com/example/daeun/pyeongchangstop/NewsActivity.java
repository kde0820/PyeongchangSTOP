package com.example.daeun.pyeongchangstop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NewsActivity extends AppCompatActivity {
    ImageView pyeongchang, bbc, cnn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        pyeongchang = (ImageView) findViewById(R.id.pyeongchangButton);
        pyeongchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });

        bbc = (ImageView) findViewById(R.id.bbcButton);
        bbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });

        cnn = (ImageView) findViewById(R.id.cnnButton);
        cnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });
    }
}
