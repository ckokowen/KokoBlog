package com.koko.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-13-10:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkListVo {
    private Long id;


    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;
    private String status;
}
