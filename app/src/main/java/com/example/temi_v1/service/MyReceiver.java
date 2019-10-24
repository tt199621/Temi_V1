package com.example.temi_v1.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.example.temi_v1.UI.activity.MainActivity;

/**
 * 开机自启
 * Created by Shinelon-LJL on 2019/9/11
 */
public class MyReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        if (intent.getAction().equals(intent.ACTION_BOOT_COMPLETED))
        {

            //延迟执行
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("==ssss", "延迟执行");
                    Intent i = new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }, 30000);
        }
    }
}
