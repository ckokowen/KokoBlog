package com.koko.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-04-13:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkVo {
    @TableId
    private Long id;


    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;
}
