package com.example.temi_v1.UI.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.UI.Base.DividerItemDecoration;
import com.example.temi_v1.UI.activity.Adjustable.AdjustableActivity;
import com.example.temi_v1.UI.activity.Business.BusinessActivity;
import com.example.temi_v1.UI.activity.Exhibition.ExhibitionActivity;
import com.example.temi_v1.UI.activity.Schedule.SchedulingConfActivity;
import com.example.temi_v1.adapter.ConfigureListAdapter;
import com.example.temi_v1.bean.ConfigListBean;
import java.util.ArrayList;
import java.util.List;

import static com.example.temi_v1.util.IntentTool.inputPwd;

/**
 * 配置页面
 */
public class ConfigureActivity extends BaseActivity implements View.OnClickListener {
    private ConfigureListAdapter mOrderAdapter;

    @Override
    protected void initView() {
        ImageView finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RecyclerView mRecyclerView = findViewById(R.id.fg_menu_rcy);
        ImageView conf_home = findViewById(R.id.conf_home);
        conf_home.setOnClickListener(this);
        mOrderAdapter = new ConfigureListAdapter(R.layout.configure_list_item);
        mOrderAdapter.setHeaderAndEmpty(true);
        mOrderAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mOrderAdapter.setEnableLoadMore(true);
        //可插拔设计
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setAdapter(mOrderAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOrderAdapter.setOnItemClickListener(mListener);
        workList();
    }

    /**
     * 视频列表点击监听
     */
    BaseQuickAdapter.OnItemClickListener mListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            adapter.getItemCount();
            switch (position) {
                case 0://迎宾词
                    Intent welcom = new Intent(ConfigureActivity.this, WelcomeguestsActivity.class);
                    startActivity(welcom);
                    break;
                case 1://活动介绍
                    Intent introduc = new Intent(ConfigureActivity.this, AdjustableActivity.class);
                    startActivity(introduc);
                    break;
                case 2://展厅介绍
                    Intent exhibition = new Intent(ConfigureActivity.this, ExhibitionActivity.class);
                    startActivity(exhibition);
                    break;
                case 3://业务问答
                    Intent business = new Intent(ConfigureActivity.this, BusinessActivity.class);
                    startActivity(business);
                    break;
                case 4://日程安排
                    //准备跳转日程安排
                    Intent workmodel = new Intent(ConfigureActivity.this, SchedulingConfActivity.class);
                    startActivity(workmodel);
                    break;
                case 5://修改口令
                    inputPwd(ConfigureActivity.this,true);
                    break;
            }

        }
    };

    /**
     * 工作列表
     */
    private void workList() {
        ConfigListBean workListBean1 = new ConfigListBean();
        workListBean1.setTitle("迎宾词");
        ConfigListBean workListBean2 = new ConfigListBean();
        workListBean2.setTitle("活动介绍");
        ConfigListBean workListBean3 = new ConfigListBean();
        workListBean3.setTitle("展厅介绍");
        ConfigListBean workListBean4 = new ConfigListBean();
        workListBean4.setTitle("业务问答");
        ConfigListBean workListBean5 = new ConfigListBean();
        workListBean5.setTitle("日程安排");
        ConfigListBean workListBean6 = new ConfigListBean();
        workListBean6.setTitle("修改口令");

        List<ConfigListBean> workList = new ArrayList<>();
        workList.add(workListBean1);
        workList.add(workListBean2);
        workList.add(workListBean3);
        workList.add(workListBean4);
        workList.add(workListBean5);
        workList.add(workListBean6);

        mOrderAdapter.setNewData(workList);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_configure;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.conf_home:
                //准备跳转日程安排
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                break;
        }
    }
}
