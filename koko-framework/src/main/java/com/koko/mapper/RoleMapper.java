package com.koko.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koko.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-09 16:58:10
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}
