package com.nuist.bookms.view;

import com.nuist.bookms.controller.UserController;
import com.nuist.bookms.util.PrintUtil;

public class RegisterView {
    public static void registerView() {
        PrintUtil.print("请输入用户名");
        String inputUsername = PrintUtil.sc.nextLine();
        PrintUtil.print("请输入密码");
        String inputPassword = PrintUtil.sc.nextLine();
        PrintUtil.print("请再次输入密码");
        String inputPasswordAgain = PrintUtil.sc.nextLine();

        if (!inputPassword.equals(inputPasswordAgain)) {
            PrintUtil.print("两次输入的密码不一致，请重新输入");
            registerView();
        } else {
            // 调用controller进行处理
            UserController.register(inputUsername, inputPassword);
        }
    }
}
