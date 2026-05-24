package com.nuist.bookms.dao.impl;

import com.nuist.bookms.dao.RoleDAO;
import com.nuist.bookms.entity.Role;
import com.nuist.bookms.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {
    @Override
    public Role selectById(int roleId) {
        Role role = null;
        String sql = "select * from role where role_id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                role = Role.builder().
                        roleId(rs.getInt("role_id"))
                        .roleName(rs.getString("role_name"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role;
    }

    @Override
    public Role selectByName(String roleName) {
        Role role = null;
        String sql = "select * from role where role_name = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, roleName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                role = Role.builder().
                        roleId(rs.getInt("role_id"))
                        .roleName(rs.getString("role_name"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role;
    }

    @Override
    public List<Role> selectAll() {
        List<Role> list = new ArrayList<>();
        String sql = "select * from role";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
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

    @Override
    public int insert(Role role) {
        int result = 0;
        String sql = "insert into role(role_name) values(?)";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, role.getRoleName());
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int update(Role role) {
        int result = 0;
        String sql = "update role set role_name = ? where role_id = ?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, role.getRoleName());
            ps.setInt(2, role.getRoleId());
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int deleteById(int roleId) {
        int result = 0;
        String sql = "delete from role where role_id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
