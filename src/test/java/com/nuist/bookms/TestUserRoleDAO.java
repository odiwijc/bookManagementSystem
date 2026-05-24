package com.nuist.bookms;

import com.nuist.bookms.dao.UserRoleDAO;
import com.nuist.bookms.dao.impl.UserRoleDAOImpl;
import com.nuist.bookms.entity.Role;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestUserRoleDAO {
    private UserRoleDAO userRoleDAO = new UserRoleDAOImpl();

    @Test
    public void testInsert() {
        int result = userRoleDAO.insert(13, 1);
        int result1 = userRoleDAO.insert(13, 2);
        System.out.println(result);

    }

    @Test
    public void testDelete() {
        int result = userRoleDAO.delete(13, 1);
        System.out.println(result);
    }

    @Test
    public void testDeleteByUserId() {
        int result = userRoleDAO.deleteByUserId(13);
        System.out.println(result);
    }

    @Test
    public void testSelectRoleIdsByUserId() {
        List<Integer> result = userRoleDAO.selectRoleIdsByUserId(13);
        System.out.println(result);
    }

    @Test
    public void testSelectRolesByUserId(){
        List<Role> roles = userRoleDAO.selectRolesByUserId(13);
        System.out.println(roles);
    }

    @Test
    public void testExists(){
        boolean result = userRoleDAO.exists(13,3);
        System.out.println(result);
    }
}
