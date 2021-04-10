package com.dcj.demo_server.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.digester.DocumentProperties;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;

@Data
@Component
public class FileFactory {

    public static String path;

    private MultipartFile file;

    public static boolean saveFile(MultipartFile file, String fold){//保存文件
        if (file.isEmpty()){
            return false;
        }
        try {
            String rootPath = path + fold + "/";
            String filename = file.getOriginalFilename();
            File dir = new File(rootPath + filename);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file.transferTo(dir);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean downloadFile(String downloadPath, HttpServletResponse response, String filename) throws Exception{
        File file = new File(downloadPath);
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition","attachment;fileName=" +filename);
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1){
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    public String getPrefix(){//获得文件名称
        String filename = this.file.getOriginalFilename();
        return filename.substring(0,filename.lastIndexOf("."));
    }

    public  String getSuffix(){//获得文件类型
        String filename = this.file.getOriginalFilename();
        String[] strArray = filename.split("\\.");
        int suffixIndex = strArray.length - 1;
        return strArray[suffixIndex];
    }

    @Value("${filepath}")
    public void setPath(String filepath) {
        path = filepath;
    }
}
