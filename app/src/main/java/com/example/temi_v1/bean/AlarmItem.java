package com.example.temi_v1.bean;

import com.example.temi_v1.util.IntentTool;

/**
 * Created by Shinelon-LJL on 2019/9/22
 */
public class AlarmItem { //内部AlarmItem,为了方便数据处理
    public String work_remarks;
    public String mTime;


    public String mTypeStr;
    public String mRepeatType;
    public String mRepeatCode;
    public String mActive;
    public String WakeType;
    public String Ring;
    public Integer IDList;
    public String getTitle() {
        return work_remarks;
    }

    public void setTitle(String title) {
        work_remarks = title;
    }
    public String getTypeStr() {
        return mTypeStr;
    }

    public void setTypeStr(String typeStr) {
        mTypeStr = typeStr;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }
    public String getWakeType() {
        return WakeType;
    }

    public void setWakeType(String wakeType) {
        WakeType = wakeType;
    }
    public String getRepeatType() {
        return mRepeatType;
    }

    public void setRepeatType(String repeatType) {
        mRepeatType = repeatType;
    }

    public String getRepeatCode() {
        return mRepeatCode;
    }

    public void setRepeatCode(String repeatCode) {
        mRepeatCode = repeatCode;
    }

    public String getActive() {
        return mActive;
    }

    public void setActive(String active) {
        mActive = active;
    }

    public Integer getIDList() {
        return IDList;
    }

    public String getRing() {
        return Ring;
    }

    public void setRing(String ring) {
        Ring = ring;
    }
    public void setIDList(Integer IDList) {
        this.IDList = IDList;
    }
    public AlarmItem(String title, String time, String mTypeStr, String repeatNormal, String repeatDefine, String active,String  WakeType,String  Ring,Integer IDList) {
        this.work_remarks = title;
        this.mTime = time;
        this.mTypeStr = mTypeStr;
        this.mRepeatType = repeatNormal;
        this.mRepeatCode = repeatDefine;
        this.mActive = active;
        this.IDList = IDList;
        this.WakeType = WakeType;
        this.Ring = Ring;

    }

    @Override
    public String toString() {
        return "AlarmItem{" +
                "work_remarks='" + work_remarks + '\'' +
                ", mTime='" + mTime + '\'' +
                ", mRepeatType='" + mRepeatType + '\'' +
                ", mRepeatCode='" + mRepeatCode + '\'' +
                ", mActive='" + mActive + '\'' +
                '}';
    }
}