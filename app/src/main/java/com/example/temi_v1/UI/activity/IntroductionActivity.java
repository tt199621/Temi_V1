package com.example.temi_v1.UI.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.util.Constant;
import com.example.temi_v1.util.SaveData;
import com.example.temi_v1.util.Tools;

/**
 * 活动介绍界面
 */
public class IntroductionActivity extends BaseActivity implements View.OnClickListener {
    AppCompatEditText adjustableTitle,input_text;
    @Override
    protected void initView() {
        ImageView finish = findViewById(R.id.finish);
        Button savedata = findViewById(R.id.savedata);
        savedata.setOnClickListener(this);
        input_text =findViewById(R.id.input_text);
        adjustableTitle = findViewById(R.id.adjustableTitle);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Tools.setEditTextEnable(adjustableTitle,false);
//      getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
    String titles;
    @Override
    protected void initData() {
        super.initData();
        titles=getIntent().getStringExtra(Constant.title);
        String activistr = SaveData.getGuideData(titles);
        if (!activistr.equals("null")) {
            input_text.setText(activistr);
        }
        adjustableTitle.setText(titles);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_introduction;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.savedata:
                String savestr=input_text.getText().toString();
                Log.e("savestr",savestr);
                SaveData.setGuideData(titles,savestr);
                finish();
                break;
        }
    }
}
