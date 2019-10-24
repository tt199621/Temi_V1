package com.example.temi_v1.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 启动服务
 * Created by Administrator on 2016/10/26.
 */
public class MyService extends Service  {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        intent=new Intent();
//        intent.setAction(BroadcastListener.ACTION);
//        intent.putExtra("state", Constant.broadcastIntTow);
//        sendBroadcast(intent);
//        Log.e("aaa","=====");

        Log.e("==ssss", "延迟执行");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        startForeground(1, new Notification());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
