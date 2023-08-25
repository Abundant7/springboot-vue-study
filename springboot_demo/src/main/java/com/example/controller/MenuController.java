package com.example.controller;


import com.example.common.Result;
import com.example.pojo.Menu;
import com.example.pojo.User;
import com.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author abundant
 * @since 2023-08-24
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;


    @GetMapping("/list")
    public Result list(@RequestParam String roleId) {
        List list = menuService.lambdaQuery().like(Menu::getMenuright, roleId).list();
        return Result.success(list);
    }
}
