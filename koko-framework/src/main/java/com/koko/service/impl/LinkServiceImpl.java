package com.koko.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koko.constants.SystemConstants;
import com.koko.domain.ResponseResult;
import com.koko.domain.dto.LinkAddDto;
import com.koko.domain.entity.Link;
import com.koko.domain.vo.LinkListVo;
import com.koko.domain.vo.LinkVo;
import com.koko.domain.vo.PageVo;
import com.koko.mapper.LinkMapper;
import com.koko.service.LinkService;
import com.koko.utils.BeanCopyUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-03-04 13:25:10
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(lambdaQueryWrapper);
        //转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    /**
     * 查询友链列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    @Override
    public ResponseResult getLinkBy(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(name),Link::getName,name)
                .like(Strings.isNotEmpty(status),Link::getStatus,status);
        Page<Link> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<LinkListVo> linkListVos = BeanCopyUtils.copyBeanList(page.getRecords(), LinkListVo.class);
        PageVo pageVo = new PageVo(linkListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 新增友链
     * @param link
     * @return
     */
    @Override
    public ResponseResult addLink(LinkAddDto link) {
        Link linkAdd = BeanCopyUtils.copyBean(link, Link.class);
        saveOrUpdate(linkAdd);
        return ResponseResult.okResult();
    }

    /**
     * 修改友链回显信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult updateReturnLink(Long id) {
        //查询
        Link link = getById(id);
        return ResponseResult.okResult(link);
    }

    /**
     * 修改友链
     * @param link
     * @return
     */
    @Override
    public ResponseResult updateLink(Link link) {
        saveOrUpdate(link);
        return ResponseResult.okResult();
    }

    /**
     * 删除友链
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteLink(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 修改友链状态
     * @param id
     * @param status
     * @return
     */
    @Override
    public ResponseResult changeLinkStatus(String id, String status) {
        LambdaUpdateWrapper<Link> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Link::getId,id)
                .set(Link::getStatus,status);
        update(updateWrapper);
        return ResponseResult.okResult();
    }
}
