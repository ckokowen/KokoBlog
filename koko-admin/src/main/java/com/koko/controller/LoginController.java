package com.koko.controller;

import com.koko.domain.ResponseResult;
import com.koko.domain.entity.LoginUser;
import com.koko.domain.entity.User;
import com.koko.domain.vo.AdminUserInfoVo;
import com.koko.domain.vo.MenuVo;
import com.koko.domain.vo.RoutersVo;
import com.koko.domain.vo.UserInfoVo;
import com.koko.enums.AppHttpCodeEnum;
import com.koko.exception.SystemException;
import com.koko.service.LoginService;
import com.koko.service.MenuService;
import com.koko.service.RoleService;
import com.koko.utils.BeanCopyUtils;
import com.koko.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-09-15:21
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService systemLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;


    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return systemLoginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> permis = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询用户角色信息
        List<String> roles = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //封装数据返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(permis,roles,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult getRouters(){
        //获取登录用户id
        Long userId = SecurityUtils.getUserId();
        //查询用户所对应的menus中的菜单
        RoutersVo routers = menuService.getRouters(userId);
        return ResponseResult.okResult(routers);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return systemLoginService.logout();
    }
}
