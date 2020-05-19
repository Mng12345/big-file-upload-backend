package com.zm.fileupload.controllers;

import ch.qos.logback.core.rolling.helper.FileStoreUtil;
import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson.JSON;
import com.zm.fileupload.dto.GlobalResult;
import com.zm.fileupload.utils.FileUploadUtil;
import lombok.extern.java.Log;
//import org.aspectj.util.FileUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @Author zhangming
 * @Date 2020/5/15 15:47
 */

@RestController
@RequestMapping("/api")
@Log
public class FileUploadController {

    @Value("${config.fileupload.path}")
    private String fileuploadPath;

    @PostMapping("/fileupload")
    @ResponseBody
    public GlobalResult upload2(@RequestParam Integer index, @RequestParam MultipartFile chunk, @RequestParam String name, @RequestParam Integer chunksLength, @RequestParam String uid) throws IOException {
        try {
            String filename = name.split("\\.")[0];
            String filefmt = name.split("\\.")[1];
            String fullFileName = String.join("-", new String[] {filename, uid, index + "", "." + filefmt});
            log.info("filename:" + fullFileName);
            File dir = new File(fileuploadPath);
            if (!dir.exists())
                dir.mkdirs();
            File file = new File(fileuploadPath + "/" + fullFileName);
            FileCopyUtils.copy(chunk.getInputStream(), new FileOutputStream(file));
            FileUploadUtil.put(uid, index, chunksLength, fullFileName, fileuploadPath);
            // 检测所有文件是否上传完成
            boolean isAllChunksUploaded = FileUploadUtil.isAllChunksUploaded(uid);
            try {
                if (isAllChunksUploaded) {
                    FileUploadUtil.mergeChunks(name, uid);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex.getMessage());
            }
            return new GlobalResult(true, "");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GlobalResult(false, "上传文件块，uid: " + uid + " index: " + index + "失败");
        }
    }

    @GetMapping("/get")
    public void get(@RequestParam String name, HttpServletRequest request) {
        System.out.println("get" + "name: " + name);
    }
}
