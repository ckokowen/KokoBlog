package com.koko.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.domain.ResponseResult;
import com.koko.domain.entity.RoleMenu;

import java.util.List;


/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author makejava
 * @since 2023-03-11 16:09:00
 */
public interface RoleMenuService extends IService<RoleMenu> {

    ResponseResult addMenuIds(Long id, List<String> menuIds);

    List<String> getRoleMenuById(Long id);
}
