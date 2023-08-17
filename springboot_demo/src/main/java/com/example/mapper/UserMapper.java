package com.example.mapper;

import com.example.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author abundant
 * @since 2023-08-17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
