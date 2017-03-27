package com.wyq.study;

import com.xiaoleilu.hutool.date.DateField;
import com.xiaoleilu.hutool.date.DateTime;
import com.xiaoleilu.hutool.date.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-20 上午10:47
 * 系统版本：1.0.0
 **/
public class MainTest {

    public static void main(String[] args) {
        Date dateTime = offsiteTime(DateUtil.beginOfDay(new Date()), -5, 30, 20);
        System.out.println(dateTime);
    }

    public static Date offsiteTime(Date date, int offsiteHour, int offsiteMinute, int offsiteSecond) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(11, offsiteHour);
        cal.add(12, offsiteMinute);
        cal.add(13, offsiteSecond);
        return cal.getTime();
    }

    public static Date offsiteDate(Date date, DateField datePart, int offsiteHour, int offsiteMinute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(datePart.getValue(), offsiteHour);
        return new DateTime(cal.getTime());
    }


}
