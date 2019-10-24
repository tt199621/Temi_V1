package com.example.temi_v1.UI.activity.Booth;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.temi_v1.R;
import com.example.temi_v1.UI.Base.BaseActivity;
import com.example.temi_v1.model.BannerModel;
import com.example.temi_v1.service.listener.BroadcastListener;
import com.example.temi_v1.util.Constant;
import com.example.temi_v1.util.MapIcon;
import com.example.temi_v1.util.RobotTools;
import com.example.temi_v1.util.SaveData;
import com.gc.materialdesign.views.ButtonFloat;
import com.google.gson.Gson;
import com.robotemi.sdk.TtsRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import cn.bingoogolapple.bgabanner.transformer.TransitionEffect;

/**
 * 展位介绍
 */
public class BoothActivity extends BaseActivity implements BGABanner.Delegate<ImageView, String>, BGABanner.Adapter<ImageView, String> {
    private BGABanner mDefaultBanner;
    @Override
    protected void initView() {
        ButtonFloat buttonFloat = findViewById(R.id.buttonFloat);
        buttonFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mDefaultBanner = findViewById(R.id.banner_main_default);
        setListener();
        loadData();
    }

    private void loadData() {
        loadData(mDefaultBanner);
        mDefaultBanner.setTransitionEffect(TransitionEffect.Cube);
    }

    List<String> location;
    private void loadData(BGABanner banner) {
        String[] url={"http://photos.breadtrip.com/photo_2015_04_23_ab4ee7bb3521c9c7f9b89167e99c8b8e.jpg?imageView/2/w/960/q/85","http://bgashare.bingoogolapple.cn/banner/imgs/6.png","http://bgashare.bingoogolapple.cn/banner/imgs/5.png"};
//        int[] url={R.mipmap.screensaver1,R.mipmap.screensaver2,R.mipmap.screensaver3};
        BannerModel bannerModel = new BannerModel();
        List<String> imgs = new ArrayList<>();
//        List<String> imgs = new ArrayList<>();
        List<String> tips = new ArrayList<>();
        location = RobotTools.getLocations();

        for (int i = 0; i < location.size(); i++) {
            int index = new Random().nextInt(3);//随机取其中一个url

            imgs.add(i, url[index]);//图片
//            imgs.add(i, MapIcon.getServiceIcon(location.get(i)));//图片
            tips.add(i, location.get(i));//标题
        }
        bannerModel.imgs = imgs;
        bannerModel.tips = tips;

        /**
         * 设置是否开启自动轮播，需要在 setData 方法之前调用，并且调了该方法后必须再调用一次 setData 方法
         * 例如根据图片当图片数量大于 1 时开启自动轮播，等于 1 时不开启自动轮播
         */
        banner.setAutoPlayAble(bannerModel.imgs.size() > 1);
        banner.setAdapter(this);
        banner.setData(bannerModel.imgs, bannerModel.tips);
    }

    private void setListener() {
        mDefaultBanner.setDelegate(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_booth;
    }
    boolean bol=true;
    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
        if (bol){
            bol=false;
//            String[] str={"请您跟我来","我带您继续参观","您跟我看看下个地⽅吧","去看看下⼀个展位吧"};
//            int index = new Random().nextInt(str.length);//随机取其中一个话术
//            robot.speak(TtsRequest.create(str[index], false));
            //保存地址
            Constant.address=location.get(position);
            Constant.alladdressKey=false;//不是全局导览
            Intent intent = new Intent();
            intent.setAction(BroadcastListener.ACTION);
            intent.putExtra(Constant.clicks,1000);
            intent.putExtra(Constant.addressKey, Constant.address);
            sendBroadcast(intent);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        bol=true;
    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
        Glide.with(itemView.getContext())
                .load(model)
                .apply(new RequestOptions().placeholder(R.mipmap.holder).error(R.mipmap.holder).dontAnimate().centerCrop())
                .into(itemView);
    }

}
