package com.example.temi_v1.UI.activity.Schedule;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.UI.Base.DividerItemDecoration;
import com.example.temi_v1.UI.activity.WorkDetailsActivity;
import com.example.temi_v1.adapter.WorkListConfAdapter;
import com.example.temi_v1.bean.AlarmItem;
import com.example.temi_v1.model.AlarmModel;
import com.example.temi_v1.service.AlarmService;
import com.example.temi_v1.util.AlarmDataTool;
import com.example.temi_v1.util.Constant;

import java.util.List;

/**
 * 日程安排
 */
public class SchedulingListActivity extends BaseActivity {
    public SwipeRefreshLayout mRefreshLayout;
    private WorkListConfAdapter mOrderAdapter;


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

        RecyclerView mRecyclerView = findViewById(R.id.fg_menu_rcy);
        mOrderAdapter = new WorkListConfAdapter(R.layout.work_list_conf_item);
        mOrderAdapter.setHeaderAndEmpty(true);
        mOrderAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mOrderAdapter.setEnableLoadMore(true);
        //可插拔设计
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setAdapter(mOrderAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOrderAdapter.setOnItemClickListener(mListener);
        workList();
        cancelRef();
    }

    private ServiceConnection connection = null;
    private void restartAlarm( final String time, final String repeat, final int id) {

        connection = new ServiceConnection(){
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (AlarmService.MyBinder)service;
                switch (repeat){
                    case "只响一次":
                        binder.setSingleAlarm(getApplicationContext(),time,id);
                        break;
                    case "每天":
                        binder.setEverydayAlarm(getApplicationContext(),time,id);
                        break;
                    default:
                        AlarmModel model = AlarmDataTool.db.getAlarm(id);
                        String repeatCode = model.getRepeatCode();
                        binder.setDiyAlarm(getApplicationContext(),repeat,time,id, repeatCode);
                }

                Log.d("MainActivity","重启闹钟");
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("MainActivity","解绑服务");
            }
        };
        Intent intent = new Intent(SchedulingListActivity.this, AlarmService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);


        unbindService(connection);

    }
    private AlarmService.MyBinder binder;
    //取消闹钟
    public void cancelAlarm(final Context context, final int id, final AlarmModel alarm){

        connection = new ServiceConnection(){
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (AlarmService.MyBinder)service;
                binder.cancelAlarm(alarm,id, context);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("MainActivity","解绑服务");
            }
        };
        Intent intent = new Intent(this,AlarmService.class);

        bindService(intent, connection, BIND_AUTO_CREATE);

        Log.d("MainActivity", "取消闹钟");

        unbindService(connection);

    }

    /**
     * 视频列表点击监听
     */
    BaseQuickAdapter.OnItemClickListener mListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent = new Intent(SchedulingListActivity.this, WorkDetailsActivity.class);
            intent.putExtra(Constant.activityId,list.get(position).IDList);
//            intent.putExtra(Constant.setVideoId, datastr.get(position).getId());
            startActivity(intent);
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
    /**
     * 工作列表
     */
    private void workList() {
        list = AlarmDataTool.loadData();

        Log.e("list",list.size()+"=====");
        mOrderAdapter.setNewData(list);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scheduling;
    }
}
