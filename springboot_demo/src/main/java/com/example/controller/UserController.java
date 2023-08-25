package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.QueryPageParam;
import com.example.common.Result;
import com.example.pojo.Menu;
import com.example.pojo.User;
import com.example.service.MenuService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author abundant
 * @since 2023-08-17
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user) {

        return userService.save(user) ? Result.success() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        return userService.updateById(user) ? Result.success() : Result.fail();
    }

    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    //删除
    @GetMapping("/del")
    public Result delete(@RequestParam String id) {

        return userService.removeById(id) ? Result.success() : Result.fail();
    }

    //查询
    /*@PostMapping("/listP")
    public List<User> listP(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(user.getName())) {
            lambdaQueryWrapper.like(User::getName,user.getName());
        }

        return userService.list(lambdaQueryWrapper);
    }*/
    @PostMapping("/listP")
    public Result listP(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(user.getName())) {
            lambdaQueryWrapper.like(User::getName, user.getName());
        }


        //return userService.list(lambdaQueryWrapper);
        return Result.success(userService.list(lambdaQueryWrapper));
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        System.out.println(query);
        System.out.println("num:" + query.getPageNum());
        System.out.println("size:" + query.getPageSize());
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String sex = (String) param.get("sex");
        String roleId = (String) param.get("roleId");
        System.out.println(name);
        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isEmpty(name)) {
            lambdaQueryWrapper.like(User::getName, name);
        }
        if (!StringUtils.isEmpty(sex)) {
            lambdaQueryWrapper.eq(User::getSex, sex);
        }
        if (!StringUtils.isEmpty(roleId)) {
            lambdaQueryWrapper.eq(User::getRoleId, roleId);
        }

        IPage result = userService.page(page, lambdaQueryWrapper);

        System.out.println(result.getTotal());

        return Result.success(result.getRecords(), result.getTotal());
    }

    //根据账号查询
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        List list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size() > 0 ? Result.success(list) : Result.fail();
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        List list = userService.lambdaQuery().eq(User::getNo, user.getNo()).
                eq(User::getPassword, user.getPassword()).
                list();

        if(list.size()>0){
            User user1 = (User)list.get(0);
            List menulist = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();

            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",menulist);

            return Result.success(res);
        }

        return Result.fail();
    }

}
