package com.example.temi_v1.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temi_v1.R;
import com.example.temi_v1.util.SaveData;

import java.util.List;

/**
 * 展厅介绍列表
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午5:01.
 */
public class ExhibitionListAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {
    private OnItemClickListener mOnItemClickListener;

    public ExhibitionListAdapter(List<String> data) {
        super(R.layout.exhibition_list_item,data);
    }

//    public ExhibitionListAdapter(int layoutResId) {
//        super(layoutResId);
//    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        TextView title=helper.getView(R.id.titile);
        title.setText(item);
        ImageView delbtn = helper.getView(R.id.delbtn);
        TextView guidecontent = helper.getView(R.id.guidecontent);
        guidecontent.setText(SaveData.getGuideData(item));
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
