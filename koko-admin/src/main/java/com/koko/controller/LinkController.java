package com.koko.controller;

import com.koko.domain.ResponseResult;
import com.koko.domain.dto.LinkAddDto;
import com.koko.domain.entity.Link;
import com.koko.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-13-10:17
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 友链列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listLink(Integer pageNum,Integer pageSize,String name,String status){
        return linkService.getLinkBy(pageNum,pageSize,name,status);
    }

    /**
     * 新增友链
     * @param link
     * @return
     */
    @PostMapping
    public ResponseResult addLink(@RequestBody LinkAddDto link){
        return linkService.addLink(link);
    }

    /**
     * 修改友链回显信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult updateReturnLink(@PathVariable("id") Long id){
        return linkService.updateReturnLink(id);
    }

    /**
     * 修改友链
     * @param link
     * @return
     */
    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        return linkService.updateLink(link);
    }

    /**
     * 删除友链
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id){
        return linkService.deleteLink(id);
    }

    /**
     * 修改友链状态
     * @param map
     * @return
     */
    @PutMapping("/changeLinkStatus")
    public ResponseResult changeLinkStatus(@RequestBody Map<String,String> map){
        return linkService.changeLinkStatus(map.get("id"),map.get("status"));
    }
}
