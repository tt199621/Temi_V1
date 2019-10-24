package com.example.temi_v1.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temi_v1.R;
import com.example.temi_v1.bean.BusinessListBean;
import com.example.temi_v1.bean.WorkListBean;
import com.example.temi_v1.util.SaveData;

import java.util.List;

/**
 * 业务问答列表
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午5:01.
 */
public class BusinessListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
private OnItemClickListener mOnItemClickListener;
    public BusinessListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        TextView titile=helper.getView(R.id.titile);
        TextView titile_body=helper.getView(R.id.titile_body);
        titile.setText(item+"");
        titile_body.setText(SaveData.getGuideData(item) +"");

        ImageView delbtn=helper.getView(R.id.delbtn);
        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(helper.getAdapterPosition());
            }
        });
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClickListener(int pos);
    }
}
