package com.koko.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-03-14:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {

    private Long id;
    private String name;
    private String description;

}
