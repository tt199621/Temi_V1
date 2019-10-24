package com.example.temi_v1.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temi_v1.R;
import com.example.temi_v1.bean.AlarmItem;

/**
 * 工作列表
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午5:01.
 */

public class WorkListAdapter extends BaseQuickAdapter<AlarmItem, BaseViewHolder> {
    public WorkListAdapter(int layoutResId) {
        super(layoutResId);
    }
    private WorkListAdapter.OnItemClickListener mOnItemClickListener;
    @Override
    protected void convert(final BaseViewHolder helper, final AlarmItem item) {
        TextView work_time=helper.getView(R.id.work_time);
        TextView work_remarks=helper.getView(R.id.work_remarks);
        TextView appointedPerson=helper.getView(R.id.appointedPerson);
        TextView appointedAddress=helper.getView(R.id.appointedAddress);
        ImageView header = helper.getView(R.id.header);

        switch (item.getTypeStr()) {
            case "工作":
                header.setImageResource(R.mipmap.work);
                break;
            case "学习":
                header.setImageResource(R.mipmap.study);
                break;
            case "私事":
                header.setImageResource(R.mipmap.privates);
                break;
            case "其它":
                header.setImageResource(R.mipmap.other);
                break;
        }
        appointedPerson.setText("联系人:"+item.getWakeType());
        appointedAddress.setText("地点:"+item.getRing());
        work_remarks.setText("备注:"+item.getTitle());
        work_time.setText("时间:"+item.getTime());
        ImageView delbtn = helper.getView(R.id.delbtn);

        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("IDList",item.getIDList()+"===");
                mOnItemClickListener.onItemClickListenerss(item.getIDList());
            }
        });

    }
    public void setOnItemClickListenerss(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListenerss(int pos);
    }
}
