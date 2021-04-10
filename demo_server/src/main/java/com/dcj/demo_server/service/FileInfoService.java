package com.dcj.demo_server.service;

import com.dcj.demo_server.common.DatetimeFactory;
import com.dcj.demo_server.common.FileFactory;
import com.dcj.demo_server.common.UuidFactory;
import com.dcj.demo_server.dao.FileInfoDao;
import com.dcj.demo_server.entity.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class FileInfoService {
    @Autowired
    FileInfoDao fileInfoDao;

    public List<FileInfo> getAllFileInfo(){return fileInfoDao.getAllFileInfo();}

    public boolean addFile(MultipartFile file){//调用dao 和 工具类保存文件以及其信息
        String createtime = DatetimeFactory.getNowDate();
        if (FileFactory.saveFile(file, createtime)){
            FileFactory fileFactory = new FileFactory();
            fileFactory.setFile(file);
            String uuid = UuidFactory.getUUID();
            String fname = fileFactory.getPrefix();
            String ftype = fileFactory.getSuffix();
            String floc =  FileFactory.path + createtime;
            String fsize = String.valueOf(file.getSize());
            fileInfoDao.addFileInfo(uuid, fname, createtime, ftype, floc, fsize);
            return true;
        }
        return false;
    }

    public String getFilePath(String uuid) {
        FileInfo fileInfo = fileInfoDao.getFileInfoByUuid(uuid);
        return fileInfo.getFloc() + "/" + fileInfo.getFname() + "." + fileInfo.getFtype();
    }

    public String getFileName(String uuid){
        FileInfo fileInfo = fileInfoDao.getFileInfoByUuid(uuid);
        return fileInfo.getFname() + "." + fileInfo.getFtype();
    }

    public boolean isFileExist(String uuid){
        return fileInfoDao.getFileInfoByUuid(uuid) != null;
    }
}
