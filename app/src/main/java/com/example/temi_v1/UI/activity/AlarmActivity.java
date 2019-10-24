package com.example.temi_v1.UI.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.UI.activity.Schedule.ScheduleConfDetailActivity;
import com.example.temi_v1.UI.activity.Schedule.SchedulingConfActivity;
import com.example.temi_v1.app.AppApplication;
import com.example.temi_v1.data.MyAlarmDataBase;
import com.example.temi_v1.model.AlarmModel;
import com.example.temi_v1.util.Constant;
import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_msg,downtime;
    private MyAlarmDataBase db;
//    private Robot robot;
//    private TtsRequest pendingTts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);
        Button btn_cancle = findViewById(R.id.btn_cancle);
        tv_msg = findViewById(R.id.tv_msg);
        downtime = findViewById(R.id.downtime);
        Button btn_sure = findViewById(R.id.btn_sure);
        btn_cancle.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
        db = new MyAlarmDataBase(this);
        initData();
        toMainActivity.start();
//        robot = Robot.getInstance();
//        robot.addNlpListener(this);
    }
    private void sayHello(String str) {//日程提醒
        TtsRequest ttsRequest = TtsRequest.create(str, false);
        speak(ttsRequest);
    }

    private void speak(TtsRequest ttsRequest) {
//        if (robot.isReady()) {
            Robot.getInstance().speak(ttsRequest);
//            pendingTts = null;
//        } else {
//            pendingTts = ttsRequest;
//        }
    }
    String alarmid;
    private void initData() {
        alarmid=getIntent().getStringExtra(MainActivity.ALARM_ID).trim();
        Log.e("alarmid",alarmid);
        AlarmModel am = db.getAlarm(Integer.parseInt(alarmid));
        tv_msg.setText("您有一条"+am.getTime()+"日程提醒！");
        String time=am.getTime();
        String[] timelist=time.split(":");
        sayHello("您有一条"+timelist[0]+"点"+timelist[1]+"分的日程提醒！");
    }
    /**
     * 倒计时60秒，一次1秒
     *///倒计时控制
    CountDownTimer toMainActivity = new CountDownTimer(Constant.downTime20 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            downtime.setText(l/1000+"");
        }

        @Override
        public void onFinish() {
            finish();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancle:
                Intent schedule = new Intent(AlarmActivity.this, WorkDetailsActivity.class);
                int alarmidint=Integer.parseInt(alarmid);
                schedule.putExtra(Constant.activityId,alarmidint);//传输闹钟id
                startActivity(schedule);
                toMainActivity.cancel();
                finish();
                break;
            case R.id.btn_sure:
                toMainActivity.cancel();
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        robot.removeNlpListener(this);
    }

//    @Override
//    public void onNlpCompleted(NlpResult nlpResult) {
//
//    }
}
