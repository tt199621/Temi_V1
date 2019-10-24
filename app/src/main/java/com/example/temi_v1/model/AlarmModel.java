package com.example.temi_v1.model;

/**
 *
 * Created by Administrator on 2016/3/4.
 */
public class AlarmModel {

    private int mID;
    private String work_remarks;//备注
    private String mTime;
    private String mRepeatType;//"只响一次", "每天", "周一至周五", "周六周日", "自定义"
    private String mRepeatCode;
    private String mActive;
    private String typeStr;//类型
    private String mWakeType;//约定人
    private String mRing;//地点

    public AlarmModel(int ID, String contentstr, String Time, String TypeStr, String WakeType,
                      String RepeatType, String RepeatCode, String ring, String Active){
        mID = ID;
        work_remarks = contentstr;
        mTime = Time;
        this.typeStr = TypeStr;
        mRepeatType = RepeatType;
        mRepeatCode = RepeatCode;
        mRing = ring;
        mActive = Active;
        mWakeType = WakeType;
    }

    public AlarmModel(String contentstr, String Time, String WakeType, String RepeatType,
                      String RepeatCode, String ring, String typeStr,String Active){
        work_remarks = contentstr;
        mTime = Time;
        mRepeatType = RepeatType;
        mRepeatCode = RepeatCode;
        mRing = ring;
        mActive = Active;
        mWakeType = WakeType;
        this.typeStr = typeStr;

    }

    public AlarmModel(){};

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public String getTitle() {
        return work_remarks;
    }
    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
    public void setTitle(String work_remarks) {
        this.work_remarks = work_remarks;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }


    public String getActive() {
        return mActive;
    }

    public void setActive(String mActive) {
        this.mActive = mActive;
    }

    public String getWakeType() {
        return mWakeType;
    }

    public void setWakeType(String mWakeType) {
        this.mWakeType = mWakeType;
    }

    public String getRepeatType() {
        return mRepeatType;
    }

    public void setRepeatType(String mRepeatType) {
        this.mRepeatType = mRepeatType;
    }

    public String getRepeatCode() {
        return mRepeatCode;
    }

    public void setRepeatCode(String mRepeatCode) {
        this.mRepeatCode = mRepeatCode;
    }

    public String getRing() {
        return mRing;
    }

    public void setRing(String ring) {
        mRing = ring;
    }

    @Override
    public String toString() {
        return "AlarmModel{" +
                "mID=" + mID +
                ", work_remarks='" + work_remarks + '\'' +
                ", mTime='" + mTime + '\'' +
                ", mRepeatType='" + mRepeatType + '\'' +
                ", mRepeatCode='" + mRepeatCode + '\'' +
                ", mActive='" + mActive + '\'' +
                ", mWakeType='" + mWakeType + '\'' +
                ", mRing='" + mRing + '\'' +
                '}';
    }
}
