package com.koko.controller;

import com.koko.domain.ResponseResult;
import com.koko.domain.entity.Menu;
import com.koko.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-11-11:50
 */
@RestController
@RequestMapping("/system/menu")
public class SystemMenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 菜单列表
     * @param status
     * @param menuName
     * @return
     */
    @GetMapping("/list")
    public ResponseResult menuList(Integer status,String menuName){
        return menuService.menuList(status,menuName);
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @PostMapping
    public ResponseResult addList(@RequestBody Menu menu){
        return menuService.addList(menu);
    }

    @GetMapping("/{id}")
    public ResponseResult menuDetail(@PathVariable("id") Long id){
        return menuService.menuDetail(id);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long id){
        return menuService.deleteMenu(id);
    }

}
