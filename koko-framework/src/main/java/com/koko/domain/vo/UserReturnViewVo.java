package com.koko.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-13-9:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReturnViewVo {
    private List<String> roleIds;
    private List<RoleAddVo> roles;
    private UserReturnInfoVo user;
}
