package com.koko.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koko.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-09 16:57:58
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> getRouters(Long userId);

    List<Menu> selectAllMenu();

    List<Menu> selectMenuById(Long userId);
}
