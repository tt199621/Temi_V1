package com.example.temi_v1.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.temi_v1.UI.activity.MainActivity;
import com.example.temi_v1.UI.activity.WorkDetailsActivity;

/**
 * 收到闹钟，启动闹钟窗口,延迟广播
 */
public class SnoozeReceiver extends BroadcastReceiver {
    public static final String ID_SNOOZE_FLAG = "id_snooze";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SnoozeReceiver","接收到贪睡广播");
        Intent i = new Intent(context, WorkDetailsActivity.class);
        i.putExtra(MainActivity.ALARM_ID, intent.getStringExtra(ID_SNOOZE_FLAG));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
