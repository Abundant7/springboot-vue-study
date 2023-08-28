package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.QueryPageParam;
import com.example.common.Result;
import com.example.pojo.Goods;

import com.example.service.GoodsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @PostMapping("/save")
    public Result save(@RequestBody Goods goods) {

        return goodsService.save(goods) ? Result.success() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Goods goods) {
        return goodsService.updateById(goods) ? Result.success() : Result.fail();
    }

    //删除
    @GetMapping("/del")
    public Result delete(@RequestParam String id) {
        return goodsService.removeById(id) ? Result.success() : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {

        HashMap param = query.getParam();
        String name = (String) param.get("name");

        Page<Goods> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isEmpty(name)) {
            lambdaQueryWrapper.like(Goods::getName, name);
        }
        IPage result = goodsService.page(page, lambdaQueryWrapper);

        System.out.println(result.getTotal());

        return Result.success(result.getRecords(), result.getTotal());
    }
}
