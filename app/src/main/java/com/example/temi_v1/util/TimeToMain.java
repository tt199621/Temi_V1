package com.example.temi_v1.util;

import android.content.Intent;
import android.os.CountDownTimer;

import com.example.temi_v1.UI.activity.MainActivity;
import com.example.temi_v1.app.AppApplication;
import com.yhao.floatwindow.FloatActivity;

/**
 * Created by Shinelon-LJL on 2019/9/27
 */
public  class TimeToMain {
    /** 倒计时10秒，一次1秒 *///倒计时控制
   public static CountDownTimer timeToMain = new CountDownTimer(Constant.downTime60 * 1000, 1000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            Intent intent = new Intent(AppApplication.getInstance(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AppApplication.getInstance().startActivity(intent);
        }
    };
}
