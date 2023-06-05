package com.koko.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koko.domain.ResponseResult;
import com.koko.domain.entity.UserRole;
import com.koko.mapper.UserRoleMapper;
import com.koko.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2023-03-11 22:29:29
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    /**
     * 添加用户对应的所有角色
     * @param userId
     * @param roleIds
     * @return
     */
    @Override
    public ResponseResult addUserRole(Long userId, List<String> roleIds) {
        for(String s:roleIds){
            save(new UserRole(userId,Long.valueOf(s)));
        }
        return ResponseResult.okResult();
    }

    @Override
    public List<String> getUserRole(Long id) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        List<UserRole> userRoles = list(queryWrapper);
        List<String> roleIds = new ArrayList<>();
        for (UserRole userRole:userRoles){
            roleIds.add(userRole.getRoleId().toString());
        }
        return roleIds;
    }
}
