package com.koko.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.domain.ResponseResult;
import com.koko.domain.entity.Menu;
import com.koko.domain.vo.RoutersVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-03-09 16:57:59
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    RoutersVo getRouters(Long userId);

    ResponseResult menuList(Integer status, String menuName);

    ResponseResult addList(Menu menu);

    ResponseResult updateMenu(Menu menu);

    ResponseResult menuDetail(Long id);

    ResponseResult deleteMenu(Long id);
}
