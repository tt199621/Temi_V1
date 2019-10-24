package com.example.temi_v1.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.temi_v1.app.AppApplication;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Shinelon-LJL on 2019/9/19
 */
public class SaveData {
    private static SharedPreferences mSp = AppApplication.getInstance()
            .getSharedPreferences(Constant.USER_STATE, Context.MODE_PRIVATE);

    /**
     * Description: 设置导览数据
     */
    public static void setGuideData(String key, String data) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putString(key, data);
        editor.apply();
    }

    public static String getGuideData(String key) {
        return mSp.getString(key, "null");
    }

    public static void saveAddressToJson(String[] address){
        if (address.length<=1){//当只有一个地址时（充电桩）,不导航
            return;
        }
        Constant.alladdress = address;//给地址赋值
        //然后把地址保存起来
        Gson gson2=new Gson();
        String strAddressJson=gson2.toJson(address);//把list地址数据转json,然后保存起来
        SaveData.setGuideData(Constant.saveAddressKey,strAddressJson);
    }
    public static void saveAddressToJson(List<String> location){

        Log.e("location", location.size() + "");
        String[] lis = new String[location.size()];//获取赋值地址数据
        for (int i = 0; i < lis.length; i++) {
            lis[i] = location.get(i);
        }
        if (lis.length<=1){//当只有一个地址时（充电桩）,不导航
            return;
        }
        Constant.alladdress = lis;//给地址赋值
        //然后把地址保存起来
        Gson gson2=new Gson();
        String strAddressJson=gson2.toJson(location);//把list地址数据转json,然后保存起来
        SaveData.setGuideData(Constant.saveAddressKey,strAddressJson);
    }
}
