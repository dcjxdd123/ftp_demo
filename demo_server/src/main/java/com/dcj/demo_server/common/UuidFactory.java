package com.dcj.demo_server.common;

import java.util.UUID;

public class UuidFactory {//随机生成uuid
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }
}
