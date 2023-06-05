package com.koko.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-04-12:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo {
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 所属分类id
     */
    private Long categoryId;
    private String categoryName;

    /**
     * 访问量
     */
    private Long viewCount;
    /**
     * 是否允许评论 1是，0否
     */
    private String isComment;


    private Date createTime;

}
