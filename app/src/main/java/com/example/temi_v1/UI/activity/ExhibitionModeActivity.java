package com.example.temi_v1.UI.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.UI.activity.Booth.BoothActivity;
import com.example.temi_v1.service.listener.BroadcastListener;
import com.example.temi_v1.util.Constant;
import com.example.temi_v1.util.RobotTools;
import com.example.temi_v1.util.SaveData;
//import com.example.temi_v1.util.SpeakTool;
import com.example.temi_v1.util.TimeCountUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.robotemi.sdk.TtsRequest;

import java.util.List;

import static com.example.temi_v1.util.IntentTool.inputPwd;
import static com.example.temi_v1.util.Tools.gototherApp;

/**
 * 展厅介绍界面
 */
public class ExhibitionModeActivity extends BaseActivity implements View.OnClickListener {

    String addressName = "";

    @Override
    protected void initView() {
        ImageView introduction = findViewById(R.id.introduction);
        ImageView contacts = findViewById(R.id.contacts);
        ImageView weatherbtn = findViewById(R.id.weatherbtn);
        ImageView returnhome = findViewById(R.id.returnhome);
        ImageView exhibition = findViewById(R.id.exhibition);
        ImageView switch_btn = findViewById(R.id.switch_btn);
        ImageView conf_btn = findViewById(R.id.conf_btn);
        ImageView music_qq = findViewById(R.id.music_qq);
        ImageView news = findViewById(R.id.news);
        introduction.setOnClickListener(this);
        news.setOnClickListener(this);
        exhibition.setOnClickListener(this);
        switch_btn.setOnClickListener(this);
        conf_btn.setOnClickListener(this);
        music_qq.setOnClickListener(this);
        returnhome.setOnClickListener(this);
        weatherbtn.setOnClickListener(this);
        contacts.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exhibition_mode2;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.introduction:
                Log.e("=====", "=====");
                //准备跳转展位介绍
                Intent workmodel = new Intent(this, BoothActivity.class);
                startActivity(workmodel);
                break;
            case R.id.switch_btn://切换按钮 切换展厅模式
                Intent exhibition = new Intent(ExhibitionModeActivity.this, WorkModelActivity.class);
                startActivity(exhibition);
                finish();
                break;
            case R.id.weatherbtn:
                //准备跳转天气页面
                gototherApp("com.robotemi.dingdang.weather.china", "com.robotemi.dingdang.weather.WeatherActivity");
                break;
            case R.id.music_qq:
                //准备跳转音乐页面
                gototherApp("com.robotemi.dingdang.music.china", "com.robotemi.dingdang.music.MusicPlayerActivity");
                break;
            case R.id.contacts:
                //跳转到联系人页面
                Intent contact = new Intent(this, ContactActivity.class);
                startActivity(contact);
                break;
            case R.id.returnhome://返回首页
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                break;
            case R.id.news:
                //跳转到新闻页面
                gototherApp("com.robotemi.dingdang.news.china", "com.robotemi.dingdang.news.NewsActivity");
                break;
            case R.id.exhibition://导览介绍
                gotoCompany();//介绍所有地址
                break;
            case R.id.conf_btn://配置
                inputPwd(ExhibitionModeActivity.this, false);
                break;
        }
    }

    int index = 0;

    private void goAddress() {
        index += 1;
        //经行展厅介绍
        List<String> loca = RobotTools.getLocations();
        if (index < loca.size()) {
            Log.e("loca", index + "");
            robot.goTo(loca.get(index));
//            SpeakTool.getInstance().goTO(loca.get(index));
            Constant.powerSend = true;
        }
    }
}
