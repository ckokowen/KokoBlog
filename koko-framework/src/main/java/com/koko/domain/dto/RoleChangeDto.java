package com.koko.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-11-14:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleChangeDto {
    private String roleId;
    private String status;
}
