package com.nuist.bookms.service.impl;

import com.nuist.bookms.dao.UserDAO;
import com.nuist.bookms.dao.impl.UserDAOImpl;
import com.nuist.bookms.entity.User;
import com.nuist.bookms.service.UserService;
import com.nuist.bookms.util.MD5Util;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public boolean ifExist(String username) {
        return userDAO.selectByUsername(username) != null;
    }

    @Override
    public boolean register(User user) {
        boolean isExist = ifExist(user.getUsername());
        // 已经有同名用户，不许注册
        if (isExist) {
            return false;
        }
        // 输入密码时加密
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        return userDAO.insert(user) > 0;
    }

    @Override
    public boolean login(String username, String password) {
        // 对输入的密码加密
        password = MD5Util.getMD5(password);
        // 调用dao获取结果
        int result = userDAO.selectByUsernameAndPassword(username, password);
        return result != 0 ? true : false;
    }

    @Override
    public User selectByUserId(Integer userId) {
        return userDAO.selectByUserId(userId);
    }

    @Override
    public boolean updateUser(User user) {
        return userDAO.update(user) > 0;
    }


    @Override
    public boolean hasPermission(User user, String permission) {

        return false;
    }

    @Override
    public List<User> selectByPage(int pageNum, int pageSize) {
        return List.of();
    }


}
