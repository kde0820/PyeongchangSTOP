package com.example.daeun.pyeongchangstop;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        final WebView webView = (WebView)
                findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function(){ " +
                        "document.getElementsByClassName('mwUI_is_fixed_wrap')[0].style.display='none'; " +
                        "document.getElementsByClassName('mwUI_is_fixed_wrap_sub-top-container')[0].style.display='none'; " +
                        "document.getElementsByClassName('floatingMenu')[0].style.display='none'; " +
                        "document.getElementsByClassName('banner-container')[0].style.display='none'; " +
                        "document.getElementsByClassName('sponsor-list')[0].style.display='none'; " +
                        "document.getElementsByClassName('footer')[0].style.display='none'; " +
                        "})()");
            }
        });
        webView.loadUrl("https://www.pyeongchang2018.com/ko/schedule");
    }
}

