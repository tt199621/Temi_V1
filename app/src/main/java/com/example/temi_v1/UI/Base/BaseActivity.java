package com.example.temi_v1.UI.Base;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.temi_v1.R;
import com.example.temi_v1.UI.activity.Adjustable.AdjustableActivity;
import com.example.temi_v1.UI.activity.Booth.BoothActivity;
import com.example.temi_v1.UI.activity.ContactActivity;
import com.example.temi_v1.UI.activity.ExhibitionModeActivity;
import com.example.temi_v1.UI.activity.MainActivity;
import com.example.temi_v1.UI.activity.Schedule.SchedulingListActivity;
import com.example.temi_v1.UI.activity.WorkModelActivity;
import com.example.temi_v1.app.AppApplication;
import com.example.temi_v1.service.MyService;
import com.example.temi_v1.service.listener.BroadcastListener;
import com.example.temi_v1.util.Constant;
import com.example.temi_v1.util.IntentTool;
import com.example.temi_v1.util.RobotTools;
import com.example.temi_v1.util.SaveData;
//import com.example.temi_v1.util.SpeakTool;
import com.example.temi_v1.util.TimeToMain;
import com.google.gson.Gson;
import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

import java.util.List;
import java.util.Map;

import static com.robotemi.sdk.constants.SdkConstants.EXTRA_NLP_RESULT;

/**
 * Created by Shinelon-LJL on 2019/6/18
 */
public abstract class BaseActivity extends AppCompatActivity implements OnRobotReadyListener, Robot.NlpListener, Robot.TtsListener {
    public FragmentManager fm = null;
    private static final int UI_ANIMATION_DELAY = 0;
    private FragmentTransaction transaction = null;
    private final Handler mHideHandler = new Handler();
    public Robot robot = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (robot == null) {
            robot = Robot.getInstance();
        }
        //初始化方法
        if (fm == null) {
            fm = getFragmentManager();
            if (transaction == null) {
                transaction = fm.beginTransaction();
            }
        }
        //将Activity加入集合
        AppApplication.addActivity(this);
        initView();
        initData();

        //actionBar.setCustomView(R.layout.actionbar_title);
        //隐藏ActionBar标题文字
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }

    //倒计时控制程序自动回首页
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("onTouchEvent", "onTouchEvent");
//            restartDownTime();
        return super.onTouchEvent(event);
    }

    @Override//触摸监听
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            TimeToMain.timeToMain.cancel();
            TimeToMain.timeToMain.start();
        }
    }
//    //从新开始倒计时
//    protected  void restartDownTime(){
////        toMainActivity.cancel();
////        toMainActivity.start();
//    };

