package com.example.temi_v1.UI.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.Face3DAngle;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.arcsoft.face.VersionInfo;
import com.example.temi_v1.Arcface.camera.CameraHelper;
import com.example.temi_v1.Arcface.camera.CameraListener;
import com.example.temi_v1.Arcface.common.Constants;
import com.example.temi_v1.Arcface.model.DrawInfo;
import com.example.temi_v1.Arcface.util.ConfigUtil;
import com.example.temi_v1.Arcface.util.DrawHelper;
import com.example.temi_v1.Arcface.widget.FaceRectView;
import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.UI.Base.SwitcherView;
import com.example.temi_v1.UI.activity.Booth.BoothDetailsActivity;
import com.example.temi_v1.UI.activity.Schedule.SchedulingListActivity;
import com.example.temi_v1.app.AppApplication;
import com.example.temi_v1.bean.AlarmItem;
import com.example.temi_v1.service.listener.BroadcastListener;
import com.example.temi_v1.util.AlarmDataTool;
import com.example.temi_v1.util.Constant;
import com.example.temi_v1.util.RobotTools;
import com.example.temi_v1.util.SaveData;
import com.robotemi.sdk.TtsRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//import com.example.temi_v1.util.SpeakTool;

public class MainActivity extends BaseActivity implements View.OnClickListener, BroadcastListener.StateChangeListener, ViewTreeObserver.OnGlobalLayoutListener {
    public static final String ALARM_ID = "id";
    Button mButton, exhibition_btn;
    public static final String KEY_TEMI_NLP_RESPONSE = "TemiNlpResponse";
    private TtsRequest pendingTts;
    int[] src = {R.mipmap.screensaver1,R.mipmap.screensaver2,R.mipmap.screensaver3,R.mipmap.screensaver4,R.mipmap.screensaver6,R.mipmap.screensaver7,R.mipmap.screensaver8,R.mipmap.screensaver9,R.mipmap.screensaver10,R.mipmap.screensaver11,R.mipmap.screensaver12,R.mipmap.screensaver13,R.mipmap.screensaver14,R.mipmap.screensaver15,R.mipmap.screensaver16};
    boolean bol = false;//控制表情是否切换
    ImageView expression;
    boolean speak = true;
    SwitcherView noticetext;
    Timer timer = new Timer();
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    /**
     * 所需的所有权限信息
     */
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
    };
    private BroadcastListener mBroadcastListener = null;

    private static final String TAG = "PreviewActivity";
    private CameraHelper cameraHelper;
    private DrawHelper drawHelper;
    private Camera.Size previewSize;
    private Integer cameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private FaceEngine faceEngine;
    private int afCode = -1;
    private int processMask = FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS;
    private Toast toast = null;
    /**
     * 相机预览显示的控件，可为SurfaceView或TextureView
     */
    private View previewView;
    private FaceRectView faceRectView;

    @Override
    protected void initView() {
        mButton = findViewById(R.id.workbtn);
        exhibition_btn = findViewById(R.id.exhibition_btn);
        noticetext = findViewById(R.id.noticetext);
        expression = findViewById(R.id.expression);
        bol = true;
        mButton.setOnClickListener(this);
        noticetext.setOnClickListener(this);
        exhibition_btn.setOnClickListener(this);

        changExpression();
        initNotice();
        if (null == mBroadcastListener) {
            mBroadcastListener = new BroadcastListener(this, this);
        }
//        activeEngine();
//        faceArce();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.workbtn:
                //准备跳转工作模式页面
                Intent workmodel = new Intent(this, WorkModelActivity.class);
                //跳转到登陆页面
                startActivity(workmodel);
                break;
            case R.id.exhibition_btn:
                Intent exhibition = new Intent(this, ExhibitionModeActivity.class);
                //跳转到展厅模式页面
                startActivity(exhibition);
                break;
            case R.id.noticetext:
                Intent schedul = new Intent(this, SchedulingListActivity.class);
                //跳转到日程安排页面
                startActivity(schedul);
                break;
        }
    }
    //初始人脸识别，开始识别
    private void initEngine() {
        faceEngine = new FaceEngine();
        afCode = faceEngine.init(this.getApplicationContext(), FaceEngine.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(this),
                16, 20, FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS);
        VersionInfo versionInfo = new VersionInfo();
        faceEngine.getVersion(versionInfo);
        Log.i(TAG, "initEngine:  init: " + afCode + "  version:" + versionInfo);
        if (afCode != ErrorInfo.MOK) {
            Toast.makeText(this, getString(R.string.init_failed, afCode), Toast.LENGTH_SHORT).show();
        }
    }

    private void unInitEngine() {
        if (afCode == 0) {
            afCode = faceEngine.unInit();
            Log.i(TAG, "unInitEngine: " + afCode);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mBroadcastListener.onStart();
    }

    List<AlarmItem> list;
    /**
     * 初始化通知动画
     */
    private void initNotice() {
        ArrayList<String> strs = new ArrayList<>();
        strs.clear();
        list = AlarmDataTool.loadData();
        for (AlarmItem mlist:list){
            strs.add(mlist.getTime()+"一个日程=>"+mlist.getTitle());
        }
        noticetext.setResource(strs);
        noticetext.startRolling();
    }
    //定时换表情
    private void changExpression() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (bol) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int index = new Random().nextInt(src.length);
                            expression.setImageResource(src[index]);
                        }
                    });
                }
            }
        }, 2000, 4000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bol = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        bol = false;
    }

    @Override
    public void onTtsStatusChanged(TtsRequest ttsRequest) {
        super.onTtsStatusChanged(ttsRequest);
        String s = ttsRequest.getSpeech();
        Log.e("ddd", s + "======");
    }

    @Override
    public void onRobotReady(boolean b) {
        super.onRobotReady(b);
        if (b) {
           //激活人脸识别权限
           activeEngine();
           faceArce();
        }
    }

    private void faceArce() {
        ////////////////////////隔离，下面属于人脸识别部分//////////////////////////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            getWindow().setAttributes(attributes);
        }

        // Activity启动后就锁定为启动时的方向
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            default:
                break;
        }
        previewView = findViewById(R.id.texture_preview);
        faceRectView = findViewById(R.id.face_rect_view);
        //在布局结束后才做初始化操作
        previewView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        ////////////////////////隔离，上面属于人脸识别部分//////////////////////////
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 激活引擎
     */
    public void activeEngine() {
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                FaceEngine faceEngine = new FaceEngine();
                int activeCode = faceEngine.active(MainActivity.this, Constants.APP_ID, Constants.SDK_KEY);
                emitter.onNext(activeCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
                            showToast(getString(R.string.active_success));
                        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
                            Log.e("引擎已激活", getString(R.string.already_activated));
                        } else {
                            showToast(getString(R.string.active_failed, activeCode));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(s);
            toast.show();
        }
    }

    //获取权限
    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this.getApplicationContext(), neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    //初始化相机
    private void initCamera() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                Log.i(TAG, "onCameraOpened: " + cameraId + "  " + displayOrientation + " " + isMirror);
                previewSize = camera.getParameters().getPreviewSize();
                drawHelper = new DrawHelper(previewSize.width, previewSize.height, previewView.getWidth(), previewView.getHeight(), displayOrientation
                        , cameraId, isMirror);
            }


            @Override
            public void onPreview(byte[] nv21, Camera camera) {

                if (faceRectView != null) {
                    faceRectView.clearFaceInfo();
                }
                List<FaceInfo> faceInfoList = new ArrayList<>();
                int code = faceEngine.detectFaces(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList);
                if (code == ErrorInfo.MOK && faceInfoList.size() > 0) {
                    code = faceEngine.process(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList, processMask);
                    if (code != ErrorInfo.MOK) {
                        return;
                    }
                } else {
                    return;
                }

                List<AgeInfo> ageInfoList = new ArrayList<>();
                List<GenderInfo> genderInfoList = new ArrayList<>();
                List<Face3DAngle> face3DAngleList = new ArrayList<>();
                List<LivenessInfo> faceLivenessInfoList = new ArrayList<>();
                int ageCode = faceEngine.getAge(ageInfoList);
                int genderCode = faceEngine.getGender(genderInfoList);
                int face3DAngleCode = faceEngine.getFace3DAngle(face3DAngleList);
                int livenessCode = faceEngine.getLiveness(faceLivenessInfoList);

                //有其中一个的错误码不为0，return
                if ((ageCode | genderCode | face3DAngleCode | livenessCode) != ErrorInfo.MOK) {
                    return;
                }
                if (faceRectView != null && drawHelper != null) {
                    List<DrawInfo> drawInfoList = new ArrayList<>();
                    for (int i = 0; i < faceInfoList.size(); i++) {
                        drawInfoList.add(new DrawInfo(faceInfoList.get(i).getRect(), genderInfoList.get(i).getGender(), ageInfoList.get(i).getAge(), faceLivenessInfoList.get(i).getLiveness(), null));
                    }
                    if (drawInfoList.size() > 0 && speak) {
                        mTimer.start();//设置时间控制，只有超过10秒没有捕获到人脸，才会提示
                        String dumpstr = "你好呀";
                        String getStr = SaveData.getGuideData("迎宾词");
                        if (!getStr.equals("null")) {
                            dumpstr = getStr;
                        }
                        robot.speak(TtsRequest.create(dumpstr, false));
                        gotoCompanyTip();
                        //向前移动
                        drawHelper.draw(faceRectView, drawInfoList);
                        speak = false;
                    } else {
                        mTimer.cancel();
                        mTimer.start();
                    }

                }
            }

            /** 倒计时10秒，一次1秒 *///倒计时控制
            CountDownTimer mTimer = new CountDownTimer(Constant.downTime10 * 1000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    speak = true;
                }
            };

            @Override
            public void onCameraClosed() {
                Log.i(TAG, "onCameraClosed: ");
            }

            @Override
            public void onCameraError(Exception e) {
                Log.i(TAG, "onCameraError: " + e.getMessage());
            }

            @Override
            public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {
                if (drawHelper != null) {
                    drawHelper.setCameraDisplayOrientation(displayOrientation);
                }
                Log.i(TAG, "onCameraConfigurationChanged: " + cameraID + "  " + displayOrientation);
            }
        };
        cameraHelper = new CameraHelper.Builder()
                .previewViewSize(new Point(previewView.getMeasuredWidth(), previewView.getMeasuredHeight()))
                .rotation(getWindowManager().getDefaultDisplay().getRotation())
                .specificCameraId(cameraID != null ? cameraID : Camera.CameraInfo.CAMERA_FACING_FRONT)
                .isMirror(false)
                .previewOn(previewView)
                .cameraListener(cameraListener)
                .build();
        cameraHelper.init();
    }
    ///倒计时控制
    CountDownTimer misstip = new CountDownTimer(Constant.downTime20 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            downtime.setText(l/1000+"");
        }

        @Override
        public void onFinish() {
            builder.dismiss();
        }
    };
    TextView downtime;
    AlertDialog builder;
    /**
     * 主动迎宾提示提示
     */
    private void gotoCompanyTip() {
        builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.topgoaddress);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        downtime = (TextView) builder.findViewById(R.id.downtime);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);//确定删除按钮
        if (msg == null || cancle == null || sure == null) return;
        msg.setText(SaveData.getGuideData("迎宾词"));
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                misstip.cancel();
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCompany();//介绍所有地址
                misstip.cancel();
                builder.dismiss();
            }
        });

        misstip.start();
    }

    //权限请求回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (isAllGranted) {
                initEngine();
                initCamera();
                if (cameraHelper != null) {
                    cameraHelper.start();
                }
            } else {
                Toast.makeText(this.getApplicationContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
            }

        }
    }


    /**
     * 在{@link #previewView}第一次布局完成后，去除该监听，并且进行引擎和相机的初始化
     */
    @Override
    public void onGlobalLayout() {
        previewView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
        } else {
            initEngine();
            initCamera();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        mBroadcastListener.onDestory();
        ///////////////人脸识别///////////////
        if (cameraHelper != null) {
            cameraHelper.release();
            cameraHelper = null;
        }
        unInitEngine();
        ///////////////人脸识别///////////////
    }

    //广播监听
    @Override
    public void mynotice(Intent intent) {
        List<String> loca = RobotTools.getLocations();
        //获取地点广播
        String state = intent.getStringExtra(Constant.addressKey);
        if (state.equals(Constant.address) && !Constant.alladdressKey) {//单点介绍
            Constant.alladdressKey=false;
            //准备跳转展位介绍
            Intent workmodel = new Intent(AppApplication.getInstance(), BoothDetailsActivity.class);
            workmodel.putExtra(Constant.titless, state);
            workmodel.putExtra(Constant.bolAddress, "true");
            workmodel.putExtra(Constant.clicks,1000);//单点介绍
            workmodel.putExtra(Constant.boothDetail, SaveData.getGuideData(state));
            //跳转到登陆页面
            startActivity(workmodel);
            Log.e("loca", "mynotice");
        }
        if (state.equals(Constant.address) && Constant.alladdressKey) {//全区介绍
            Constant.alladdressKey=true;
            //准备跳转展位介绍
            Intent workmodel = new Intent(AppApplication.getInstance(), BoothDetailsActivity.class);
            workmodel.putExtra(Constant.titless, state);
            workmodel.putExtra(Constant.bolAddress, "true");
            workmodel.putExtra(Constant.clicks,2000);//全局介绍
            workmodel.putExtra(Constant.boothDetail, SaveData.getGuideData(state));
            //跳转到登陆页面
            startActivity(workmodel);
            Log.e("loca", "mynotice");
        }
        int intState = intent.getIntExtra(Constant.clicks,20);
//        if (intState==Constant.intStat3000) {//最后⼀个点位介绍完，机器⼈随机话术
//            String[] str={"我已经带您参观完啦，有什么感兴趣的吗","我们产品都看完了，您喜欢什么呀","参观结束了，要不要跟我玩玩呀"};
//
//            int index = new Random().nextInt(str.length);//随机取其中一个话术
//            robot.speak(TtsRequest.create(str[index], false));
//            Log.e("loca", "mynotice");
//        }
//        if (intState==Constant.intStat4000) {//在导航前，机器⼈随机话术
//            String[] str={"请您跟我来","我带您继续参观","您跟我看看下个地⽅吧","去看看下⼀个展位吧"};
//            int index = new Random().nextInt(str.length);//随机取其中一个话术
//            robot.speak(TtsRequest.create(str[index], false));
//            Log.e("loca", "mynotice");
//        }

    }
}
