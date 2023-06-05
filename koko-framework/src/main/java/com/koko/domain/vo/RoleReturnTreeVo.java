package com.koko.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-11-16:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleReturnTreeVo {
    private List<MenuTreeVo> menus;
    private List<String> checkedKeys;
}
