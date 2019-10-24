package com.example.temi_v1.util;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

/**
 * 倒计时
 * Created by kqw on 2016/5/11.
 * TimeCountUtil
 */
public class TimeCountUtil extends CountDownTimer {
    private TimeCountUtil.TimeListener mListener = null;

    public TimeCountUtil(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);

    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {
        mListener.onTimeLoad();
        Log.e("=====", "111111");
    }

    public interface TimeListener {
        /**
         * 点击条目
         */
        void onTimeLoad();
    }

    public void setOnTimeListener(TimeCountUtil.TimeListener listener) {
        this.mListener = listener;
    }
}
