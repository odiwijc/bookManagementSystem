package com.nuist.bookms;

import com.nuist.bookms.dao.UserDAO;
import com.nuist.bookms.dao.impl.UserDAOImpl;
import com.nuist.bookms.entity.User;
import com.nuist.bookms.util.DBUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestUserDAO {
    private UserDAO userDAO = new UserDAOImpl();

    @Test
    public void testConnection() {
        // 测试数据库连接
        Connection c = null;
        try {
            c = DBUtil.getConnection();
            System.out.println(c);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testUserInsert() {
        User user = User.builder().username("habixia")
                .password("8300")
                .realName("哈比下")
                .phone("8300000")
                .email("hajimi@hjm.com")
                .status(1)
                .build();
        int result;
        result = userDAO.insert(user);
        System.out.println(result);

    }

    @Test
    public void testSelectByUsername() {
        User user = userDAO.selectByUsername("habixia");
        System.out.println(user);
    }

    @Test
    public void testSelectByUserId() {
        User user = userDAO.selectByUserId(1);
        System.out.println(user);
    }

    @Test
    public void testSelectAll() {
        List<User> users = userDAO.selectAll();
        System.out.println(users);
    }

    @Test
    public void testSelectByPage() {
        List<User> users = userDAO.selectByPage(2, 2);
        System.out.println(users);
    }

    @Test
    public void testUpdate() {
        User hajimi = userDAO.selectByUsername("hajimi");
        hajimi.setRealName("叮咚机");
        int result = userDAO.update(hajimi);
        System.out.println(result);

    }

    @Test
    public void testDeleteById(){
        int result = userDAO.deleteById(13);
        System.out.println(result);
    }

    @Test
    public void testUpdateStatus(){
        int result = userDAO.updateStatus(13,1);
        System.out.println(result);
    }
}
