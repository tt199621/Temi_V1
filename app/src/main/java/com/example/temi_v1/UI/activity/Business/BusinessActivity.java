package com.example.temi_v1.UI.activity.Business;

import android.content.Intent;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.UI.Base.DividerItemDecoration;
import com.example.temi_v1.adapter.BusinessListAdapter;
import com.example.temi_v1.bean.BusinessListBean;
import com.example.temi_v1.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务问答
 */
public class BusinessActivity extends BaseActivity implements BusinessListAdapter.OnItemClickListener{
    public SwipeRefreshLayout mRefreshLayout;
    private BusinessListAdapter mOrderAdapter;

    @Override
    protected void initView() {
        ImageView finish=findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRefreshLayout = findViewById(R.id.listview);

        RecyclerView mRecyclerView = findViewById(R.id.fg_menu_rcy);
        mOrderAdapter = new BusinessListAdapter(R.layout.busine_list_item);
        mOrderAdapter.setOnItemClickListener(this);
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
    /**
     * 业务问答列表点击监听
     */
    BaseQuickAdapter.OnItemClickListener mListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent = new Intent(BusinessActivity.this, BusinessDestilsActivity.class);
            intent.putExtra(Constant.title, workList.get(position));
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
    List<String> workList;
    /**
     * 工作列表
     */
    private void workList() {
        workList=new ArrayList<>();

        List<String> qaOne = new ArrayList<>();
        qaOne.add("你是谁");
        qaOne.add("你多高");
        qaOne.add("卫生间在哪");
        qaOne.add("你多大了");
        workList = qaOne;
        mOrderAdapter.setNewData(workList);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_business;
    }

    /**
     * 适配器列表删除键点击监听
     * @param pos
     */
    @Override
    public void onItemClickListener(int pos) {
        String configListBeanssss=  workList.get(pos);
        workList.remove(configListBeanssss);
        mOrderAdapter.notifyDataSetChanged();
    }
}
