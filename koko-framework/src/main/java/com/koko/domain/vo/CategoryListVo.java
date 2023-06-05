package com.koko.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-13-9:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListVo {
    private Long id;
    private String name;
    private String description;
    private String status;
}
