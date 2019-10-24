package com.example.temi_v1.UI.activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.adapter.LocationUseridAdapter;
import com.example.temi_v1.adapter.LocationUseridAdapter2;
import com.example.temi_v1.util.Tools;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.UserInfo;
import com.robotemi.sdk.model.RecentCallModel;
import java.util.List;

/**
 * 联系人界面
 */
public class ContactActivity extends BaseActivity {
    private LocationUseridAdapter2 mOrderAdapter;
    private LocationUseridAdapter mOrderAdapterssss;
    public SwipeRefreshLayout mRefreshLayout;

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
        mOrderAdapter = new LocationUseridAdapter2(R.layout.addres_order_item);
        mOrderAdapter.setHeaderAndEmpty(true);
        mOrderAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mOrderAdapter.setEnableLoadMore(true);
        mOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startTelepresence(list.get(position));
            }
        });

        mRecyclerView.setAdapter(mOrderAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cancelRef();
        addheader();
        addData();
    }

    /**
     * 拨打电话
     * @param userInfo
     */
    private void startTelepresence(UserInfo userInfo) {
        Log.d("TAG", "startTelepresence with " + userInfo);
        if (userInfo != null) {
            Robot.getInstance().startTelepresence(userInfo.getName(), userInfo.getUserId());
        }
    }

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
    //添加头部
    private void addheader() {
        headerOne();
        headerTwo();
        headerThree();
    }
    //头样式----最近通话
    private void headerOne() {
        ConstraintLayout linearLayout = (ConstraintLayout) LayoutInflater.from(this).inflate(
                R.layout.video_summerized2, null);
        mOrderAdapter.addHeaderView(linearLayout);
    }

    /**
     * 历史联系人
     */
    private void headerTwo() {
        RecyclerView recyclerView = new RecyclerView(this);

        //历史联系人
        mOrderAdapterssss = new LocationUseridAdapter(R.layout.addres_order_item2);
        mOrderAdapterssss.setHeaderAndEmpty(true);
        mOrderAdapterssss.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mOrderAdapterssss.setEnableLoadMore(true);
        recyclerView.setAdapter(mOrderAdapterssss);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        /**
         * 获取联系人历史记录
         */
        final List<RecentCallModel> recen = Robot.getInstance().getRecentCalls();
        mOrderAdapterssss.setNewData(recen);
        mOrderAdapterssss.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                UserInfo userInfo = Tools.selectUserInfo(recen.get(position));
                if (null != userInfo) {
                    startTelepresence(userInfo);
                    Log.e("name", userInfo.getName() + "====");
                }
            }
        });
        mOrderAdapter.addHeaderView(recyclerView);
    }

    private void headerThree() {
        ConstraintLayout linearLayout = (ConstraintLayout) LayoutInflater.from(this).inflate(
                R.layout.video_summerized3, null);

        mOrderAdapter.addHeaderView(linearLayout);
    }

    List<UserInfo> list;

    private void addData() {
        list = Robot.getInstance().getAllContact();

        mOrderAdapter.setNewData(list);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact;
    }
}
