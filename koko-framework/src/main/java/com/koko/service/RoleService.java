package com.koko.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.domain.ResponseResult;
import com.koko.domain.dto.RoleAddDto;
import com.koko.domain.dto.RoleChangeDto;
import com.koko.domain.dto.RoleUpdateDto;
import com.koko.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-09 16:58:10
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long userId);

    ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(RoleChangeDto roleChangeDto);

    ResponseResult permsTree();

    ResponseResult addRole(RoleAddDto roleAddDto);

    ResponseResult updateReturnView(Long id);

    ResponseResult updateReturnTree(Long id);

    ResponseResult updateRole(RoleUpdateDto role);

    ResponseResult delete(Long id);

    ResponseResult listAllRole();
}
