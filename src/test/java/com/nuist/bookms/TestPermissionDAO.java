package com.nuist.bookms;

import com.nuist.bookms.dao.PermissionDAO;
import com.nuist.bookms.dao.impl.PermissionDAOImpl;
import com.nuist.bookms.entity.Permission;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestPermissionDAO {
    private PermissionDAO permissionDAO = new PermissionDAOImpl();

    @Test
    public void testSelectById() {
        Permission permission = permissionDAO.selectById(1);
        System.out.println(permission);
    }

    @Test
    public void testSelectAll() {
        List<Permission> list = permissionDAO.selectAll();
        System.out.println(list);
    }

    @Test
    public void testSelectByRoleId() {
        List<Permission> list = permissionDAO.selectByRoleId(1);
        System.out.println(list);

    }

    @Test
    public void testInsert(){
        Permission p = Permission.builder()
                .permissionName("ha")
                .description("可以哈气")
                .build();
        int result = permissionDAO.insert(p);
        System.out.println(result);
    }

    @Test
    public void testDeleteById(){
        int result = permissionDAO.deleteById(14);
        System.out.println(result);
    }
}
