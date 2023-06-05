package com.koko.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-13-9:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAddDto {
    private String name;
    private String description;
    private String status;
}
