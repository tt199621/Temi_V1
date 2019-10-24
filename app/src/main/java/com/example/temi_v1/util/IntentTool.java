package com.example.temi_v1.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.temi_v1.R;
import com.example.temi_v1.UI.activity.ConfigureActivity;
import com.example.temi_v1.app.AppApplication;

/**
 * Created by Shinelon-LJL on 2019/9/18
 */
public class IntentTool {
    /**
     * 退出登陆提示
     */
    static String pwd="";//输入的密码
    static String defulpwd="4455";//默认密码

    //bolen=true修改密码,bolen=false登陆
    public static void inputPwd(final Context context,final boolean bolen) {
        String defpwd=SaveData.getGuideData("defulpwd");
        if (!defpwd.equals("null")){
            defulpwd=defpwd;
        }
        final AlertDialog builder = new AlertDialog.Builder(context)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setLayout(1300,600);
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局

        final TextView inputpwd = builder.findViewById(R.id.inputpwd);
        if (bolen){//设置默认提示
            pwd=defulpwd;
           inputpwd.setText(pwd);
        }
        Button button1 = builder.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.length()>10){
                    return;
                }
                pwd += "1";
                inputpwd.setText(pwd);
            }
        });
        Button button2 = builder.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.length()>10){
                    return;
                }
                pwd += "2";
                inputpwd.setText(pwd);
            }
        });
        Button button3 = builder.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.length()>10){
                    return;
                }
                pwd += "3";
                inputpwd.setText(pwd);
            }
        });
        Button button4 = builder.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.length()>10){
                    return;
                }
                pwd += "4";
                inputpwd.setText(pwd);
            }
        });
        Button button5 = builder.findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.length()>10){
                    return;
                }
                pwd += "5";
                inputpwd.setText(pwd);
            }
        });
        Button button6 = builder.findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.length()>10){
                    return;
                }
                pwd += "6";
                inputpwd.setText(pwd);
            }
        });
        Button button7 = builder.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.length()>10){
                    return;
                }
                pwd += "7";
                inputpwd.setText(pwd);
            }
        });
        Button button8 = builder.findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.length()>10){
                    return;
                }
                pwd += "8";
                inputpwd.setText(pwd);
            }
        });
        Button button9 = builder.findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.length()>10){
                    return;
                }
                pwd += "9";
                inputpwd.setText(pwd);
            }
        });
        Button button0 = builder.findViewById(R.id.button0);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.length()>10){
                    return;
                }
                pwd += "0";
                inputpwd.setText(pwd);
            }
        });
        Button enterbtn = builder.findViewById(R.id.enterbtn);
        enterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.equals("00000000")){//后台密码
                    toConfigure(context);
                }
                if (!pwd.equals(defulpwd)&&!bolen){
                    return;
                }
                if(bolen){
                    SaveData.setGuideData("defulpwd",pwd);
                    Toast.makeText(AppApplication.getInstance(),"修改成功",Toast.LENGTH_SHORT).show();
                }else {
                    toConfigure(context);
                }
                pwd="";//恢复默认值
                builder.dismiss();
            }
        });
        Button buttondel = builder.findViewById(R.id.buttondel);
        buttondel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.length()==0){
                    return;
                }
                pwd=pwd.substring(0,pwd.length()-1);
                inputpwd.setText(pwd);
            }
        });
    }

    //跳转到配置页面
    private static void toConfigure(Context context) {
        Intent exhibition = new Intent(AppApplication.getInstance(), ConfigureActivity.class);
        //跳转到配置页面
        context.startActivity(exhibition);
    }


}
