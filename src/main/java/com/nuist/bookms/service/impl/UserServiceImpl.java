package com.nuist.bookms.service.impl;

import com.nuist.bookms.dao.UserDAO;
import com.nuist.bookms.dao.UserRoleDAO;
import com.nuist.bookms.dao.impl.UserDAOImpl;
import com.nuist.bookms.dao.impl.UserRoleDAOImpl;
import com.nuist.bookms.entity.User;
import com.nuist.bookms.service.UserService;
import com.nuist.bookms.util.MD5Util;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = new UserDAOImpl();
    private UserRoleDAO userRoleDAO = new UserRoleDAOImpl();

    @Override
    public boolean ifExist(String username) {
        return userDAO.selectByUsername(username) != null;
    }

    @Override
    public boolean ifExist(Integer userId) {
        return userDAO.selectByUserId(userId) != null;
    }

    @Override
    public int register(User user) {
        boolean isExist = ifExist(user.getUsername());
        // 已经有同名用户，不许注册
        if (isExist) {
            return -1;
        }
        // 输入密码时加密
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        // 返回的是用户插入数据库中的主键id
        return userDAO.insert(user);
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
    public List<User> selectByPage(int pageNum, int pageSize) {
        return userDAO.selectByPage(pageNum, pageSize);
    }

}
