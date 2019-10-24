package com.example.temi_v1.UI.activity.Schedule;

import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.data.MyAlarmDataBase;
import com.example.temi_v1.model.AlarmModel;
import com.example.temi_v1.service.AlarmService;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * 日程安排配置页面
 */
public class ScheduleConfDetailActivity extends BaseActivity implements
        TimePickerDialog.OnTimeSetListener, View.OnClickListener {
    Intent[] intents;
    Calendar calendarss;
    PendingIntent paintent;
    TextView selectime;
    TextView mRepeatText;
    Button schedule_save;
    private int mHour, mMinute, ID;
    private String mTime;
    private ServiceConnection connection = null;
    private AlarmService.MyBinder binder;
    private String work_remarks;
    private String mRepeatType, mRepeatCode;
    private String finalDefine;
    private MyAlarmDataBase db;
    private List<Integer> repeatCode = new ArrayList<>();
    AppCompatEditText mTitleText, appointedPerson, appointedAddress;
    private String mActive, mWake, mRing;
    private Calendar mCalendar;
    String typeStr="工作";//类型

    @Override
    protected void initView() {
        ImageView finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmModel am = db.getAlarm(ID);
                db.deleteAlarm(am);
                Log.d("AddActivity", "用户取消创建Alarm");
                finish();
            }
        });
        Button cencalalerm = findViewById(R.id.cencalalerm);
        cencalalerm.setOnClickListener(this);
        initMenu();
        mRepeatText = findViewById(R.id.calendar);
        appointedPerson = findViewById(R.id.appointedPerson);
        appointedAddress = findViewById(R.id.appointedAddress);
        schedule_save = findViewById(R.id.schedule_save);
        mTitleText = findViewById(R.id.schedule_event);

        schedule_save.setOnClickListener(this);
        selectime = findViewById(R.id.selectime);
        selectime.setOnClickListener(this);
        mRepeatText.setOnClickListener(this);

        initEvent();

        //设置默认值
        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mTime = mHour + ":" + mMinute;

        mActive = "true";
        mRepeatType = "每天";
        mRepeatCode = String.valueOf(new Random().nextInt(100) + 1);
        mWake = "常规";
        mRing = "响铃";

        db = new MyAlarmDataBase(this);
        ID = db.addAlarm(new AlarmModel(work_remarks, mTime, mWake, mRepeatType, mRepeatCode, mRing, typeStr,mActive));

    }

    private void initEvent() {
        //设置输入监听
        mTitleText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                work_remarks = s.toString().trim();
                mTitleText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    //选择响铃周期
    public void selectRepeat() {

        final String[] items = {"只响一次", "每天", "周一至周五", "周六周日", "自定义"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_view_day_grey600_24dp);
        builder.setTitle("选择重复");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String repeatType = items[which];
                if (which == 4) {
                    showDefineDialog(dialog);
                } else {
                    mRepeatType = repeatType;
                    mRepeatText.setText(mRepeatType);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDefineDialog(DialogInterface lastDialog) {
        lastDialog.dismiss();

        final String[] myDefine = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        final List<String> choosedDefine = new ArrayList<>();
        finalDefine = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("自定义");
        builder.setMultiChoiceItems(myDefine, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if (isChecked) {
                    choosedDefine.add(myDefine[which]);
                    repeatCode.add(which + 2);

                    StringBuilder sb = new StringBuilder();
                    if (repeatCode != null && repeatCode.size() > 0) {
                        for (int i = 0; i < repeatCode.size(); i++) {
                            if (i < repeatCode.size() - 1) {
                                sb.append(repeatCode.get(i) + ",");
                            } else {
                                sb.append(repeatCode.get(i));
                            }
                        }
                    }
                    mRepeatCode = sb.toString();
                }
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < choosedDefine.size(); i++) {
                    finalDefine = finalDefine + " " + choosedDefine.get(i);
                    mRepeatType = finalDefine;
                    mRepeatText.setText(mRepeatType);
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //设置时间
    public void selectTime() {
        final Calendar c = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(this,
                this,
                //闹钟的初始值（时和分）
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                false);
        dialog.show();
    }

    //设置时间的监听
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            mTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTime = hourOfDay + ":" + minute;
        }
        selectime.setText(mTime);
    }

    /**
     * 初始化选择菜单
     */
    private void initMenu() {
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("工作", "学习", "私事", "其它");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                switch (position) {
                    case 0:
                        typeStr="工作";
                        break;
                    case 1:
                        typeStr="学习";
                        break;
                    case 2:
                        typeStr="私事";
                        break;
                    case 3:
                        typeStr="其它";
                        break;
                }
            }
        });
    }

    //返回删除插入的数据
    @Override
    public void onBackPressed() {
        AlarmModel am = db.getAlarm(ID);
        db.deleteAlarm(am);
        Log.d("AddActivity", "用户取消创建Alarm");
        super.onBackPressed();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.schedule_save:
                mTitleText.setText(work_remarks);
                mWake = appointedPerson.getText().toString();
                if (appointedPerson.getText().toString().length() == 0) {
                    appointedPerson.setError("约定人不能为空");
                    return;
                }
                mRing = appointedAddress.getText().toString();
                if (appointedAddress.getText().toString().length() == 0) {
                    appointedAddress.setError("地点不能为空");
                    return;
                }
                if (mTitleText.getText().toString().length() == 0) {
                    mTitleText.setError("闹钟名不能为空");
                    return;
                }

                saveAlarm();
                finish();
                break;
            case R.id.cencalalerm:
                AlarmModel am = db.getAlarm(ID);
                db.deleteAlarm(am);
                Log.d("AddActivity", "用户取消创建Alarm");
                finish();
                break;
            case R.id.selectime://选择时间
                selectTime();
                break;
            case R.id.calendar: //选择响铃周期
                selectRepeat();
                break;
        }
    }

    /**
     * 保存闹钟
     */
    private void saveAlarm() {

        AlarmModel model = db.getAlarm(ID);
        model.setTitle(work_remarks);
        model.setTime(mTime);
        model.setTypeStr(typeStr);
        model.setRepeatType(mRepeatType);
        model.setRepeatCode(mRepeatCode);
        model.setWakeType(mWake);
        model.setActive(mActive);
        model.setRing(mRing);
        db.updateAlarm(model);

        if (mActive.equals("true")) {
            connection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    binder = (AlarmService.MyBinder) service;
                    switch (mRepeatType) {
                        case "只响一次":
                            binder.setSingleAlarm(getApplicationContext(), mTime, ID);
                            break;
                        case "每天":
                            binder.setEverydayAlarm(getApplicationContext(), mTime, ID);
                            break;
                        default:
                            binder.setDiyAlarm(getApplicationContext(), mRepeatType, mTime, ID, mRepeatCode);
                            repeatCode.clear();
                            break;
                    }

                    Log.d("AddActivity", "绑定服务");
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
        }

        Intent intent = new Intent(ScheduleConfDetailActivity.this, AlarmService.class);
        boolean bol = bindService(intent, connection, BIND_AUTO_CREATE);
        Log.d("ScheduleConfDetailActivity", "绑定服务" + bol);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connection != null) {
            unbindService(connection);
        }
    }
}
