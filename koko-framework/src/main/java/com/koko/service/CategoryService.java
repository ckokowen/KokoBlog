package com.koko.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.domain.ResponseResult;
import com.koko.domain.dto.CategoryAddDto;
import com.koko.domain.entity.Category;
import com.koko.domain.vo.CategoryListVo;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-03-03 12:45:48
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult listCategoryBy(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(CategoryAddDto category);

    ResponseResult updateReturnCategory(Long id);

    ResponseResult updateCategory(CategoryListVo category);

    ResponseResult deleteCategory(Long id);
}
