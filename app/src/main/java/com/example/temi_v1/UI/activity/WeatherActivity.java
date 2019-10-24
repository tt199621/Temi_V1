package com.example.temi_v1.UI.activity;

import android.view.View;
import android.widget.ImageView;

import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;

/**
 * 天气
 */
public class WeatherActivity extends BaseActivity {

    @Override
    protected void initView() {
        ImageView finish=findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weather;
    }
}
