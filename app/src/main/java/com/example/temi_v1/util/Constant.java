package com.example.temi_v1.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shinelon-LJL on 2019/9/17
 */
public class Constant {
    public static final int CANCEL = 0;//取消
    public static final String boothDetail = "boothDetail";//展厅介绍
    public static final String title = "title";//取消
    public static final String clicktitless = "clicktitless";//
    public static final String clicks = "clicks";//点击
    public static final String bolAddress = "bolAddress";//是否是导览
    public static final String titless = "titless";//导览介绍标题
    public static final String USER_STATE = "user_state";
    public static final int noDataDef = 0;
    public static final String noDataDefStr = "null";
    public static boolean powerSend = false;//第一次加载
    public static final int broadcastIntTow = 2;//通知数据跟新

    public static final int downTime = 60;//倒计时
    public static final int downTime20 = 20;//倒计时
    public static final int intStat3000 = 3000;//最后⼀个点位介绍完，机器⼈随机话术
    public static final int intStat4000 = 4000;//在导航前，机器⼈随机话术
    public static final int downTime10 = 10;//倒计时10秒
    public static final int downTime60 = 60;//倒计时10秒
    public static String address = "";//通知数据跟新
    public static String saveAddressKey = "saveAddressKey";//保存的所有地址
    public static String addressKey = "addressKey";//通知数据跟新
    public static boolean alladdressKey = false;//是否导览所有地址
    public static String[] alladdress = {"接待点", "智能家居", "5d广告机", "智能教育区", "无人机", "运动设备", "眼部产品", "耳部产品", "手部产品", "视听区", "音箱设备", "智能家电", "家用产品", "4g手表", "柔宇手机", "学习机器人"};//通知数据跟新
    public static String[] alladdrecontext = {"这里有一个仿真机器人带有眨眼睛、模拟人嘴唇动作，鞠躬等身体交互，具有主动迎宾，自动感应，自定义语言等功能。" +
            "你觉得她可爱美丽大方吗？"
            , "这里是智能家居区域，这里有扫地机、指纹门锁、人脸识别门锁、摄像头、感应灯、智能台灯、语音开关和语音遥控器。" +
            "以酷扫地机不带有APP，价格580元，旺家扫地机带有APP,价格1290-1990之间，" +
            "不带APP指纹门锁，出厂价398，带APP指纹门锁，锁体不一样，700-900之间" +
            "人脸识别门锁，888元，" +
            "摄像头，分为无线蓝牙摄像头和有线摄像头，" +
            "九旭感应灯，在13-50元之间" +
            "飞鹤智能台灯，在150-300元之间" +
            "语音开关80元，" +
            "语音遥控器80元，" +
            "您看有什么感兴趣的产品吗？"
            , "现在这里是广告机，根据尺寸不同价格不同，，分为70、50寸" +
            "都带有APP，可以进行内容自动更换，简单快速。"
            , "这边就是一片智能教育区，这些设备可以用于学生编程硬件，主要是为了开发学生智力和创造力为主，" +
            "可以送给小朋友玩哦"
            , "这里还有小飞机哦，其实它们是无人机，分为玩具类和工具类，" +
            "小型玩具类无人机主要给小孩玩耍，工具类的无人机用于专业拍照，拍出来效果很好呦，都不需要磨皮呢。" +
            "快带一台回去玩吧"
            , "要不过来玩下这个电动滑板车？还有电动平衡车可以玩的，不过要小心，速度还是有点快的"
            , "过来做个眼部按摩吧，这里有眼部按摩仪，还有VR眼镜，石墨烯眼罩，小型台灯，手写板，可以体验一下。"
            , "这里有入耳式蓝牙耳机，运动式蓝牙耳机，降噪式蓝牙耳机，想要哪种都有。"
            , "这里有运动功能手环，还有适合早晨刷牙的电动牙刷，随身携带的微型投影仪，手持翻译机进行多语言翻译"
            , "最享受的就是这个视听区了，有降噪耳机，智能眼镜，骨传感耳机，悄悄告诉你，我体验过，还不错"
            , "想带音箱回家玩嘛，这里有复古音箱，运动音箱，微型音箱，带一个回去听歌吧"
            , "这里是百宝区哦，有智能电视，智能曲面电视，智能电饭煲，炒菜机，高压机，破壁机，空气净化器，水杯，垃圾桶，净水器等等，每一个都很实用，我都想要一个"
            , "这就是智能家用区了，压缩水壶，家用音响，家用电脑，家用平板，智能鼠标，让生活更方便"

            , "这个4G手表可厉害了，它代替手机，进行录像拍照，下载软件，当然也可以跟手机同步，防水，主要用于大专院校学生。还有一个小欧智能虚拟机器人，可以用于变装、娱乐、生活、教育" +
            "小欧智能虚拟机器人，可以用于变装、娱乐、生活、教育"
            , "想试试曲面的东西吗？柔宇主要是做曲面穿戴产品，属于国内顶尖科技产品，包含曲面手机，曲面折叠屏的衍生产品，明年3月份面市弯曲手上手机" +
            "这还有未来小七机器人，金大机器人，也可以体验下" +
            "未来小七机器人" +
            "金大机器人"
            , "这一个柜子摆放的都是学习机，分为7寸、9寸安卓和小系统各种学习机器人，可以教小朋友学习"};//通知数据跟新

    public static int addressindex = 1;//地址下标
    public static String activityId = "activityId";//日程id
    //    Map<String,Integer> iconMap=new Map<String, Integer>;
    Map<String, Integer> map = new HashMap<String, Integer>();
    public static String goAddre = "shunxu";

}
