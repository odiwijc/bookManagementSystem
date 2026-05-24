package com.nuist.bookms.dao;

import com.nuist.bookms.entity.Role;

import java.util.List;

public interface RoleDAO {
    // 根据角色ID查询
    Role selectById(int roleId);

    // 根据角色名查询
    Role selectByName(String roleName);

    // 查询所有角色
    List<Role> selectAll();

    // 新增角色
    int insert(Role role);

    // 更新角色名
    int update(Role role);

    // 删除角色(需要确保没有关联的用户)
    int deleteById(int roleId);
}
