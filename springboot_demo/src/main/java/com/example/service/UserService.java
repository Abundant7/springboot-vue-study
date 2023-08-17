package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> selectAll();
}
