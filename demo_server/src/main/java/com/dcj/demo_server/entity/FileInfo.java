package com.dcj.demo_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class FileInfo {
    @Id
    private String uuid;//
    private String fname;//文件名称
    private String createtime;//文件上传时间
    private String ftype;//文件类型
    private String floc;//文件存放路径
    private String fsize;//文件大小
}
