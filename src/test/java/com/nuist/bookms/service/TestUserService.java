package com.nuist.bookms.service;

import com.nuist.bookms.entity.User;
import com.nuist.bookms.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

public class TestUserService {
    UserService userService = new UserServiceImpl();

    @Test
    public void testIsExist() {
        System.out.println(userService.ifExist("admin"));
    }

    @Test
    public void testRegister() {
        User user = new User();
        user.setUsername("admin1");
        user.setPassword("123456");
        user.setRealName("管理员1");
        user.setPhone("12345678901");
        user.setEmail("<EMAIL>");
        user.setStatus(1);
        System.out.println(userService.register(user));
    }

    @Test
    public void testLogin() {
        System.out.println(userService.login("admin1", "123456"));
    }

    @Test
    public void testSelectByUserId() {
        System.out.println(userService.selectByUserId(1));
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setUserId(16);
        user.setUsername("admin1");
        user.setPassword("123456");
        user.setRealName("管理员1");
        user.setPhone("11111111111");
        user.setEmail("<EMAIL>");
        user.setStatus(1);
        System.out.println(userService.updateUser(user));
    }


    @Test
    public void testSelectByPage() {
        System.out.println(userService.selectByPage(1, 10));
    }
}
