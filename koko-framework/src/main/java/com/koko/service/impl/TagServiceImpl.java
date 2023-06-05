package com.koko.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koko.domain.ResponseResult;
import com.koko.domain.dto.TagListDto;
import com.koko.domain.entity.Tag;
import com.koko.domain.vo.PageVo;
import com.koko.domain.vo.TagVo;
import com.koko.mapper.TagMapper;
import com.koko.service.TagService;
import com.koko.utils.BeanCopyUtils;
import com.koko.utils.SecurityUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-09 14:55:08
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    /**
     * 列出标签
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     * @return
     */
    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>(pageNum,pageSize);
        page(page, queryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(page.getRecords(), TagVo.class);
        PageVo pageVo = new PageVo(tagVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 新增标签
     * @param name
     * @param remark
     * @return
     */
    @Override
    public ResponseResult addTag(String name, String remark) {
        save(new Tag(name, remark));
        return ResponseResult.okResult();
    }


    /**
     * 获取标签信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult getTagInfo(Long id) {
        Tag tag = getBaseMapper().selectById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }



    /**
     * 修改标签信息
     * @return
     */
    @Override
    public ResponseResult updateTag(Tag tag) {
        saveOrUpdate(tag);
        return ResponseResult.okResult();
    }

    /**
     * 标签删除，逻辑删除
     * @param ids
     * @return
     */
    @Override
    public ResponseResult deleteTags(List<Long> ids) {
        for(Long id:ids){
            LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(Tag::getDelFlag,1).eq(Tag::getId,id);
            update(updateWrapper);
        }
        return ResponseResult.okResult();
    }

    /**
     * 列出所有的标签
     * @return
     */
    @Override
    public ResponseResult listAllTag() {
        List<Tag> list = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }

}
