package com.example.daeun.pyeongchangstop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class EntertainmentActivity extends AppCompatActivity {
    ImageView tourButton, dormButton, foodButton, storeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);

        tourButton = (ImageView) findViewById(R.id.tourButton);
        tourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EntertainmentActivity.this, WebviewActivity.class);
                intent.putExtra("url","https://www.pyeongchang2018.com/ko/spectator-guide/travel-culture/attractions");
                startActivity(intent);
            }
        });

        dormButton = (ImageView) findViewById(R.id.dormButton);
        dormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EntertainmentActivity.this, WebviewActivity.class);
                intent.putExtra("url","https://www.pyeongchang2018.com/ko/spectator-guide/tourism-accommodation");
                startActivity(intent);
            }
        });

        foodButton = (ImageView) findViewById(R.id.foodButton);
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EntertainmentActivity.this, WebviewActivity.class);
                intent.putExtra("url","https://www.pyeongchang2018.com/ko/spectator-guide/travel-culture/food");
                startActivity(intent);
            }
        });

        storeButton = (ImageView) findViewById(R.id.storeButton);
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EntertainmentActivity.this, WebviewActivity.class);
                intent.putExtra("url","http://store.pyeongchang2018.com/");
                startActivity(intent);
            }
        });
    }
}
