package com.koko.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-11-16:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleReturnViewVo {
    private Long id;
    private String roleName;
    private String roleKey;
    private Integer roleSort;
    private String status;
    private String remark;
}
