package com.koko.service;

import com.koko.domain.ResponseResult;
import com.koko.domain.entity.User;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-07-15:41
 */
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
