package com.nuist.bookms.dao;

import com.nuist.bookms.entity.Permission;

import java.util.List;

public interface RolePermissionDAO {
    // 给予某个角色权限
    int insert(int roleId, int permId);

    // 删除某个角色的某个权限
    int delete(int roleId, int permId);

    // 删除角色所有权限
    int deleteByRoleId(int roleId);

    // 查询角色拥有的所有权限id
    List<Integer> selectPermIdByRoleId(int roleId);

    // 查询角色拥有的Permission对象列表
    List<Permission> selectPermissionsByRoleId(int roleId);

    // 判断角色是否有某个权限
    boolean exists(int roleId, int permId);

}
