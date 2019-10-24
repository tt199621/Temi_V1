package com.example.temi_v1.UI.activity.Exhibition;

import android.util.Log;
import android.view.View;
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
 * 展厅介绍详情页--配置
 */
public class ExhibitionDestilsActivity extends BaseActivity implements View.OnClickListener {
    AppCompatEditText business_title,input_text;
    Button save_btn;
    @Override
    protected void initView() {
        ImageView finish=findViewById(R.id.finish);
        business_title =findViewById(R.id.business_title);
        input_text =findViewById(R.id.input_text);
        save_btn =findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);
        Tools.setEditTextEnable(business_title,false);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    String titles;
    @Override
    protected void initData() {
        super.initData();
        titles=getIntent().getStringExtra(Constant.title);
        business_title.setText(titles);
        input_text.setText(SaveData.getGuideData(titles));
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
