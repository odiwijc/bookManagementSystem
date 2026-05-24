package com.nuist.bookms;

import com.nuist.bookms.dao.RolePermissionDAO;
import com.nuist.bookms.dao.impl.RolePermissionDAOImpl;
import com.nuist.bookms.entity.Permission;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestRolePermissionDAO {
    private RolePermissionDAO rolePermissionDAO = new RolePermissionDAOImpl();

    @Test
    public void testInsert() {
        int result = rolePermissionDAO.insert(67, 67);
        System.out.println(result);
    }

    @Test
    public void testDelete() {
        int result = rolePermissionDAO.delete(67, 67);
        System.out.println(result);
    }

    @Test
    public void testDeleteByRoleId() {
        int result = rolePermissionDAO.deleteByRoleId(67);
        System.out.println(result);
    }

    @Test
    public void testSelectPermIdsByRoleId() {
        List<Integer> list = rolePermissionDAO.selectPermIdByRoleId(2);
        System.out.println(list);
    }

    @Test
    public void testSelectPermissionsByRoleId() {
        List<Permission> list = rolePermissionDAO.selectPermissionsByRoleId(2);
        System.out.println(list);
    }

    @Test
    public void testExists(){
        boolean result = rolePermissionDAO.exists(2,14);
        System.out.println(result);
    }
}
