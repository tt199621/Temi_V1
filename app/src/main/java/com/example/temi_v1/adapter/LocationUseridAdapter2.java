package com.example.temi_v1.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temi_v1.R;
import com.robotemi.sdk.UserInfo;

import java.net.URL;

/**
 * 查看用户所有地址适配器
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午5:01.
 */

public class LocationUseridAdapter2 extends BaseQuickAdapter<UserInfo, BaseViewHolder> {
    public LocationUseridAdapter2(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfo item) {
        String url = item.getPicUrl();
        String name = item.getName();
        ImageView header = helper.getView(R.id.header);
        TextView namestr = helper.getView(R.id.name);

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.header)//图片加载出来前，显示的图片
                .fallback(R.mipmap.header) //url为空的时候,显示的图片
                .error(R.mipmap.header);//图片加载失败后，显示的图片
        Log.e("=======", url + "=====");
        Glide.with(mContext).load(url).apply(options).into(header);
        namestr.setText(name);
    }
}
