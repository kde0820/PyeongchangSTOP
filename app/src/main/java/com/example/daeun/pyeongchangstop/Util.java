package com.example.daeun.pyeongchangstop;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by daeun on 2017-08-22.
 */

class Util {
    public static void setGlobalFont(Context context, View view, String font){
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                int len = vg.getChildCount();
                for (int i = 0; i < len; i++) {
                    View v = vg.getChildAt(i);
                    if (v instanceof TextView) {
                        ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/"+font));
                    }
                    setGlobalFont(context, v, font);
                }
            }
        } else {
            Log.e("NULL","");
        }

    }
}
