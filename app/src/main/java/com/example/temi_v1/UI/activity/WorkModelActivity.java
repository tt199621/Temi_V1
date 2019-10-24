package com.example.temi_v1.UI.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.UI.activity.Schedule.SchedulingListActivity;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.UserInfo;

import java.util.List;

import static com.example.temi_v1.util.IntentTool.inputPwd;

public class WorkModelActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void initView() {
        ImageView scheduling = findViewById(R.id.scheduling);
        ImageView weatherbtn = findViewById(R.id.weatherbtn);
        ImageView news = findViewById(R.id.news);
        ImageView videoMeeting = findViewById(R.id.videoMeeting);
        ImageView switch_btn = findViewById(R.id.switch_btn);
        ImageView returnhome = findViewById(R.id.returnhome);
        ImageView conf_btn = findViewById(R.id.conf_btn);
        ImageView music_qq = findViewById(R.id.music_qq);
        ImageView contacts = findViewById(R.id.contacts);
        scheduling.setOnClickListener(this);
        videoMeeting.setOnClickListener(this);
        switch_btn.setOnClickListener(this);
        news.setOnClickListener(this);
        returnhome.setOnClickListener(this);
        music_qq.setOnClickListener(this);
        conf_btn.setOnClickListener(this);
        contacts.setOnClickListener(this);
        weatherbtn.setOnClickListener(this);
    }

    //跳转到展厅模式页面
    private void toExhibition() {
        Intent exhibition = new Intent(this, ExhibitionModeActivity.class);
        //跳转到展厅模式页面
        startActivity(exhibition);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_model2;
    }

    @Override
    public void onClick(View view) {
        Intent contact = new Intent(this, ContactActivity.class);
        switch (view.getId()) {
            case R.id.scheduling:
                Log.e("=====", "=====");
                //准备跳转日程安排
                Intent workmodel = new Intent(this, SchedulingListActivity.class);
                startActivity(workmodel);
                break;
            case R.id.returnhome:
                Log.e("=====", "=====");
                //准备跳转日程安排
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                break;
            case R.id.weatherbtn:
                //准备跳转天气页面
                gototherApp("com.robotemi.dingdang.weather.china", "com.robotemi.dingdang.weather.WeatherActivity");
                break;
            case R.id.music_qq:
                //准备跳转音乐页面
                gototherApp("com.robotemi.dingdang.music.china", "com.robotemi.dingdang.music.MusicPlayerActivity");
                break;
            case R.id.videoMeeting:
                Log.e("=====", "=====");
                //跳转到视频通话页面
                startActivity(contact);
                break;
            case R.id.contacts:
                Log.e("=====", "=====");
                //跳转到联系人页面
                startActivity(contact);
                break;
            case R.id.news:
                gototherApp("com.robotemi.dingdang.news.china", "com.robotemi.dingdang.news.NewsActivity");
                break;
            case R.id.switch_btn://切换按钮 切换展厅模式
                toExhibition();
                finish();
                break;
            case R.id.conf_btn://配置
                inputPwd(WorkModelActivity.this, false);
                break;
        }
    }

    /**
     * @param packages
     * @param activityPath
     */
    private void gototherApp(String packages, String activityPath) {
        Log.e("=====", "=====");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packages,
                activityPath);
        intent.setComponent(cn);
        if (intent.resolveActivityInfo(getPackageManager(), PackageManager.MATCH_DEFAULT_ONLY) != null) {//启动的intent存在
            startActivity(intent);
        } else {
            Toast.makeText(this, "应用未安装", Toast.LENGTH_SHORT).show();
        }
    }

    private void getConstant() {
        List<UserInfo> list = Robot.getInstance().getAllContact();
        startTelepresence(list.get(0));
        Log.e("dsfsfdsf", list.get(0).getName() + "dfdfdfd");
    }

    private void startTelepresence(UserInfo userInfo) {
        Log.d("TAG", "startTelepresence with " + userInfo);
        if (userInfo != null) {
            Robot.getInstance().startTelepresence(userInfo.getName(), userInfo.getUserId());
        }
    }
}
