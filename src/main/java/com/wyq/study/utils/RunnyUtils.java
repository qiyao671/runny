package com.wyq.study.utils;

/**
 * Created by qiyao on 2017/4/27.
 */
public class RunnyUtils {
    public static String getImageUrl(String imageName) {
        if (imageName != null && !imageName.isEmpty()) {
            return "http://192.168.31.245:8080/images" + imageName;
        } else {
            return imageName;
        }
    }
}
