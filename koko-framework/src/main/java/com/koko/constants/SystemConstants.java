package com.koko.constants;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-03-10:28
 */
public class SystemConstants {
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    public static final String STATUS_NORMAL = "0";
    /**
     * 友联
     * 0审核通过，1在审核，2审核未通过
     */
    public static final String LINK_STATUS_NORMAL = "0";
    public static final String LINK_STATUS_DRAFT = "1";
    public static final String LINK_STATUS_REFUSED = "2";
    /*
        评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /*
        评论类型为：友联评论
     */
    public static final String LINK_COMMENT = "1";


    /*
        M目录 C菜单 F按钮
     */
    public static final String MENU = "C";
    public static final String BUTTON = "F";

    /*
        分类表中状态为0表示
     */
    public static final String NORMAL = "0";
    /*
        1为管理员0为普通用户
     */
    public static final String ADMAIN = "1";
}
