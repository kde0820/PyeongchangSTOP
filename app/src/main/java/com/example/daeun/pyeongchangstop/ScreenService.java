package com.example.daeun.pyeongchangstop;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.IntDef;

public class ScreenService extends Service {
    public ScreenService() {
    }
    private ScreenReceiver mReceiver = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mReceiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //startForeground(1,new Notification());
        if (intent != null){
            if (mReceiver == null){
                mReceiver = new ScreenReceiver();
                IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
                registerReceiver(mReceiver, filter);
            }
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mReceiver != null){
            unregisterReceiver(mReceiver);
        }
    }
}
