package com.koko.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koko.constants.SystemConstants;
import com.koko.domain.ResponseResult;
import com.koko.domain.entity.Menu;
import com.koko.domain.vo.MenuDetailVo;
import com.koko.domain.vo.MenuListVo;
import com.koko.domain.vo.MenuVo;
import com.koko.domain.vo.RoutersVo;
import com.koko.mapper.MenuMapper;
import com.koko.service.MenuService;
import com.koko.utils.BeanCopyUtils;
import com.koko.utils.SecurityUtils;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-09 16:58:00
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long userId) {
        //如果用户id为1，返回所有的权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> list = list(queryWrapper);
            List<String> perms = list.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则查询用户所具有的权限并返回
        return getBaseMapper().selectPermsByUserId(userId);
    }

    @Override
    public RoutersVo getRouters(Long userId) {
        //如果用户id为1，返回所有的菜单
        if(SecurityUtils.isAdmin()){
            List<Menu> list = getBaseMapper().selectAllMenu();
            return getUserMenu(list);
        }
        //否则查询用户所具有的菜单
        else{
            List<Menu> list = getBaseMapper().selectMenuById(userId);
            return getUserMenu(list);
        }
    }

    /**
     * 菜单列表
     * @param status
     * @param menuName
     * @return
     */
    @Override
    public ResponseResult menuList(Integer status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!Strings.isEmpty(menuName),Menu::getMenuName,menuName)
                    .orderByAsc(Menu::getParentId)
                    .orderByAsc(Menu::getOrderNum);
        //查询菜单列表
        List<Menu> list = list(queryWrapper);
        //封装
        List<MenuListVo> menuListVos = BeanCopyUtils.copyBeanList(list, MenuListVo.class);
        return ResponseResult.okResult(menuListVos);
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @Override
    public ResponseResult addList(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @Override
    public ResponseResult updateMenu(Menu menu) {
        saveOrUpdate(menu);
        return ResponseResult.okResult();
    }

    /**
     * 修改菜单之显示菜单信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult menuDetail(Long id) {
        Menu menu = getById(id);
        MenuDetailVo menuDetailVo = BeanCopyUtils.copyBean(menu, MenuDetailVo.class);
        return ResponseResult.okResult(menuDetailVo);
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteMenu(Long id) {
        Menu menu = getById(id);
        Long menuId = menu.getId();
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuId);
        List<Menu> list = list(queryWrapper);
        if(!list.isEmpty()){
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 查找用户的所有菜单，并将其组成父子关系集合
     * @param list
     * @return
     */
    private RoutersVo getUserMenu(List<Menu> list) {
        //将所有菜单转换为Vo，allMenuVos为所有菜单集合
        List<MenuVo> allMenuVos = BeanCopyUtils.copyBeanList(list, MenuVo.class);
        //创建新集合，用于放置有父子关系的集合
        List<MenuVo> menuVos = new ArrayList<>();
        for(MenuVo menuVo:allMenuVos){
            if(menuVo.getParentId() == 0L){
                menuVos.add(menuVo);
                //寻找此菜单的子菜单
                List<MenuVo> childList = searchChild(menuVo.getId(),allMenuVos);
                menuVo.setChildren(childList);
            }
        }
        RoutersVo routersVo = new RoutersVo(menuVos);
        return routersVo;
    }


    /**
     * 递归寻找此菜单的子菜单
     * @param id
     * @param allMenuVos
     * @return
     */
    private List<MenuVo> searchChild(Long id, List<MenuVo> allMenuVos) {
        List<MenuVo> childrenList = new ArrayList<>();
        for(MenuVo menuVo:allMenuVos){
            //获取菜单的id
            Long parentId = menuVo.getParentId();
            //当发现菜单中有这个菜单的子菜单
            if(id.intValue() == parentId.intValue()){
                //递归查询子菜单的子菜单
                childrenList.add(menuVo);
                List<MenuVo> children = searchChild(menuVo.getId(),allMenuVos);
                menuVo.setChildren(children);
            }
        }
        return childrenList;
    }


}
