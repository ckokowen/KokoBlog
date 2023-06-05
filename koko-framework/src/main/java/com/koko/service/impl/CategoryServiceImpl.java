package com.koko.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koko.constants.SystemConstants;
import com.koko.domain.ResponseResult;
import com.koko.domain.dto.CategoryAddDto;
import com.koko.domain.entity.Article;
import com.koko.domain.entity.Category;
import com.koko.domain.vo.CategoryListVo;
import com.koko.domain.vo.CategoryVo;
import com.koko.domain.vo.PageVo;
import com.koko.mapper.CategoryMapper;
import com.koko.service.ArticleService;
import com.koko.service.CategoryService;
import com.koko.utils.BeanCopyUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-03-03 12:45:48
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;


    @Override
    public ResponseResult getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    /**
     * 分页查询分类列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    @Override
    public ResponseResult listCategoryBy(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(name),Category::getName,name)
                .like(Strings.isNotEmpty(status),Category::getStatus,status);
        Page<Category> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);
        //封装
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryListVo.class);
        PageVo pageVo = new PageVo(categoryListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(CategoryAddDto category) {
        Category categoryAdd = BeanCopyUtils.copyBean(category, Category.class);
        saveOrUpdate(categoryAdd);
        return ResponseResult.okResult();
    }

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @Override
    public ResponseResult updateReturnCategory(Long id) {
        Category category = getById(id);
        CategoryListVo categoryListVo = BeanCopyUtils.copyBean(category,CategoryListVo.class);
        return ResponseResult.okResult(categoryListVo);
    }

    /**
     * 修改分类
     * @param category
     * @return
     */
    @Override
    public ResponseResult updateCategory(CategoryListVo category) {
        Category categoryUpdate = BeanCopyUtils.copyBean(category, Category.class);
        saveOrUpdate(categoryUpdate);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}
