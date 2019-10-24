//package com.example.temi_v1.util;
//
//import android.util.Log;
//
//import com.example.temi_v1.app.AppApplication;
//import com.robotemi.sdk.Robot;
//import com.robotemi.sdk.TtsRequest;
//import com.robotemi.sdk.listeners.OnRobotReadyListener;
//
///**
// * Created by Shinelon-LJL on 2019/9/25
// */
//public class SpeakTool implements OnRobotReadyListener {
//    private static Robot robot = null;
//    private  SpeakTool(){
//        if (robot == null) {
//            robot = Robot.getInstance();
//        }
//
//    }
//    private static SpeakTool speakTool=null;
//    public static SpeakTool getInstance(){
//        if (speakTool==null){
//            speakTool=new SpeakTool();
//        }
//        return speakTool;
//    }
//    TtsRequest ttsRequest=null;
//    //说话
//    public void SpeakStr(String str){
//        if (ttsRequest==null){
//            ttsRequest=TtsRequest.create(str, false);
//        }
//        robot.speak(ttsRequest);
//    }
//    //取消说话
//    public void cancelSpeak(){
//        if (ttsRequest!=null){
//            robot.cancelAllTtsRequests();
//            ttsRequest=null;
//        }
//    }
//    public void goTO(String str){
//        robot.goTo(str);
//    }
////    addOnRobotReadyListener
//    public void setOnRobtReadyListener(){
//        robot.addOnRobotReadyListener(new OnRobotReadyListener() {
//            @Override
//            public void onRobotReady(boolean b) {
//
//            }
//        });
//
//    }
//
//    @Override
//    public void onRobotReady(boolean b) {
//        Log.e("","");
//    }
//}
