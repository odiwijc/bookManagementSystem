package com.nuist.bookms.view;

import com.nuist.bookms.controller.UserController;
import com.nuist.bookms.util.PrintUtil;

public class LoginView {
    public static void loginView() {
        PrintUtil.print("请输入用户名");
        String inputUsername = PrintUtil.sc.nextLine();
        PrintUtil.print("请输入密码");
        String inputPassword = PrintUtil.sc.nextLine();
        PrintUtil.print("正在登陆。。。");
        // 调用controller进行处理
        UserController.login(inputUsername,inputPassword);
    }
}
