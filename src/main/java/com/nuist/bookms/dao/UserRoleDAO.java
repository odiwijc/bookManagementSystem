package com.nuist.bookms.dao;

import com.nuist.bookms.entity.Role;

import java.util.List;

public interface UserRoleDAO {
    // 为用户分配角色
    int insert(int userId, int roleId);

    // 移除用户的某一个角色
    int delete(int userId, int roleId);

    // 删除用户的所有角色（检查用户status需要为0）
    int deleteByUserId(int userId);

    // 查询用户拥有的所有RoleId
    List<Integer> selectRoleIdsByUserId(int userId);

    // 查询用户拥有的所有Role，列表
    List<Role> selectRolesByUserId(int userId);

    // 判断用户是否拥有某个角色
    boolean exists(int userId, int roleId);
}
