package com.example.temi_v1.UI.activity.Booth;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.app.AppApplication;
import com.example.temi_v1.service.listener.BroadcastListener;
import com.example.temi_v1.util.Constant;
import com.example.temi_v1.util.MapIcon;
import com.example.temi_v1.util.SaveData;
import com.example.temi_v1.util.TimeToMain;
import com.gc.materialdesign.views.ButtonFloat;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

/**
 * 展厅介绍详情页===迎宾点
 */
public class BoothDetailsActivity extends BaseActivity implements OnGoToLocationStatusChangedListener {
//    int[] src = {R.mipmap.screensaver1,R.mipmap.screensaver2,R.mipmap.screensaver3,R.mipmap.screensaver4,R.mipmap.screensaver5,R.mipmap.screensaver6,R.mipmap.screensaver7,R.mipmap.screensaver8,R.mipmap.screensaver9,R.mipmap.screensaver10,R.mipmap.screensaver11,R.mipmap.screensaver12,R.mipmap.screensaver13,R.mipmap.screensaver14,R.mipmap.screensaver15,R.mipmap.screensaver16};

    @Override
    protected void initView() {
        ButtonFloat buttonFloat = findViewById(R.id.buttonFloat);
        buttonFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIntent().removeExtra(Constant.clicks);
                finish();
            }
        });
        final String videoid = getIntent().getStringExtra(Constant.boothDetail);
        final String titless = getIntent().getStringExtra(Constant.titless);

        TextView str2 = findViewById(R.id.detailstr2);//内容
        TextView str = findViewById(R.id.detailstr);//标题
        ImageView khxqicon = findViewById(R.id.khxqicon);//照片
        int icon=MapIcon.getIcon(titless);

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.img14)//图片加载出来前，显示的图片
                .fallback(R.mipmap.img14) //url为空的时候,显示的图片
                .error(R.mipmap.img14);//图片加载失败后，显示的图片
//        Log.e("=======", url + "=====");

        Glide.with(AppApplication.getInstance()).load(icon).apply(options).into(khxqicon);//设置照片

        str2.setText(videoid);
        str.setText(titless);
        robot.addOnGoToLocationStatusChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        robot.addOnRobotReadyListener(this);
        robot.addNlpListener(this);
        robot.addTtsListener(this);
//        robot.addOnGoToLocationStatusChangedListener(this);
    }

    @Override
    public void onGoToLocationStatusChanged(String s, String s1) {
        TimeToMain.timeToMain.cancel();//重新开始倒计时回首页
        TimeToMain.timeToMain.start();
        switch (s1) {
            case "complete"://已到目的地
                String addre = "我到了，这里就是" + s;
                if (!addre.equals("null")) {
                    addre = SaveData.getGuideData(s);
                }
                sayHello(addre);
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();

        int toAddre = getIntent().getIntExtra(Constant.clicks, 10);
        getIntent().removeExtra(Constant.clicks);
        if (toAddre == 1000) {//点击去某地
            String addrestr = getIntent().getStringExtra(Constant.titless);
            //延迟执行
            robot.goTo(addrestr);
        }
        if (toAddre == 2000) {//全局导览
            String addrestr = getIntent().getStringExtra(Constant.titless);
            //延迟执行
            robot.goTo(addrestr);
        }
    }

    @Override
    public void onTtsStatusChanged(TtsRequest ttsRequest) {//机器人说话状态，
        TimeToMain.timeToMain.cancel();//重新开始倒计时回首页
        TimeToMain.timeToMain.start();
        // Do whatever you like upon the status changing. after the robot finishes speaking
        Log.d("TAG", "speech: " + ttsRequest.getSpeech() + "status:" + ttsRequest.getStatus());
        TtsRequest.Status status = ttsRequest.getStatus();
        switch (status) {
            case ERROR:
                break;
            case COMPLETED://说完话
                if (getIntent().getStringExtra(Constant.bolAddress).equals("true")) {//是否是导览介绍说的话
                    if (!Constant.alladdressKey) {//是否导览所有地址
                        getIntent().removeExtra(Constant.clicks);//清除Intent,避免重复出动地点
                        finish();//每说完话结束当前页面
                        return;
                    }
                    final int dempindex = Constant.addressindex += 1;
                    if (Constant.addressindex < Constant.alladdress.length) {
                        //延迟执行
                        Constant.address = Constant.alladdress[dempindex];//获取地址赋值给临时地址
                        Constant.alladdressKey = true;//是全局导览
                        Intent intent = new Intent();
                        intent.setAction(BroadcastListener.ACTION);
                        intent.putExtra(Constant.clicks, 2000);
                        intent.putExtra(Constant.addressKey, Constant.address);
                        sendBroadcast(intent);//发送广播,继续全局导览
                        finish();//每说完话结束当前页面
                    } else {
//                        robot.goTo("迎宾点");
                        //延迟执行
                        getIntent().removeExtra(Constant.bolAddress);//清除数据
                        Constant.addressindex = 1;//下标复原
                        finish();
                    }
                }
                break;
        }
    }

    private void sayHello(String str) {
        TtsRequest ttsRequest = TtsRequest.create(str, false);
        speak(ttsRequest);
    }

    private TtsRequest pendingTts;

    private void speak(final TtsRequest ttsRequest) {
        if (robot.isReady()) {
            //延迟执行
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    robot.speak(ttsRequest);
                    pendingTts = null;
                }
            }, 2000);

        } else {
            this.pendingTts = ttsRequest;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        robot.removeOnRobotReadyListener(this);
//        robot.removeNlpListener(this);
//        robot.removeTtsListener(this);
//        robot.removeOnGoToLocationStatusChangedListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_booth_details2;
    }
}
