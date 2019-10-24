package com.example.temi_v1.util;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.temi_v1.UI.activity.MainActivity;
import com.example.temi_v1.app.AppApplication;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.UserInfo;
import com.robotemi.sdk.model.RecentCallModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shinelon-LJL on 2019/9/12
 */
public class Tools {
    /**
     * 根据userid筛选userinfo
     *
     * @param recen
     * @return
     */
    public static UserInfo selectUserInfo(RecentCallModel recen) {
        final List<UserInfo> listUser = Robot.getInstance().getAllContact();
        for (UserInfo u : listUser) {
            if (recen.getUserId().equals(u.getUserId())) {
                return u;
            }
        }
        return null;
    }

    /**
     * @param packages
     * @param activityPath
     */
    public static void gototherApp(String packages, String activityPath) {
        Log.e("=====", "=====");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packages,
                activityPath);
        intent.setComponent(cn);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivityInfo(AppApplication.getInstance().getPackageManager(), PackageManager.MATCH_DEFAULT_ONLY) != null) {//启动的intent存在
            AppApplication.getInstance().startActivity(intent);
        } else {
            Toast.makeText(AppApplication.getInstance(), "应用未安装", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置EditText是否可编辑
     *
     * @param editText 设置的Edittext对象
     * @param mode     true可编辑 false:不可编辑
     */
    public static void setEditTextEnable(AppCompatEditText editText, boolean mode) {
        editText.setFocusable(mode);
        editText.setFocusableInTouchMode(mode);
        editText.setLongClickable(mode);//设置是否可以长按
        editText.setInputType(mode ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_NULL);
    }
}
