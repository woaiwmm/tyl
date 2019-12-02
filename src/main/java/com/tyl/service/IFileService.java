package com.tyl.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 * @date 2019-11-29 8:47
 */
public interface IFileService {
     String upload(MultipartFile file, String path) ;
}
