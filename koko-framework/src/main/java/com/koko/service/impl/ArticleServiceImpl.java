package com.koko.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koko.constants.SystemConstants;
import com.koko.domain.ResponseResult;
import com.koko.domain.dto.AddArticleDto;
import com.koko.domain.dto.UpdateArticleDto;
import com.koko.domain.entity.Article;
import com.koko.domain.entity.ArticleTag;
import com.koko.domain.entity.Category;
import com.koko.domain.vo.*;
import com.koko.mapper.ArticleMapper;
import com.koko.service.ArticleService;
import com.koko.service.ArticleTagService;
import com.koko.service.CategoryService;
import com.koko.utils.BeanCopyUtils;
import com.koko.utils.RedisCache;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-02-20:41
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();
/*        //bean拷贝
        List<HotArticleVo> hotArticleVos = new ArrayList<>();
        for (Article article: articles) {
            HotArticleVo hotArticleVo = new HotArticleVo();
            BeanUtils.copyProperties(article,hotArticleVo);
            hotArticleVos.add(hotArticleVo);
        }*/

        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId > 0,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        //查询categoryName
        List<Article> articles = page.getRecords();
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        /* //根据categoryId查询categoryName
       for(Article article : articles){
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category.getName());
        }*/
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Integer id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        //根据分类id查询分类名
        Category category = categoryService.getById(articleVo.getCategoryId());
        if(category != null){
            articleVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleVo);
    }

    /**
     * 将Redis中对应Id的文章的阅读量增加1
     * @param id
     * @return
     */
    @Override
    public ResponseResult updateViewCount(Integer id) {
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    /**
     * 新增博文
     * @param addArticleDto
     * @return
     */
    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult addArticle(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);
        List<Long> tags = addArticleDto.getTags();
        List<ArticleTag> articleTags = new ArrayList<>();
        for(Long tag:tags){
            articleTags.add(new ArticleTag(article.getId(),tag));
        }
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    /**
     * 后台管理系统：内容管理--文章管理
     * 根据标题和摘要模糊查询，列出文章列表
     * @param pageNum
     * @param pageSize
     * @param title
     * @param summary
     * @return
     */
    @Override
    public ResponseResult adminList(Integer pageNum, Integer pageSize, String title, String summary) {
        //设置模糊查询
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!Strings.isEmpty(title),Article::getTitle,title)
                    .like(!Strings.isEmpty(summary),Article::getSummary,summary);
        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page, queryWrapper);
        List<Article> articleList = page.getRecords();
        //封装
        List<ArticleLikeVo> articleLikeVos = BeanCopyUtils.copyBeanList(articleList, ArticleLikeVo.class);
        PageVo pageVo = new PageVo(articleLikeVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 内容管理--文章管理
     * 修改文章之显示文章详情
     * @param id
     * @return
     */
    @Override
    public ResponseResult articleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //封装
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        return ResponseResult.okResult(articleDetailVo);
    }

    /**
     * 内容管理--文章管理
     * 修改文章
     * @param updateArticleDto
     * @return
     */
    @Override
    public ResponseResult updateArticle(UpdateArticleDto updateArticleDto) {
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        saveOrUpdate(article);
        return ResponseResult.okResult();
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteArticle(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}
