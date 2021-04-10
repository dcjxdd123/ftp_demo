package com.dcj.demo_server.web;

import com.dcj.demo_server.common.*;
import com.dcj.demo_server.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("api")
public class FileController {
    @Autowired
    private FileInfoService fileInfoService;

    @GetMapping("/getAllFileInfo")
    public Result getAllFileInfo(){//获取所有文件信息api
        return ResultFactory.buildResult(ResultCode.SUCCESS,"success",fileInfoService.getAllFileInfo());
    }

    @PostMapping("/addFile")
    public Result addFile(@RequestParam  MultipartFile file){//上传文件api
        if (fileInfoService.addFile(file)){
            return ResultFactory.buildSuccessResult("success");
        }
        return ResultFactory.buildResult(410,"fail","");
    }

    @GetMapping("/download/{uuid}")
    public Result downloadFile (@PathVariable String uuid, HttpServletResponse response) throws Exception{//下载文件api

        if (fileInfoService.isFileExist(uuid)){
            String downloadPath = fileInfoService.getFilePath(uuid);
            String filename = fileInfoService.getFileName(uuid);
            FileFactory.downloadFile(downloadPath, response, filename);
            return ResultFactory.buildSuccessResult("success");
        }

        return ResultFactory.buildResult(410,"fail","");
    }
}
