package com.koko.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koko.domain.ResponseResult;
import com.koko.domain.entity.RoleMenu;
import com.koko.mapper.RoleMenuMapper;
import com.koko.service.RoleMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-11 16:09:00
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    /**
     * 添加角色对应的所有菜单
     * @param id
     * @param menuIds
     * @return
     */
    @Override
    public ResponseResult addMenuIds(Long id, List<String> menuIds) {
        for(String s:menuIds){
            save(new RoleMenu(id,Long.valueOf(s)));
        }
        return ResponseResult.okResult();
    }

    /**
     * 获取角色所关联的菜单权限id列表
     * @param id
     * @return
     */
    @Override
    public List<String> getRoleMenuById(Long id) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        List<RoleMenu> list = list(queryWrapper);
        List<String> checkedKeys = new ArrayList<>();
        for(RoleMenu roleMenu:list){
            checkedKeys.add(roleMenu.getMenuId().toString());
        }
        return checkedKeys;
    }
}
