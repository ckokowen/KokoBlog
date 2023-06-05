package com.koko.controller;

import com.koko.domain.ResponseResult;
import com.koko.domain.dto.TagListDto;
import com.koko.domain.entity.Tag;
import com.koko.domain.vo.TagVo;
import com.koko.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-09-14:58
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody Tag tag){
        return tagService.addTag(tag.getName(),tag.getRemark());
    }

//    @DeleteMapping("/{id}")
//    public ResponseResult deleteTag(@PathVariable("id") Long id){
//        return tagService.deleteTag(id);
//    }

    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable("id")Long id){
        return tagService.getTagInfo(id);
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody Tag tag){
        return tagService.updateTag(tag);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteTags(@PathVariable("ids") List<Long> ids){
        return tagService.deleteTags(ids);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }

}
