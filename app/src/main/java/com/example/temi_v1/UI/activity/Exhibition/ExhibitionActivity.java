package com.example.temi_v1.UI.activity.Exhibition;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.UI.Base.DividerItemDecoration;
import com.example.temi_v1.adapter.ExhibitionListAdapter;
import com.example.temi_v1.util.Constant;
import com.example.temi_v1.util.RobotTools;
import com.example.temi_v1.util.SaveData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

/**
 * 展厅介绍--配置
 */
public class ExhibitionActivity extends BaseActivity implements ExhibitionListAdapter.OnItemClickListener {
    public SwipeRefreshLayout mRefreshLayout;
    private ExhibitionListAdapter mOrderAdapter;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    private ItemTouchHelper mItemTouchHelper;
    RecyclerView mRecyclerView;

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

        mRecyclerView = findViewById(R.id.fg_menu_rcy);
        workList();
        mOrderAdapter = new ExhibitionListAdapter(workList);
        ////////////////////////////////////////////////
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mOrderAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        //上下拖动
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
//        mOrderAdapter.enableSwipeItem();//设可左右滑动
//        mOrderAdapter.setOnItemSwipeListener(onItemSwipeListener);
        mOrderAdapter.enableDragItem(mItemTouchHelper);//设可上下滑动
        mOrderAdapter.setOnItemDragListener(listener);
        ///////////////////////////////////////////////

        mOrderAdapter.setOnItemClickListener(ExhibitionActivity.this);
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

    private static final String TAG = ExhibitionActivity.class.getSimpleName();
    int indexnum;
    List<String> dumpint = new ArrayList<>();
    OnItemDragListener listener = new OnItemDragListener() {//上下滑动监听
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d(TAG, "drag start");
            dumpint.clear();
            dumpint.addAll(workList);
            for (String str : dumpint) {
                Log.e(TAG, "=start==" + str);
            }
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            Log.d(TAG, "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
            Log.d(TAG, dumpint.size() + "====");
//            if (source.getAdapterPosition()<target.getAdapterPosition()){
            String startstr = dumpint.get(source.getAdapterPosition());
            String endstr = dumpint.get(target.getAdapterPosition());
            Log.e("aaaaa1", endstr + "endstr");
            Log.e("aaaaa1", startstr + "startstr");
            dumpint.set(source.getAdapterPosition(), endstr);
            dumpint.set(target.getAdapterPosition(), startstr);

            workList.clear();
            workList.addAll(dumpint);
            for (String str : dumpint) {
                Log.e(TAG, "=end==" + str);
            }
        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d(TAG, "drag end");

            SaveData.saveAddressToJson(dumpint);//更新本地地址顺序
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        mOrderAdapter.notifyDataSetChanged();
    }

    /**
     * 业务问答列表点击监听
     */
    BaseQuickAdapter.OnItemClickListener mListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent = new Intent(ExhibitionActivity.this, ExhibitionDestilsActivity.class);
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
                        List<String> location = RobotTools.getLocations();
                        SaveData.saveAddressToJson(location);//保存地址
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
    private List<String> workList() {
        workList = new ArrayList<>();
        List<String> location = RobotTools.getLocations();
        String jsonAddres = SaveData.getGuideData(Constant.saveAddressKey);//获取所有的地址
        Log.e(TAG, jsonAddres);
        if (!jsonAddres.equals("null")) {//当地址不为空时
            Gson gson = new Gson();
            //把json地址数据转数组,然后赋值给Constant.alladdress
            String[] userArray = gson.fromJson(jsonAddres, String[].class);
            if (location.size() != userArray.length) {
                workList.addAll(location);
            } else {
//                String[] userArray = gson.fromJson(jsonAddres, String[].class);
                List<String> listAddres = gson.fromJson(jsonAddres, new TypeToken<List<String>>() {
                }.getType());
                workList.addAll(listAddres);
            }
        } else {
            workList.addAll(location);
        }
        return workList;
//        mOrderAdapter.setNewData(workList);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_business;
    }

    /**
     * 适配器列表删除键点击监听
     *
     * @param pos
     */
    @Override
    public void onItemClickListener(int pos) {
        String configListBeanssss = workList.get(pos);
        workList.remove(configListBeanssss);
        mOrderAdapter.notifyDataSetChanged();
    }
}
