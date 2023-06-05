package com.koko.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.koko.domain.ResponseResult;
import com.koko.domain.dto.CategoryAddDto;
import com.koko.domain.entity.Category;
import com.koko.domain.vo.CategoryListVo;
import com.koko.domain.vo.ExcelCategoryVo;
import com.koko.enums.AppHttpCodeEnum;
import com.koko.mapper.CategoryMapper;
import com.koko.service.CategoryService;
import com.koko.utils.BeanCopyUtils;
import com.koko.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-10-15:16
 */
@RestController
@RequestMapping("/content/category")
public class CategoryContentController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> list = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(list,ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE)
                    .sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    /**
     * 分页查询分类列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listCategory(Integer pageNum,Integer pageSize,String name,String status){
        return categoryService.listCategoryBy(pageNum,pageSize,name,status);
    }

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryAddDto category){
        return categoryService.addCategory(category);
    }

    /**
     * 修改分类回显分类信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult updateReturnCategory(@PathVariable("id") Long id){
        return categoryService.updateReturnCategory(id);
    }

    /**
     * 修改分类
     * @param category
     * @return
     */
    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryListVo category){
        return categoryService.updateCategory(category);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public ResponseResult deleteCategory(@PathVariable("id") Long id){
        return categoryService.deleteCategory(id);
    }

}
