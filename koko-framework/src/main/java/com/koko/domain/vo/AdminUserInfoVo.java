package com.koko.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-09-17:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserInfoVo {
    private List<String> permis;
    private List<String> roles;
    private UserInfoVo user;

}
