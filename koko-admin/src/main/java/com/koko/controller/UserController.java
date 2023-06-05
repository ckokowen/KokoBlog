package com.koko.controller;

import com.koko.domain.ResponseResult;
import com.koko.domain.dto.UserAddDto;
import com.koko.domain.dto.UserUpdateDto;
import com.koko.service.RoleService;
import com.koko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-11-21:50
 */
@RestController
@RequestMapping("/system")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 用户列表
     * @param pageNum
     * @param pageSize
     * @param userName
     * @param phonenumber
     * @param status
     * @return
     */
    @GetMapping("/user/list")
    public ResponseResult userList(Integer pageNum,Integer pageSize,String userName,String phonenumber,String status){
        return userService.userAllList(pageNum,pageSize,userName,phonenumber,status);
    }

    /**
     * 查询是所有状态正常的角色
     * @return
     */
    @GetMapping("/role/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }

    /**
     * 添加用户
     * @param userAddDto
     * @return
     */
    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody UserAddDto userAddDto){
        return userService.addUser(userAddDto);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/user/{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }

    /**
     * 修改用户回显用户信息
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public ResponseResult updateReturnUser(@PathVariable("id") Long id){
        return userService.returnViewUser(id);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody UserUpdateDto user){
        return userService.updateUser(user);
    }

}
