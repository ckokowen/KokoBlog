package com.koko.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-11-14:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MenuTreeVo {
    private List<MenuTreeVo> children;
    private Long id;
    private String label;
    private Long parentId;

    public MenuTreeVo(Long id, String label, Long parentId) {
        this.id = id;
        this.label = label;
        this.parentId = parentId;
    }
}
