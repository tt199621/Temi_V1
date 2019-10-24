package com.example.temi_v1.service.listener;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.fragment.app.Fragment;

/**
 * Created by Shinelon-LJL on 2019/9/20
 */
public class BroadcastListener  {
    public static String ACTION="myaction";
    public static String ACTIONBody="myactionbody";
    Activity activity;
    StateChangeListener stateChangeListener;
    MyPriveReceiver myReceiver;

    /**
     * 实例化构造器，设置监听回调
     * @param activity
     * @param stateChangeListener
     */
    public BroadcastListener(Activity activity, StateChangeListener stateChangeListener) {
        this.activity=activity;
        this.stateChangeListener=stateChangeListener;
    }
    public BroadcastListener(Service service, StateChangeListener stateChangeListener) {
        this.mService=service;
        this.stateChangeListener=stateChangeListener;
    }
    public BroadcastListener(Fragment fragment, StateChangeListener stateChangeListener) {
        this.activity=fragment.getActivity();
        this.stateChangeListener=stateChangeListener;
    }
    Service mService;

    /**
     * 启动广播
     */
    public void onStart(){
        myReceiver=new MyPriveReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION);
        if(null!=myReceiver&&null!=activity){
            activity.registerReceiver(myReceiver,intentFilter);
        }
    }
    /**
     * 启动广播
     */
    public void onServiceStartBroad(){
        myReceiver=new MyPriveReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION);
        if(null!=myReceiver&&null!=activity){
            mService.registerReceiver(myReceiver,intentFilter);
        }
    }

    /**
     * 消除广播
     */
    public void onDestory(){
        if(null!=myReceiver&&null!=activity){
            activity.unregisterReceiver(myReceiver);
        }
        myReceiver=null;
    }

    /**
     * 定义一个内部广播
     */
    public class MyPriveReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(BroadcastListener.ACTION)){
                int state=intent.getIntExtra("state",0);
                stateChangeListener.mynotice(intent);
               /* if(state==0){
                    if(null!=stateChangeListener){
                        String body=intent.getStringExtra("body");
                        stateChangeListener.mynotice(body);
                    }
                }else if(state==1){
                    if(null!=stateChangeListener){
                        stateChangeListener.mynoticeLoadMePage();
                    }
                }*//*else if(state==2){
                    if(null!=stateChangeListener){
                        stateChangeListener.mystop();
                    }
                }*/
            }
        }
    }
    public interface StateChangeListener{
        void mynotice(Intent intent);
        //void mynoticeLoadMePage();
        //void mystop();
    }
}