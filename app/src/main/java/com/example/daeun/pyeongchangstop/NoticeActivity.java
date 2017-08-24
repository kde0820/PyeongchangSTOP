package com.example.daeun.pyeongchangstop;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

// 공지사항 파싱 --> 관련 뉴스 파싱
public class NoticeActivity extends AppCompatActivity {
    String url = "https://www.pyeongchang2018.com/ko/press-releases";
    ArrayList<NewsItem> newsItemList;
    ListView lvNews;
    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        newsItemList = new ArrayList<>();

        lvNews = (ListView) findViewById(R.id.Listview_news);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document doc = Jsoup.parse(response);
                Element form = doc.getElementById("listForm");
                Elements itemElements = form.getElementsByTag("li");

                for (int i = 0; i < itemElements.size(); i++) {
                    Element item = itemElements.get(i);
                    String title = item.getElementsByClass("photo-tit").first().text();
                    String datetime = item.getElementsByClass("photo-date").first().text();
                    Element imgDiv = item.getElementsByClass("photo-img").first();
                    String imageLink = parseImageLink(imgDiv.getElementsByTag("img").first().attr("data-original"));
                    String newsLink = makeNewsLink(item.getElementsByTag("a").first().attr("href"));

                    Log.e("이미지: ", imgDiv.getElementsByTag("img").first().attr("data-original"));

                    NewsItem news = new NewsItem();
                    news.title = title;
                    news.dateTime = datetime;
                    news.imageLink = imageLink;
                    news.newsLink = newsLink;
                    newsItemList.add(news);
                }


                adapter = new NewsAdapter(NoticeActivity.this, newsItemList);
                lvNews.setAdapter(adapter);
                lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        NewsItem currentNews = newsItemList.get(i);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.newsLink));
                        startActivity(intent);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NoticeActivity.this, "request failed", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    private String parseImageLink(String link) {
        url = url.replace("/ko/press-releases","");
        link = url + link;
        return link;
    }

    private String makeNewsLink(String link) {
        url = url.replace("/ko/press-releases","");
        link = link.replaceAll(" ","");
        link = url + link;
        return link;
    }

}
