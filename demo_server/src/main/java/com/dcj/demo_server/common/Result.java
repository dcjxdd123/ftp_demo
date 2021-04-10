package com.dcj.demo_server.common;

import lombok.Data;

@Data
public class Result {//json返回信息
    private int code;
    private String message;
    private Object result;

    public Result(int code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
}

