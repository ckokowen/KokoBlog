package com.koko.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-11-16:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAddDto {

    private String roleName;
    private String roleKey;
    private Integer roleSort;
    private String status;
    private List<String> menuIds;
    private String remark;

}
