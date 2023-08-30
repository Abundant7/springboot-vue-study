package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.pojo.Record;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author abundant
 * @since 2023-08-28
 */
public interface RecordService extends IService<Record> {
    IPage  pageCC(IPage<Record> page, Wrapper wrapper);


}
