package com.nuist.bookms.controller;

import com.nuist.bookms.util.PrintUtil;
import com.nuist.bookms.view.LoginView;
import com.nuist.bookms.view.MainView;
import com.nuist.bookms.view.RegisterView;

public class MainController {
    public static void mainController(String choice) {
        switch (choice) {
            case "1":
                LoginView.loginView();
                break;
            case "2":
                RegisterView.registerView();
                break;
            case "3":
                PrintUtil.print("bye~");
                System.exit(0);
                break;
            default:
                PrintUtil.print("输入错误，请重新输入");
                MainView.mainView();
        }
    }

    public static void mainController(int userId, String choice) {
        switch (choice) {
            case "1":
                // todo 查看用户信息
                break;
            case "2":
                // todo 修改密码
                break;
            case "3":
                // 退出登录
                PrintUtil.print("退出登录");
                MainView.mainView();
                break;
            case "4":
                // 退出系统
                PrintUtil.print("bye~");
                System.exit(0);
                break;
            default:
                PrintUtil.print("输入错误，请重新输入");
                MainView.mainView();
        }
    }
}
