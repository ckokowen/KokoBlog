package com.koko.service;

import com.koko.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-08-15:36
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
