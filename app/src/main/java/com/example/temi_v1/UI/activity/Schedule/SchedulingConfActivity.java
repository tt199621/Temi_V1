package com.example.temi_v1.UI.activity.Schedule;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.UI.Base.DividerItemDecoration;
import com.example.temi_v1.adapter.WorkListAdapter;
import com.example.temi_v1.bean.AlarmItem;
import com.example.temi_v1.data.MyAlarmDataBase;
import com.example.temi_v1.model.AlarmModel;
import com.example.temi_v1.util.AlarmDataTool;
import com.example.temi_v1.util.Constant;
import com.gc.materialdesign.views.ButtonFloat;

import java.util.List;

/**
 * 日程安排
 */
public class SchedulingConfActivity extends BaseActivity implements View.OnClickListener {
    public SwipeRefreshLayout mRefreshLayout;
    private WorkListAdapter mOrderAdapter;
    ButtonFloat buttonFloat;
    private MyAlarmDataBase db ;
    @Override
    protected void initView() {
        ImageView finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRefreshLayout = findViewById(R.id.listview);
        buttonFloat = findViewById(R.id.buttonFloat);
        buttonFloat.setOnClickListener(this);
        db = new MyAlarmDataBase(this);
        RecyclerView mRecyclerView = findViewById(R.id.fg_menu_rcy);
        mOrderAdapter = new WorkListAdapter(R.layout.work_list_item);

        mOrderAdapter.setHeaderAndEmpty(true);
        mOrderAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mOrderAdapter.setEnableLoadMore(true);
        //可插拔设计
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setAdapter(mOrderAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOrderAdapter.setOnItemClickListener(mListener);
//        workList();
        cancelRef();

    }

    @Override
    protected void initData() {
        super.initData();
        mOrderAdapter.setOnItemClickListenerss(new WorkListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListenerss(int pos) {
                AlarmModel am = db.getAlarm(pos);//删除当个闹钟
                db.deleteAlarm(am);
                list = AlarmDataTool.loadData();
                mOrderAdapter.setNewData(list);
                mOrderAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 工作计划列表点击监听
     */
    BaseQuickAdapter.OnItemClickListener mListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            Intent schedule = new Intent(SchedulingConfActivity.this, ScheduleConfDetailActivity.class);
            schedule.putExtra(Constant.activityId,list.get(position).IDList);
            startActivity(schedule);
        }
    };

    /**
     * 取消刷新
     */
    private void cancelRef() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //延迟执行
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }
    List<AlarmItem> list;
    @Override
    protected void onResume() {
        super.onResume();
        //日程安排列表
        list = AlarmDataTool.loadData();
        mOrderAdapter.setNewData(list);
        mOrderAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scheduling_item;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonFloat:
                Intent schedule = new Intent(SchedulingConfActivity.this, ScheduleConfDetailActivity.class);
                startActivity(schedule);
                break;
        }
    }
}
