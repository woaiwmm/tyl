package com.tyl.service.impl;

import com.google.common.collect.Lists;
import com.tyl.service.IFileService;
import com.tyl.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Administrator
 * @date 2019-11-29 8:47
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * @param file 上传文件
     * @param path 上传文件的目标存放路径
     * @return
     */
    public String upload(MultipartFile file, String path) {
        String fileName = file.getName();

        //扩展名abc.jpg-->jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //防止上传文件名重复，给每个图片接一个UUID
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件，上传文件名：{}，上传的路径：{}，新文件名：{}", fileName, path, uploadFileName);
//        判断存放路径是否存在
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            //file already upload success
            file.transferTo(targetFile);
            //file already upload to FTPServer
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //after upload success,delete save's file
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }

        return targetFile.getName();
    }
}
