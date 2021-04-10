package com.dcj.api;

import com.dcj.api.bean.HttpUtil;

public class MyHttpClient {
    public static String getAllFileInfo() throws Exception{
        String data = HttpUtil.doGet("http://localhost:8080/api/getAllFileInfo");
        System.out.println(data);
        return data;
    }

    public static boolean uploadFile(String filename) throws Exception{
        String responseCode = HttpUtil.doPost("http://localhost:8080/api/addFile",filename);
        if (responseCode.equals("200")){
            System.out.println("上传成功");
            return true;
        }
        System.out.println("上传失败");
        return false;
    }

    public static boolean downloadFile(String uuid,String savePath, String filename) throws Exception{
        String responseCode = HttpUtil.doDownload("http://localhost:8080/api/download/" + uuid, savePath, filename);
        if (responseCode.equals("200")){
            System.out.println("下载成功");
            return true;
        }
        System.out.println("下载失败");
        return false;
    }
}
