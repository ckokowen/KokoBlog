package com.koko.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koko.constants.SystemConstants;
import com.koko.domain.ResponseResult;
import com.koko.domain.dto.RoleAddDto;
import com.koko.domain.dto.RoleChangeDto;
import com.koko.domain.dto.RoleUpdateDto;
import com.koko.domain.entity.Menu;
import com.koko.domain.entity.Role;
import com.koko.domain.entity.RoleMenu;
import com.koko.domain.vo.*;
import com.koko.mapper.MenuMapper;
import com.koko.mapper.RoleMapper;
import com.koko.service.MenuService;
import com.koko.service.RoleMenuService;
import com.koko.service.RoleService;
import com.koko.utils.BeanCopyUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-09 16:58:10
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long userId) {
        //如果是超管直接返回admin
        if(userId == 1L){
            List<String> role = new ArrayList<>();
            role.add("admin");
            return role;
        }
        //其他用户查询之后返回
        return getBaseMapper().selectRoleKeyByUserId(userId);
    }

    /**
     * 角色列表分页查询
     * 根据角色名称和状态模糊查询
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @param status
     * @return
     */
    @Override
    public ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(roleName),Role::getRoleName,roleName)
                    .like(Strings.isNotEmpty(status),Role::getStatus,status)
                    .orderByAsc(Role::getRoleSort);
        //分页查询
        Page<Role> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Role> records = page.getRecords();
        //封装
        List<RoleListVo> roleListVos = BeanCopyUtils.copyBeanList(records, RoleListVo.class);
        PageVo pageVo = new PageVo(roleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 修改角色的状态
     * @param roleChangeDto
     * @return
     */
    @Override
    public ResponseResult changeStatus(RoleChangeDto roleChangeDto) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Role::getId,Long.valueOf(roleChangeDto.getRoleId()))
                    .set(Role::getStatus,roleChangeDto.getStatus());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 获取菜单树接口
     * @return
     */
    @Override
    public ResponseResult permsTree() {
        List<Menu> menus = menuService.list();
        //把menuName字段赋值给label
        List<MenuTreeVo> allMenu = menus.stream().map(result -> new MenuTreeVo(result.getId(), result.getMenuName(), result.getParentId()))
                .collect(Collectors.toList());
        List<MenuTreeVo> menuTreeVo = new ArrayList<>();
        for(MenuTreeVo menuTree:allMenu){
            if(menuTree.getParentId() == 0L){
                menuTreeVo.add(menuTree);
                //寻找此菜单的子菜单
                List<MenuTreeVo> childList = searchChild(menuTree.getId(),allMenu);
                menuTree.setChildren(childList);
            }
        }
        return ResponseResult.okResult(menuTreeVo);
    }

    /**
     * 新增用户
     * @param roleAddDto
     * @return
     */
    @Override
    public ResponseResult addRole(RoleAddDto roleAddDto) {
        //把新角色存入数据库
        Role role = BeanCopyUtils.copyBean(roleAddDto, Role.class);
        save(role);
        //从数据库中查询出新角色的id
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getRoleName,role.getRoleName());
        Role newRole = getOne(queryWrapper);
        return roleMenuService.addMenuIds(newRole.getId(),roleAddDto.getMenuIds());
    }

    @Override
    public ResponseResult updateReturnView(Long id) {
        Role role = getById(id);
        RoleReturnViewVo roleReturnViewVo = BeanCopyUtils.copyBean(role, RoleReturnViewVo.class);
        return ResponseResult.okResult(roleReturnViewVo);
    }

    /**
     * 回显角色所拥有的菜单权限
     * @param id
     * @return
     */
    @Override
    public ResponseResult updateReturnTree(Long id) {
        //先获取菜单树
        List<MenuTreeVo> tree = (List<MenuTreeVo>) permsTree().getData();
        //获取角色所关联的菜单权限id列表
        List<String> checkedKeys = roleMenuService.getRoleMenuById(id);
        RoleReturnTreeVo roleReturnTreeVo = new RoleReturnTreeVo(tree,checkedKeys);
        return ResponseResult.okResult(roleReturnTreeVo);
    }

    /**
     * 修改角色信息
     * @param role
     * @return
     */
    @Override
    public ResponseResult updateRole(RoleUpdateDto role) {
        Role updateRole = BeanCopyUtils.copyBean(role, Role.class);
        //更新角色表
        saveOrUpdate(updateRole);
        //更新角色菜单表
        roleMenuService.addMenuIds(role.getId(),role.getMenuIds());
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long id) {
        //删除用户表
        removeById(id);
        //删除用户菜单表
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        roleMenuService.remove(queryWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 查询所有状态正常的角色
     * @return
     */
    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> list = list(queryWrapper);
        List<RoleAddVo> roleAddVos = BeanCopyUtils.copyBeanList(list, RoleAddVo.class);
        return ResponseResult.okResult(roleAddVos);
    }


    /**
     * 递归寻找此菜单的子菜单
     * @param id
     * @param allMenu
     * @return
     */
    private List<MenuTreeVo> searchChild(Long id, List<MenuTreeVo> allMenu) {
        List<MenuTreeVo> childrenList = new ArrayList<>();
        for(MenuTreeVo menuVo:allMenu){
            //获取菜单的id
            Long parentId = menuVo.getParentId();
            //当发现菜单中有这个菜单的子菜单
            if(id.intValue() == parentId.intValue()){
                //递归查询子菜单的子菜单
                childrenList.add(menuVo);
                List<MenuTreeVo> children = searchChild(menuVo.getId(),allMenu);
                menuVo.setChildren(children);
            }
        }
        return childrenList;
    }
}
