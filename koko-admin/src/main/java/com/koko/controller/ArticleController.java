package com.koko.controller;

import com.koko.domain.ResponseResult;
import com.koko.domain.dto.AddArticleDto;
import com.koko.domain.dto.UpdateArticleDto;
import com.koko.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-10-15:33
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto){
        return articleService.addArticle(addArticleDto);
    }

    /**
     * 列出文章列表，并根据标题和摘要模糊查询
     * @param pageNum
     * @param pageSize
     * @param title
     * @param summary
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String title,String summary){
        return articleService.adminList(pageNum,pageSize,title,summary);
    }

    /**
     * 获取文章详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult articleDetail(@PathVariable("id") Long id){
        return articleService.articleDetail(id);
    }

    /**
     * 修改文章
     * @param updateArticleDto
     * @return
     */
    @PutMapping
    public ResponseResult updateArticle(@RequestBody UpdateArticleDto updateArticleDto){
        return articleService.updateArticle(updateArticleDto);
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Long id){
        return articleService.deleteArticle(id);
    }
}
