package com.example.temi_v1.UI.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.util.SaveData;

/**
 * 迎宾词页面
 */
public class WelcomeguestsActivity extends BaseActivity implements View.OnClickListener {
    TextView input_text_save;
    Button save_wecl;

    @Override
    protected void initView() {
        ImageView finish = findViewById(R.id.finish);
        input_text_save = findViewById(R.id.input_text_save);
        save_wecl = findViewById(R.id.save_wecl);
        save_wecl.setOnClickListener(this);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcomeguests;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save_wecl:
                String inputstr=input_text_save.getText().toString();
                if (inputstr.equals("")){
                    Toast.makeText(this,"请输入迎宾词",Toast.LENGTH_SHORT).show();
                    return;
                }
                SaveData.setGuideData("迎宾词",inputstr);
                finish();
                break;
        }
    }
}
