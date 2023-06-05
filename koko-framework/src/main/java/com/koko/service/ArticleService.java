package com.koko.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.domain.ResponseResult;
import com.koko.domain.dto.AddArticleDto;
import com.koko.domain.dto.UpdateArticleDto;
import com.koko.domain.entity.Article;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-02-20:41
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Integer id);

    ResponseResult updateViewCount(Integer id);

    ResponseResult addArticle(AddArticleDto addArticleDto);

    ResponseResult adminList(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult articleDetail(Long id);

    ResponseResult updateArticle(UpdateArticleDto updateArticleDto);

    ResponseResult deleteArticle(Long id);
}
