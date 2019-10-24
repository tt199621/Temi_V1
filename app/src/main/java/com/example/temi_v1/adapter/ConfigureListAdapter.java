package com.example.temi_v1.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temi_v1.R;
import com.example.temi_v1.bean.ConfigListBean;
import com.example.temi_v1.bean.WorkListBean;

/**
 * 配置列表
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午5:01.
 */

public class ConfigureListAdapter extends BaseQuickAdapter<ConfigListBean, BaseViewHolder> {
    public ConfigureListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConfigListBean item) {
        TextView title=helper.getView(R.id.title);
        title.setText(item.getTitle());
    }
}
