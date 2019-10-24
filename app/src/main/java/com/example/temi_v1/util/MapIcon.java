package com.example.temi_v1.util;

import com.example.temi_v1.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shinelon-LJL on 2019/9/27
 */
public class MapIcon {
//    http://47.106.170.112/khxqicon/5d%E5%B9%BF%E5%91%8A%E6%9C%BA.jpg
   private static Map<String, Integer> map = new HashMap<String, Integer>();
   private static Map<String, String> servicemap = new HashMap<String, String>();
   public static Integer getIcon(String string){
        map.clear();
        map.put("5d广告机", R.mipmap.screensaver1);
        map.put("4g手表",R.mipmap.screensaver2);
        map.put("耳部产品",R.mipmap.screensaver3);
        map.put("家用产品",R.mipmap.screensaver4);
        map.put("接待点",R.mipmap.screensaver7);
        map.put("柔宇手机",R.mipmap.screensaver5);
        map.put("手部产品",R.mipmap.screensaver8);
        map.put("无人机",R.mipmap.screensaver9);
        map.put("学习机器人",R.mipmap.screensaver10);
        map.put("音箱设备",R.mipmap.screensaver12);
        map.put("运动设备",R.mipmap.screensaver13);
        map.put("眼部产品",R.mipmap.screensaver11);
        map.put("智能家电",R.mipmap.screensaver14);
        map.put("智能家居",R.mipmap.screensaver15);
        map.put("视听区",R.mipmap.screensaver6);
        map.put("智能教育区",R.mipmap.screensaver16);
        map.put("4g智能手表",R.mipmap.ic1);
        map.put("temi机器人",R.mipmap.ic2);
        map.put("折叠代步车",R.mipmap.ic3);
        map.put("智能太阳镜",R.mipmap.ic4);
        map.put("自动炒菜机",R.mipmap.ic5);
        map.put("折叠屏手机",R.mipmap.ic6);
        map.put("教育机器人",R.mipmap.ic7);
        map.put("手机稳定器",R.mipmap.ic8);
        map.put("华为手机",R.mipmap.ic9);
        map.put("广告机",R.mipmap.ic10);
        map.put("迎宾机器人",R.mipmap.ic11);
        map.put("蓝牙音箱",R.mipmap.ic12);
        map.put("公司简介",R.mipmap.ic13);
        map.put("体验馆",R.mipmap.ic14);
        map.put("客户星球LOGO",R.mipmap.ic15);
        map.put("消费直链",R.mipmap.ic16);

      return (map.get(string)==null)?0:map.get(string);
    }
    public static String getServiceIcon(String string){
        servicemap.clear();
        servicemap.put("home base","http://47.106.170.112/khxqicon/screensaver113.jpg");
        servicemap.put("5d广告机","http://47.106.170.112/khxqicon/5d广告机.jpg");
        servicemap.put("4g手表","http://47.106.170.112/khxqicon/4g手表.jpg");
        servicemap.put("耳部产品","http://47.106.170.112/khxqicon/耳部产品.jpg");
        servicemap.put("家用产品","http://47.106.170.112/khxqicon/家用产品.jpg");
        servicemap.put("接待点","http://47.106.170.112/khxqicon/接待点.jpg");
        servicemap.put("柔宇手机","http://47.106.170.112/khxqicon/柔宇手机.jpg");
        servicemap.put("手部产品","http://47.106.170.112/khxqicon/手部产品.jpg");
        servicemap.put("无人机","http://47.106.170.112/khxqicon/无人机.jpg");
        servicemap.put("学习机器人","http://47.106.170.112/khxqicon/学习机器人.jpg");
        servicemap.put("音箱设备","http://47.106.170.112/khxqicon/音箱设备.jpg");
        servicemap.put("运动设备","http://47.106.170.112/khxqicon/运动设备.jpg");
        servicemap.put("眼部产品","http://47.106.170.112/khxqicon/眼部产品.jpg");
        servicemap.put("智能家电","http://47.106.170.112/khxqicon/智能家电.jpg");
        servicemap.put("智能家居","http://47.106.170.112/khxqicon/智能家居.jpg");
        servicemap.put("视听区","http://47.106.170.112/khxqicon/视听区.jpg");
        servicemap.put("智能教育区","http://47.106.170.112/khxqicon/智能教育区.jpg");
        return servicemap.get(string);
    }
}
