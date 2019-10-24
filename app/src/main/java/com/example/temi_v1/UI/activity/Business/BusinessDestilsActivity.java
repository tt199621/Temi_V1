package com.example.temi_v1.UI.activity.Business;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.util.Constant;
import com.example.temi_v1.util.SaveData;
import com.example.temi_v1.util.Tools;

/**
 * 业务问答详情页
 */
public class BusinessDestilsActivity extends BaseActivity implements View.OnClickListener {
    AppCompatEditText input_text,business_title;
    @Override
    protected void initView() {
        ImageView finish = findViewById(R.id.finish);
        Button savedata = findViewById(R.id.save_btn);
        savedata.setOnClickListener(this);
        business_title =findViewById(R.id.business_title);
        input_text =findViewById(R.id.input_text);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Tools.setEditTextEnable(business_title,false);
//      getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
    String titles;
    @Override
    protected void initData() {
        super.initData();
        titles=getIntent().getStringExtra(Constant.title);
        business_title.setText(titles);
        String activistr = SaveData.getGuideData(titles);
        if (!activistr.equals("null")) {
            input_text.setText(titles);
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_business_destils;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save_btn:
                String savestr=input_text.getText().toString();
                Log.e("savestr",savestr);
                SaveData.setGuideData(titles,savestr);
                finish();
                break;
        }
    }
}

