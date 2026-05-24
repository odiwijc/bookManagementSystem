package com.nuist.bookms.dao.impl;

import com.nuist.bookms.dao.UserRoleDAO;
import com.nuist.bookms.entity.Role;
import com.nuist.bookms.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAOImpl implements UserRoleDAO {
    @Override
    public int insert(int userId, int roleId) {
        int result = 0;
        String sql = "insert into user_role(user_id,role_id) values(?,?)";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, roleId);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int delete(int userId, int roleId) {
        int result = 0;
        String sql = "delete from user_role where user_id = ? and role_id = ?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, roleId);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int deleteByUserId(int userId) {
        int result = 0;
        String sql = "delete from user_role where user_id = ?";

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
    public List<Integer> selectRoleIdsByUserId(int userId) {
        List<Integer> list = new ArrayList<>();
        String sql = "select role_id from user_role where user_id = ?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);

            // 执行sql语句 获取查询结果集
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Integer i = rs.getInt(1);
                list.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Role> selectRolesByUserId(int userId) {
        List<Role> list = new ArrayList<>();
        String sql = "select role_id, role_name from role where role_id in (select role_id from user_role where user_id = ?)";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);

            // 执行sql语句 获取查询结果集
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Role role = Role.builder()
                        .roleId(rs.getInt("role_id"))
                        .roleName(rs.getString("role_name"))
                        .build();
                list.add(role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 判断用户是否拥有某个角色
    @Override
    public boolean exists(int userId, int roleId) {
        int result = 0;
        String sql = "select count(*) from user_role where user_id = ? and role_id = ?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, roleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                result = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result > 0;
    }
}
