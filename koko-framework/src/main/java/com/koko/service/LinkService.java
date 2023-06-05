package com.koko.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.domain.ResponseResult;
import com.koko.domain.dto.LinkAddDto;
import com.koko.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-04 13:25:10
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult getLinkBy(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(LinkAddDto link);

    ResponseResult updateReturnLink(Long id);

    ResponseResult updateLink(Link link);

    ResponseResult deleteLink(Long id);

    ResponseResult changeLinkStatus(String id, String status);
}
