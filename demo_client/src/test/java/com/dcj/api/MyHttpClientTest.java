package com.dcj.api;

import com.alibaba.fastjson.JSONObject;
import com.dcj.api.bean.HttpUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyHttpClientTest {

    @Test
    public void getAllFileInfo() throws Exception{//获取文件信息接口测试
        JSONObject jsonObject = JSONObject.parseObject(MyHttpClient.getAllFileInfo());
        assertTrue(jsonObject.getString("code").equals("200"));
    }

    @Test
    public void uploadFile() throws Exception{//上传文件接口测试
        assertTrue(MyHttpClient.uploadFile("/home/dcj/Documents/test.csv"));
    }

    @Test
    public void downloadFile() throws Exception {//下载文件接口测试
        assertTrue(MyHttpClient.downloadFile("1e3ba3b7388d43a4ac3ddfd7c0b53f5a","/home/dcj/Downloads/","test.csv"));
    }
}
