package com.dcj.demo_server.dao;

import com.dcj.demo_server.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileInfoDao extends JpaRepository<FileInfo,String> {

    @Modifying
    @Query(value = "select * from file_info",nativeQuery = true)
    List<FileInfo> getAllFileInfo();//获取所有文件信息

    @Modifying
    @Query(value = "insert into file_info(uuid, fname, createtime, ftype, floc, fsize) values(?1, ?2, ?3, ?4, ?5, ?6)",nativeQuery = true)
    void addFileInfo(String uuid, String fname, String createtime, String ftype, String floc, String fsize);//上传文件

    FileInfo getFileInfoByUuid(String uuid);//根据uuid获取文件信息
}
