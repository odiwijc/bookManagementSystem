package com.nuist.bookms.view;

import com.nuist.bookms.controller.MainController;
import com.nuist.bookms.util.PrintUtil;

public class MainView {
    public static void mainView() {
        PrintUtil.print("欢迎使用图书管理系统");
        PrintUtil.print("当前状态：未登录");
        PrintUtil.print();
        PrintUtil.print("1. 用户登录");
        PrintUtil.print("2. 用户注册");
        PrintUtil.print("3. 退出系统");
        PrintUtil.print("请输入选项：");

        String choice = PrintUtil.sc.nextLine();
        MainController.mainController(choice);


    }

    public static void mainView(Integer userId) {
        PrintUtil.print("欢迎使用图书管理系统");
        PrintUtil.print("当前状态：已登录");
        PrintUtil.print("1. 用户信息");
        PrintUtil.print("2. 修改密码");
        PrintUtil.print("3. 退出登录");
        PrintUtil.print("4. 退出系统");

        String choice = PrintUtil.sc.nextLine();
        MainController.mainController(userId,choice);
    }
}
