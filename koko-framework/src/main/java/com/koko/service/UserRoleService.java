package com.koko.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.domain.ResponseResult;
import com.koko.domain.entity.UserRole;

import java.util.List;


/**
 * 用户和角色关联表(UserRole)表服务接口
 *
 * @author makejava
 * @since 2023-03-11 22:29:29
 */
public interface UserRoleService extends IService<UserRole> {

    ResponseResult addUserRole(Long userId, List<String> roleIds);

    List<String> getUserRole(Long id);
}
