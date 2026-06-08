package com.nuist.bookms.dao.impl;

import com.nuist.bookms.dao.UserDAO;
import com.nuist.bookms.entity.User;
import com.nuist.bookms.util.DBUtil;
import com.nuist.bookms.util.MD5Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public int insert(User user) {
        int result = 0;
        String sql = "insert into user(username, password, real_name, phone, email, register_time, status)" +
                "values(?,?,?,?,?,now(),?)";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, MD5Util.getMD5(user.getPassword()));
            ps.setString(3, user.getRealName());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getStatus());


            result = ps.executeUpdate();
            if (result > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 返回用户id，用于登录
        return user.getUserId();
    }

    // 根据用户名查询
    @Override
    public User selectByUsername(String username) {
        User user = null;
        String sql = "select * from user where username = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = User.builder()
                        .userId(rs.getInt("user_id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .realName(rs.getString("real_name"))
                        .phone(rs.getString("phone"))
                        .email(rs.getString("email"))
                        .registerTime(rs.getTime("register_time"))
                        .status(rs.getInt("status"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // 根据用户id查询
    @Override
    public User selectByUserId(Integer userId) {
        User user = null;
        String sql = "select * from user where user_id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = User.builder()
                        .userId(rs.getInt("user_id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .realName(rs.getString("real_name"))
                        .phone(rs.getString("phone"))
                        .email(rs.getString("email"))
                        .registerTime(rs.getTime("register_time"))
                        .status(rs.getInt("status"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> selectAll() {

        List<User> list = new ArrayList<>();
        String sql = "select * from user";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            // 执行sql语句 获取查询结果集
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                User user = User.builder()
                        .userId(rs.getInt("user_id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .realName(rs.getString("real_name"))
                        .phone(rs.getString("phone"))
                        .email(rs.getString("email"))
                        .registerTime(rs.getTime("register_time"))
                        .status(rs.getInt("status"))
                        .build();
                list.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }

    // 分页查询
    @Override
    public List<User> selectByPage(int pageNum, int pageSize) {
        List<User> list = new ArrayList<>();
        String sql = "select * from user limit ?,?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, (pageNum - 1) * pageSize);
            ps.setInt(2, pageSize);

            // 执行sql语句 获取查询结果集
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                User user = User.builder()
                        .userId(rs.getInt("user_id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .realName(rs.getString("real_name"))
                        .phone(rs.getString("phone"))
                        .email(rs.getString("email"))
                        .registerTime(rs.getTime("register_time"))
                        .status(rs.getInt("status"))
                        .build();
                list.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public int update(User user) {
        int result = 0;
        String sql = "update user set username=?, password=?, real_name=?,phone=?,email=?,status=? where user_id =?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, MD5Util.getMD5(user.getPassword()));
            ps.setString(3, user.getRealName());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getStatus());
            ps.setInt(7, user.getUserId());


            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // 逻辑删除 status设为0
    @Override
    public int deleteById(Integer userId) {
        int result = 0;
        String sql = "update user set status=0 where user_id =?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);

            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int updateStatus(int userId, int newStatus) {
        int result = 0;
        String sql = "update user set status=? where user_id =?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, newStatus);
            ps.setInt(2, userId);

            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int selectByUsernameAndPassword(String username, String password) {
        int result = 0;
        String sql = "select * from user where username=? and password=?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, MD5Util.getMD5(password));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
