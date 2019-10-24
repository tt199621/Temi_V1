package com.example.temi_v1.util;

import com.robotemi.sdk.Robot;

import java.util.List;

/**
 * Created by Shinelon-LJL on 2019/9/19
 */
public class RobotTools {
    /**
     * 获取所有地址
     * @return
     */
    public static  List<String> getLocations() {
        return Robot.getInstance().getLocations();
    }
}
