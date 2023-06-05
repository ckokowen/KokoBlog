package com.koko.service.impl;

import com.koko.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-10-17:58
 */
@Service("ps")
public class PermissionService {

    /**
     * 查询用户是否拥有该请求的权限
     * @param permsNeeded
     * @return true为有权限
     */
    public boolean hasPermission(String permsNeeded){
        if(SecurityUtils.isAdmin()){
            return true;
        }
        List<String> permsHave = SecurityUtils.getLoginUser().getPerms();
        return permsHave.contains(permsNeeded);
    }
}
