package com.example.temi_v1.app;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.temi_v1.R;
import com.example.temi_v1.service.MyService;
import com.example.temi_v1.service.listener.BroadcastListener;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.PermissionListener;
import com.yhao.floatwindow.Screen;
import com.yhao.floatwindow.ViewStateListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shinelon-LJL on 2019/6/19
 */
public class AppApplication extends Application{
    private static final String TAG = "FloatWindow";
    /**
     * 存储Activity的集合
     */
    private static List<Activity> activities = new ArrayList<>();
    /**
     * 添加Activity进集合
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }
    /**
     * 将Activity移除集合
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }
    private static Context mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initFloat();
        Intent i = new Intent(this, MyService.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(i);
//        } else {
            startService(i);
//        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Intent i = new Intent(this, MyService.class);
        stopService(i);
    }

    private void initFloat() {
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.mipmap.logo);
        Class[]  b=new Class[activities.size()];
        FloatWindow//悬浮窗
                .with(getApplicationContext())
                .setView(imageView)
                .setWidth(Screen.width, 0.1f) //设置悬浮控件宽高
                .setHeight(Screen.width, 0.1f)
                .setX(Screen.width, 0.8f)
                .setY(Screen.height, 0.7f)
                .setMoveType(MoveType.slide,100,100)
                .setMoveStyle(500, new BounceInterpolator())
                .setFilter(true,activities.toArray(b))//传入所有的Activity,过滤掉达到让其不再应用内显示
                .setViewStateListener(mViewStateListener)
                .setPermissionListener(mPermissionListener)
                .setDesktopShow(true)
                .build();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里是指从后台返回到前台  前两个的是关键
                gototherApp("com.example.temi_v1","com.example.temi_v1.UI.activity.ExhibitionModeActivity");
            }
        });
    }
    /**
     *
     * @param packages
     * @param activityPath
     */
    private void gototherApp(String packages,String activityPath) {
        Log.e("=====", "=====");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packages,
                activityPath);
        intent.setComponent(cn);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivityInfo(getPackageManager(), PackageManager.MATCH_DEFAULT_ONLY) != null) {//启动的intent存在
            startActivity(intent);
        } else {
            Toast.makeText(this, "应用未安装", Toast.LENGTH_SHORT).show();
        }
    }
    private ViewStateListener mViewStateListener = new ViewStateListener() {
        @Override
        public void onPositionUpdate(int x, int y) {
            Log.d(TAG, "onPositionUpdate: x=" + x + " y=" + y);
        }

        @Override
        public void onShow() {
            Log.d(TAG, "onShow");
        }

        @Override
        public void onHide() {
            Log.d(TAG, "onHide");
        }

        @Override
        public void onDismiss() {
            Log.d(TAG, "onDismiss");
        }

        @Override
        public void onMoveAnimStart() {
            Log.d(TAG, "onMoveAnimStart");
        }

        @Override
        public void onMoveAnimEnd() {
            Log.d(TAG, "onMoveAnimEnd");
        }

        @Override
        public void onBackToDesktop() {
            Log.d(TAG, "onBackToDesktop");
        }
    };

    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "onSuccess");
        }

        @Override
        public void onFail() {
            Log.d(TAG, "onFail");
        }
    };
    public static Context getInstance() {
        return mInstance;
    }
    /**
     * 销毁所有的Actiityv
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
