package com.koko.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.domain.ResponseResult;
import com.koko.domain.dto.UserAddDto;
import com.koko.domain.dto.UserUpdateDto;
import com.koko.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-07 21:06:28
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult userAllList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addUser(UserAddDto userAddDto);

    ResponseResult deleteUser(Long id);

    ResponseResult returnViewUser(Long id);

    ResponseResult updateUser(UserUpdateDto user);
}
