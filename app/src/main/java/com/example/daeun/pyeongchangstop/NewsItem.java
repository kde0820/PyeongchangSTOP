package com.example.daeun.pyeongchangstop;

import java.io.Serializable;

/**
 * Created by daeun on 2017-08-14.
 */

public class NewsItem implements Serializable {
    String title;
    String dateTime;
    String imageLink;
    String newsLink;

    @Override
    public String toString() {
        return title;
    }
}
