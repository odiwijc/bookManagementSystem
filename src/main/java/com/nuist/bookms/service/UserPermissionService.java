package com.nuist.bookms.service;

import com.nuist.bookms.entity.Permission;

import java.util.List;

public interface UserPermissionService {
    // 给出用户拥有的所有角色的所有权限列表
    List<Permission> selectPermissionsByUserId(int userId);

    // 判断用户是否有某个权限
    boolean hasPermission(int userId, String permissionName);
}
