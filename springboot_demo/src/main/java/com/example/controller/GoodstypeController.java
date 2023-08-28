package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.QueryPageParam;
import com.example.common.Result;
import com.example.pojo.Goodstype;

import com.example.service.GoodstypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author abundant
 * @since 2023-08-25
 */
@CrossOrigin
@RestController
@RequestMapping("/goodstype")

public class GoodstypeController {
    @Autowired
    private GoodstypeService goodstypeService;

    @PostMapping("/save")
    public Result save(@RequestBody Goodstype goodstype) {

        return goodstypeService.save(goodstype) ? Result.success() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Goodstype goodstype) {
        return goodstypeService.updateById(goodstype) ? Result.success() : Result.fail();
    }

    //删除
    @GetMapping("/del")
    public Result delete(@RequestParam String id) {

        return goodstypeService.removeById(id) ? Result.success() : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {

        HashMap param = query.getParam();
        String name = (String) param.get("name");

        Page<Goodstype> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goodstype> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isEmpty(name)) {
            lambdaQueryWrapper.like(Goodstype::getName, name);
        }

        IPage result = goodstypeService.page(page, lambdaQueryWrapper);

        System.out.println(result.getTotal());

        return Result.success(result.getRecords(), result.getTotal());
    }
    @GetMapping("/list")
    public Result list() {
        List list = goodstypeService.list();
        return Result.success(list);
    }
}
