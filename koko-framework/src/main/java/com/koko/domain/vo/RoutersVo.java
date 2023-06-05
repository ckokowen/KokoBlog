package com.koko.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-09-22:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {
    private List<MenuVo> menus;
}
