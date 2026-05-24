package com.nuist.bookms;

import com.nuist.bookms.dao.RoleDAO;
import com.nuist.bookms.dao.impl.RoleDAOImpl;
import com.nuist.bookms.entity.Role;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestRoleDAO {
    private RoleDAO roleDAO = new RoleDAOImpl();

    @Test
    public void testSelectById() {
        Role r = roleDAO.selectById(1);
        System.out.println(r);
    }

    @Test
    public void testSelectByName() {
        Role r = roleDAO.selectByName("管理员");
        System.out.println(r);
    }

    @Test
    public void testSelectAll() {
        List<Role> list = roleDAO.selectAll();
        System.out.println(list);
    }

    @Test
    public void testInsert() {
        Role r = new Role(null, "超级管理员");
        int result = roleDAO.insert(r);
        System.out.println(result);
    }

    @Test
    public void testUpdate(){
        Role r = new Role(1,"普通用户");
        int result = roleDAO.update(r);
        System.out.println(result);
    }

    @Test
    public void testDeleteById(){
        int id = 3;
        int result = roleDAO.deleteById(id);
        System.out.println(result);
    }
}
