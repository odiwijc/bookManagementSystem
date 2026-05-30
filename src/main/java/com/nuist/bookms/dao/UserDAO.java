package com.nuist.bookms.dao;

import com.nuist.bookms.entity.User;

import java.util.List;

public interface UserDAO {
    // 插入用户（注册）
    int insert(User user);

    // 根据用户名查询
    User selectByUsername(String username);

    // 根据用户名和密码查询
    int selectByUsernameAndPassword(String username,String password);

    // 根据用户id查询
    User selectByUserId(Integer userId);

    // 查询所有用户
    List<User> selectAll();

    // 分页查询用户
    List<User> selectByPage(int pageNum,int pageSize);

    // 更新用户信息
    int update(User user);

    // 删除用户
    int deleteById(Integer userId);

    // 启用/禁用用户
    int updateStatus(int userId,int status);
    
}
