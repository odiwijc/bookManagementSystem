package com.nuist.bookms.service.impl;

import com.nuist.bookms.dao.PermissionDAO;
import com.nuist.bookms.dao.RolePermissionDAO;
import com.nuist.bookms.dao.UserDAO;
import com.nuist.bookms.dao.UserRoleDAO;
import com.nuist.bookms.dao.impl.PermissionDAOImpl;
import com.nuist.bookms.dao.impl.RolePermissionDAOImpl;
import com.nuist.bookms.dao.impl.UserDAOImpl;
import com.nuist.bookms.dao.impl.UserRoleDAOImpl;
import com.nuist.bookms.entity.Permission;
import com.nuist.bookms.service.UserPermissionService;

import java.util.ArrayList;
import java.util.List;

public class UserPermissionServiceImpl implements UserPermissionService {
    private PermissionDAO permissionDAO = new PermissionDAOImpl();
    private RolePermissionDAO rolePermissionDAO = new RolePermissionDAOImpl();
    private UserDAO userDAO = new UserDAOImpl();
    private UserRoleDAO userRoleDAO = new UserRoleDAOImpl();

    @Override
    public List<Permission> selectPermissionsByUserId(int userId) {
        List<Permission> permissionList = new ArrayList<>();
        // 获取用户所有角色id的列表
        List<Integer> roleIds = userRoleDAO.selectRoleIdsByUserId(userId);
        if (roleIds != null && roleIds.size() > 0) {
            for (Integer roleId : roleIds) {
                List<Permission> permissions = rolePermissionDAO.selectPermissionsByRoleId(roleId);
                permissionList.addAll(permissions);
            }
        }
        return permissionList;
    }

    @Override
    public boolean hasPermission(int userId, String permissionName) {
        Permission permission = permissionDAO.selectByName(permissionName);
        return selectPermissionsByUserId(userId).contains(permission);
    }


}
