package com.koko.controller;

import com.koko.domain.ResponseResult;
import com.koko.domain.dto.RoleAddDto;
import com.koko.domain.dto.RoleChangeDto;
import com.koko.domain.dto.RoleUpdateDto;
import com.koko.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-11-13:51
 */
@RestController
@RequestMapping("/system")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色列表
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @param status
     * @return
     */
    @GetMapping("/role/list")
    public ResponseResult roleList(Integer pageNum,Integer pageSize,String roleName,String status){
        return roleService.roleList(pageNum,pageSize,roleName,status);
    }

    /**
     *
     * @param roleChangeDto
     * @return
     */
    @PutMapping("/role/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleChangeDto roleChangeDto){
        return roleService.changeStatus(roleChangeDto);
    }

    /**
     * 获取菜单树接口
     * @return
     */
    @GetMapping("/menu/treeselect")
    public ResponseResult permsTree(){
        return roleService.permsTree();
    }

    /**
     * 新增用户
     * @param roleAddDto
     * @return
     */
    @PostMapping("/role")
    public ResponseResult addRole(@RequestBody RoleAddDto roleAddDto){
        return roleService.addRole(roleAddDto);
    }

    /**
     * 回显角色信息
     * @param id
     * @return
     */
    @GetMapping("/role/{id}")
    public ResponseResult updateReturnView(@PathVariable("id") Long id){
        return roleService.updateReturnView(id);
    }

    /**
     * 加载对应角色菜单列表树
     * @param id
     * @return
     */
    @GetMapping("/menu/roleMenuTreeselect/{id}")
    public ResponseResult updateReturnTree(@PathVariable("id") Long id){
        return roleService.updateReturnTree(id);
    }

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    @PutMapping("/role")
    public ResponseResult update(@RequestBody RoleUpdateDto role){
        return roleService.updateRole(role);
    }

    /**
     * 删除角色信息
     * @param id
     * @return
     */
    @DeleteMapping("/role/{id}")
    public ResponseResult delete(@PathVariable("id") Long id){
        return roleService.delete(id);
    }

}