//
//
//    /**
//     * 倒计时60秒，一次1秒
//     *///倒计时控制
//    CountDownTimer toMainActivity = new CountDownTimer(Constant.downTime * 1000, 1000) {
//        @Override
//        public void onTick(long l) {
//
//        }
//
//        @Override
//        public void onFinish() {
////            if (this instanceof MainActivity){
//            Intent exhibition = new Intent(AppApplication.getInstance(), MainActivity.class);
//            //跳转到展厅模式页面
//            startActivity(exhibition);
////            }
//        }
//    };

    private void handleIntent() {
        NlpResult nlpResult = getIntent().getParcelableExtra(EXTRA_NLP_RESULT);
        if (nlpResult != null) {
            onNlpCompleted(nlpResult);
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent();
//        getConstant();
    }

    //语音映射
    @Override
    public void onNlpCompleted(NlpResult nlpResult) {
        Map<String, String> param = nlpResult.params;
        for (String keys:param.keySet()){
            Log.e("keyset",keys);
            Log.e("entrySet",param.get(keys));

        }

        Constant.alladdressKey = false;
        switch (nlpResult.action) {
            case "sss.sss":
                // 实现做chat.chat对应的动作的代码，如： // 这里返回的是一个JSON数据，JSON的内部数据结构视具体情况而定
                // String response = nlpResult.params.get(KEY_TEMI_NLP_RESPONSE);
                Log.e("===dfdfd==", "=====");
                toExhibition();
                break;
            case "sss.work"://打开工作模式
                // 实现做chat.chat对应的动作的代码，如： // 这里返回的是一个JSON数据，JSON的内部数据结构视具体情况而定
                // String response = nlpResult.params.get(KEY_TEMI_NLP_RESPONSE);
                Log.e("===dfdfd==", "=====");
                toWorkModel();
                break;
            case "sss.main"://打开首页
                // 实现做chat.chat对应的动作的代码，如： // 这里返回的是一个JSON数据，JSON的内部数据结构视具体情况而定
                // String response = nlpResult.params.get(KEY_TEMI_NLP_RESPONSE);
                Log.e("===dfdfd==", "=====");
                toMainActivity();
                break;
            case "go.tv"://打开首页
                List<String> loca = RobotTools.getLocations();

                Log.e("loca", loca.get(2) + "");
                robot.goTo("机器人");
                break;
            case "work"://打开首页
//                SpeakTool.getInstance().goTO("工作区");
//                robot.speak(TtsRequest.create("工作区", false));
                robot.goTo("工作区");
                break;
            case "go.door"://打开首页
                robot.goTo("门口");
                break;
            case "schedule":
                //- 日程   安排
                //- 打开日程安排
                //- 查看日程安排
                Intent exhibition = new Intent(this, SchedulingListActivity.class);
                //跳转到展厅模式页面
                startActivity(exhibition);
                break;
            case "conference":
                //- 视频会议
                //- 打开视频会议
                //- 开始视频会议
                //跳转到联系人页面
                Intent contact = new Intent(this, ContactActivity.class);
                //跳转到展厅模式页面
                startActivity(contact);
                break;
            case "booth":
                //- 展位介绍
                // - 打开展位介绍
                // - 介绍展位
                Intent booth = new Intent(this, BoothActivity.class);
                //跳转到展厅模式页面
                startActivity(booth);
                break;
            case "intro.company":
//                robot.speak(TtsRequest.create("Cancelled", false));
                gotoCompany();
                break;
            case "tour"://导览介绍
                gotoCompany();
                break;
            /////////////////////////////////////////////////////////////////
            case "name"://活动介绍   SaveData.getGuideData(item)
                String activitys = "";
                String activity2 = SaveData.getGuideData("你是谁");
                String activity3 = SaveData.getGuideData("你叫什么名字");
                String activity4 = SaveData.getGuideData("你叫什么");
                if (!activity2.equals("null")) {
                    activitys = activity2;
                } else if (!activity3.equals("null")) {
                    activitys = activity3;
                } else if (!activity4.equals("null")) {
                    activitys = activity3;
                }

//                SpeakTool.getInstance().SpeakStr(activitys);
                robot.speak(TtsRequest.create(activitys, false));

                break;
            case "high"://活动介绍   SaveData.getGuideData(item)
                String high = "";
                String high1 = SaveData.getGuideData("你多高");
                String high2 = SaveData.getGuideData("你身高多少");
                if (!high1.equals("null")) {
                    high = high1;
                } else if (!high2.equals("null")) {
                    high = high2;
                }

                robot.speak(TtsRequest.create(high, false));
                break;
            case "restroom"://活动介绍   SaveData.getGuideData(item)
                String restroom = "";
                String restroom1 = SaveData.getGuideData("卫生间在哪");
                String restroom2 = SaveData.getGuideData("我想去卫生间");
                String restroom3 = SaveData.getGuideData("洗手间在哪");
                String restroom4 = SaveData.getGuideData("我想去洗手间");
                if (!restroom1.equals("null")) {
                    restroom = restroom1;
                } else if (!restroom2.equals("null")) {
                    restroom = restroom2;
                } else if (!restroom3.equals("null")) {
                    restroom = restroom3;
                } else if (!restroom4.equals("null")) {
                    restroom = restroom4;
                }
//                SpeakTool.getInstance().SpeakStr(restroom);
                robot.speak(TtsRequest.create(restroom, false));
                break;
            case "age"://活动介绍   SaveData.getGuideData(item)
                String age = "";
                String age1 = SaveData.getGuideData("你多大了");
                String age2 = SaveData.getGuideData("你几岁了");
                if (!age1.equals("null")) {
                    age = age1;
                } else if (!age2.equals("null")) {
                    age = age2;
                }
//                SpeakTool.getInstance().SpeakStr(age);
                robot.speak(TtsRequest.create(age, false));
                break;
            case "activity"://活动介绍   SaveData.getGuideData(item)
                String activity1 = SaveData.getGuideData("活动一");
//                AdjustableActivity.workList();
//                SpeakTool.getInstance().SpeakStr(activity1);
                robot.speak(TtsRequest.create(activity1, false));
                break;

            ///////////////////////////////////////////////////////////////////

        }
    }
    //公司导览
    public void gotoCompany() {
        List<String> location = RobotTools.getLocations();
        Log.e("location", location.size() + "");
        String[] lis = new String[location.size()];//获取赋值地址数据
        if (lis.length<=1){//当只有一个地址时（充电桩）,不导航
            return;
        }
        for (int i = 0; i < lis.length; i++) {
            lis[i] = location.get(i);
        }
        ////////////////////////////////开关，是否取本地保存的地址///////////////////////////////
        String jsonAddres=SaveData.getGuideData(Constant.saveAddressKey);//获取所有的地址
        if (!jsonAddres.equals("null")){//当地址不为空时
            Gson gson = new Gson();
            //把json地址数据转数组,然后赋值给Constant.alladdress
            String[] userArray = gson.fromJson(jsonAddres, String[].class);
            Constant.alladdress=userArray;
        }
        ///////////////////////////////////////////////////////////////
        if (Constant.alladdress.length!=lis.length){//当地址不一样的时候，刷新数据
            Constant.alladdress = lis;//给地址赋值
            //然后把地址保存起来
            Gson gson2=new Gson();
            String strAddressJson=gson2.toJson(location);//把list地址数据转json,然后保存起来
            SaveData.setGuideData(Constant.saveAddressKey,strAddressJson);
        }

        int ddd = Constant.addressindex = 1;//决定着从哪个下标开始,1表示去除第一个位置（充电桩）

        Constant.address=Constant.alladdress[ddd];//获取地址赋值给临时地址
        Constant.alladdressKey=true;//是全局导览
        Intent intent = new Intent();
        intent.setAction(BroadcastListener.ACTION);
        intent.putExtra(Constant.addressKey, Constant.address);
        sendBroadcast(intent);
        Constant.alladdressKey = true;
        Log.e("公司", "公司");
    }

    private void turnBy() {//改变角度
        Log.d("TAG", "turnBy 90 degree with speed 0.9f");
        Robot.getInstance().turnBy(-90, 0.9f);
    }

    private void toWorkModel() {
        Intent exhibition = new Intent(this, WorkModelActivity.class);
        //跳转到展厅模式页面
        startActivity(exhibition);
    }

    private void toExhibition() {
        Intent exhibition = new Intent(this, ExhibitionModeActivity.class);
        //跳转到展厅模式页面
        startActivity(exhibition);
    }

    private void toMainActivity() {
        Intent exhibition = new Intent(this, MainActivity.class);
        //跳转到展厅模式页面
        startActivity(exhibition);
    }

    @Override//机器人说话状态监听
    public void onTtsStatusChanged(TtsRequest ttsRequest) {

    }

    @Override
    public void onRobotReady(boolean b) {
        //任务栏显示图标
        if (b) {
//            try {
//                final ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
//                Robot.getInstance().onStart(activityInfo);
//            } catch (PackageManager.NameNotFoundException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initResume();
        robot.addOnRobotReadyListener(this);
        robot.addNlpListener(this);
        robot.addTtsListener(this);

    }



    /**
     * 初始化数据
     */
    protected void initResume() {
    }

    /**
     * 初始化
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 得到Activity布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();

//    @Override
//    public void onGoToLocationStatusChanged(String s, String s1) {
//        Log.e("loca", "执行onPause" + s + "====" + s1);
//        //mTimeCountUtil.start();
//        Constant.address = s;
////        switch (s1) {
////            case "start":
//////                robot.speak(TtsRequest.create("Starting", false));
////                break;
////            case "calculating":
//////                robot.speak(TtsRequest.create("准备出发", false));
////                break;
////            case "going":
//////                robot.speak(TtsRequest.create("跟着我", false));
////                break;
////            case "complete"://已到目的地
////                if (Constant.alladdressKey) {
////                    Intent intent = new Intent();
////                    intent.setAction(BroadcastListener.ACTION);
////                    intent.putExtra(Constant.addressKey, Constant.address);
////                    sendBroadcast(intent);
////                } else {
////                    Constant.alladdressKey = false;
////                    Intent intent = new Intent();
////                    intent.setAction(BroadcastListener.ACTION);
////                    intent.putExtra(Constant.addressKey, Constant.address);
////                    sendBroadcast(intent);
////                }
////
////                break;
////            case "abort":
//////                robot.speak(TtsRequest.create("Cancelled", false));
////
////                break;
//
////        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //将当前Activity移除集合
        AppApplication.removeActivity(this);
        robot.removeOnRobotReadyListener(this);
        robot.removeNlpListener(this);
        robot.removeTtsListener(this);
    }
}
