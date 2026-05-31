package com.nuist.bookms.service;

import com.nuist.bookms.entity.User;

import java.util.List;

public interface UserService {
    // 判断用户是否存在
    boolean ifExist(String username);

    // 用户注册
    boolean register(User user);

    // 用户登录
    boolean login(String username, String password);

    // 根据id查询用户
    User selectByUserId(Integer userId);

    // 修改用户信息(包括密码)
    boolean updateUser(User user);

    // 分页查询用户列表
    List<User> selectByPage(int pageNum, int pageSize);

}
