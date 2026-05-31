package com.nuist.bookms.dao;

import com.nuist.bookms.entity.Permission;

import java.util.List;

public interface PermissionDAO {
    // 根据权限ID查询
    Permission selectById(int permId);

    // 根据权限名查询
    Permission selectByName(String permName);

    // 查询所有权限
    List<Permission> selectAll();

    // 查询某个Role的所有权限
    List<Permission> selectByRoleId(int roleId);

    // 新增权限
    int insert(Permission permission);

    // 删除权限
    int deleteById(int permId);

}
