package com.nuist.bookms.controller;

import com.nuist.bookms.entity.User;
import com.nuist.bookms.service.UserService;
import com.nuist.bookms.service.impl.UserServiceImpl;
import com.nuist.bookms.view.MainView;

public class UserController {
    public static UserService userService = new UserServiceImpl();

    public static void login(String username, String password) {
        if (userService.login(username, password)) {
            System.out.println("登录成功");
            MainView.mainView(userService.selectByUserId(1).getUserId());
        } else {
            System.out.println("登录失败");
            MainView.mainView();
        }
    }

    public static void register(String username, String password) {
        User user = User.builder()
                .username(username)
                .password(password)
                .status(1)
                .build();
        int userId = userService.register(user);
        if (userId >= 1){
            System.out.println("注册成功");
            MainView.mainView(userId);
        }else {
            System.out.println("注册失败");
            MainView.mainView();
        }
    }
}
