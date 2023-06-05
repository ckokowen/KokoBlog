package com.koko.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-04-10:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVo {
    private Long id;
    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    private String categoryName;
    /**
     * 缩略图
     */
    private String thumbnail;
    /**
     * 访问量
     */
    private Long viewCount;

    private Date createTime;

}
