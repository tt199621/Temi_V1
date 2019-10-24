package com.example.temi_v1.util;

import android.content.Intent;
import com.example.temi_v1.app.AppApplication;
import com.example.temi_v1.service.listener.BroadcastListener;

/**
 * Created by Shinelon-LJL on 2019/9/18
 */
public class BusinessBroadTools {
    //在导航前，机器⼈随机话术
    public static void IntroductionsTalking() {
        Intent intent = new Intent();
        intent.setAction(BroadcastListener.ACTION);
        intent.putExtra(Constant.clicks,Constant.intStat4000);//赋值
        AppApplication.getInstance().sendBroadcast(intent);//发送广播
    }
    //最后⼀个点位介绍完，机器⼈随机话术
    public static void RandomTalking() {
        Intent intent = new Intent();
        intent.setAction(BroadcastListener.ACTION);
        intent.putExtra(Constant.clicks,Constant.intStat3000);//赋值
        AppApplication.getInstance().sendBroadcast(intent);//发送广播
    }
}
