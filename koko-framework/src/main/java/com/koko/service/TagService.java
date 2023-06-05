package com.koko.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.domain.ResponseResult;
import com.koko.domain.dto.TagListDto;
import com.koko.domain.entity.Tag;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-03-09 14:55:08
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(String name, String remark);

//    ResponseResult deleteTag(Long id);

    ResponseResult getTagInfo(Long id);

    ResponseResult updateTag(Tag tag);

    ResponseResult deleteTags(List<Long> ids);

    ResponseResult listAllTag();
}
