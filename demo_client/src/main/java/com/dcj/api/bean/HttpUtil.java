package com.dcj.api.bean;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

public class HttpUtil {
    public static String doGet(String url) throws Exception{//封装get请求
        String result = "";
        BufferedReader in = null;
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 建立实际的连接
        connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }

        if (in != null) {
            in.close();
        }

        return result;
    }

    public static String doPost(String url, String filename) throws Exception{

        String charset = "UTF-8";
        File binaryFile = new File(filename);
        String boundary = "------------------------" + Long.toHexString(System.currentTimeMillis()); //生成boundary
        String CRLF = "\r\n"; //multipart/form-data.所需的换行
        int    responseCode = 0;


        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        connection.addRequestProperty("User-Agent", "CheckpaySrv/1.0.0");
        connection.addRequestProperty("Accept", "*/*");

        OutputStream output = connection.getOutputStream();
        PrintWriter writer  = new PrintWriter(new OutputStreamWriter(output, charset), true);


        writer.append("--" + boundary).append(CRLF);
        writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
        writer.append("Content-Type: application/octet-stream").append(CRLF);
        writer.append(CRLF).flush();


        Files.copy(binaryFile.toPath(), output);
        output.flush();

        //multipart/form-data.
        writer.append(CRLF).append("--" + boundary + "--").flush();

        responseCode = ((HttpURLConnection) connection).getResponseCode();

        output.flush();
        output.close();

        return String.valueOf(responseCode);
    }

    public static String doDownload(String url, String savePath, String filename) throws Exception{
        File file=new File(savePath);
        FileOutputStream fileOut = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        // 建立链接
        URL httpUrl=new URL(url);
        connection=(HttpURLConnection) httpUrl.openConnection();
        //以Post方式提交表单，默认get方式
        connection.setDoInput(true);
        connection.setDoOutput(true);
        // post方式不能使用缓存
        connection.setUseCaches(false);
        //连接指定的资源
        connection.connect();
        //获取网络输入流
        inputStream=connection.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        //判断文件的保存路径后面是否以/结尾
        if (!savePath.endsWith("/")) {
            savePath += "/";
        }
        //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
        fileOut = new FileOutputStream(savePath + filename);
        BufferedOutputStream bos = new BufferedOutputStream(fileOut);

        byte[] buf = new byte[10240];
        int length = bis.read(buf);
        //保存文件
        while(length != -1)
        {
            bos.write(buf, 0, length);
            length = bis.read(buf);
        }
        bos.close();
        bis.close();
        connection.disconnect();

    String responseCode = String.valueOf(((HttpURLConnection) connection).getResponseCode());

    return responseCode;
}

}
