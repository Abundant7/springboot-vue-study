package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.QueryPageParam;
import com.example.common.Result;
import com.example.pojo.User;
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

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user) {

        return userService.save(user)?Result.success():Result.fail();
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
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return userService.removeById(id);
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
        if(!StringUtils.isEmpty(user.getName())) {
            lambdaQueryWrapper.like(User::getName,user.getName());
        }


        //return userService.list(lambdaQueryWrapper);
        return Result.success(userService.list(lambdaQueryWrapper));
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        System.out.println(query);
        System.out.println("num:"+query.getPageNum());
        System.out.println("size:"+query.getPageSize());
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String sex = (String)param.get("sex");
        System.out.println(name);
        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if(!StringUtils.isEmpty(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }
        if(!StringUtils.isEmpty(sex)){
            lambdaQueryWrapper.eq(User::getSex,sex);
        }

        IPage result = userService.page(page,lambdaQueryWrapper);

        System.out.println(result.getTotal());

        return Result.success(result.getRecords(), result.getTotal());
    }
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no){
        List list = userService.lambdaQuery().eq(User::getNo,no).list();
        return list.size()>0?Result.success(list):Result.fail();
    }


}
