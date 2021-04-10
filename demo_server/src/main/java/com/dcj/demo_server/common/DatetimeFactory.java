package com.dcj.demo_server.common;

import java.text.SimpleDateFormat;
//时间转换工具类
public class DatetimeFactory {
    public static String getNowDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(System.currentTimeMillis());
    }
}
