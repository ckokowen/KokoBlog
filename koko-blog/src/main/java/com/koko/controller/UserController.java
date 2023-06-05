package com.koko.controller;

import com.koko.annotation.SystemLog;
import com.koko.domain.ResponseResult;
import com.koko.domain.entity.User;
import com.koko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-08-13:43
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(businessName = "显示用户信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "注册")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }

}
