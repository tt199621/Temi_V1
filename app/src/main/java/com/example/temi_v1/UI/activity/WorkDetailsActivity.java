package com.example.temi_v1.UI.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.data.MyAlarmDataBase;
import com.example.temi_v1.model.AlarmModel;
import com.example.temi_v1.service.SnoozeReceiver;
import com.example.temi_v1.util.Constant;

import java.util.Calendar;

/**
 * 日程详情页
 */
public class WorkDetailsActivity extends BaseActivity implements View.OnClickListener {
    private MyAlarmDataBase db;
// intent.putExtra(Constant.activityId,list.get(position).IDList);
    @Override
    protected void initView() {
        ImageView finish = findViewById(R.id.finish);
        Button snoozebtn = findViewById(R.id.snoozebtn);
        Button work_iknow = findViewById(R.id.work_iknow);
        snoozebtn.setOnClickListener(this);
        work_iknow.setOnClickListener(this);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        db = new MyAlarmDataBase(this);
    }
    int ints;
    @Override
    protected void initData() {
        super.initData();
        ints = getIntent().getIntExtra(Constant.activityId, 0);
        AlarmModel am = db.getAlarm(ints);
        TextView typestr = findViewById(R.id.typestr);
        TextView timestr = findViewById(R.id.timestr);
        TextView addresstr = findViewById(R.id.addresstr);
        TextView remarksstr = findViewById(R.id.remarksstr);
        TextView appointedPerson = findViewById(R.id.appointedPerson);
        typestr.setText("类型:"+am.getTypeStr());
        appointedPerson.setText("时间:" + am.getWakeType());//约定人
        timestr.setText("时间:" + am.getTime());//时间
        addresstr.setText("地点:" + am.getRing());//地点
        remarksstr.setText("备注:" + am.getTitle());//
        Log.e("tag==", am.toString());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_details;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.snoozebtn:
                snooze();//延迟闹钟
                break;
            case R.id.work_iknow://我知道了
                finish();
                break;
        }
    }
    private static final long FIVE_MINUTE_TIME = 1000 * 60 * 5;
    private static final int SNOOZE_ALARM_ID = 100;
    //休息一会再响
    private void snooze() {
        AlarmManager manager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Calendar c =Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), 0);
        long mTimeInfo = c.getTimeInMillis()+FIVE_MINUTE_TIME;

        Intent intent = new Intent(this, SnoozeReceiver.class);
        intent.putExtra(SnoozeReceiver.ID_SNOOZE_FLAG, Integer.toString(ints));
        PendingIntent pi = PendingIntent.getBroadcast(this,SNOOZE_ALARM_ID,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        manager.set(AlarmManager.RTC_WAKEUP, mTimeInfo, pi);
        Log.d("NormalFragment","已设置贪睡");
        finish();
    }
}
