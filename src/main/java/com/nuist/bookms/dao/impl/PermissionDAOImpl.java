package com.nuist.bookms.dao.impl;

import com.nuist.bookms.dao.PermissionDAO;
import com.nuist.bookms.entity.Permission;
import com.nuist.bookms.entity.Role;
import com.nuist.bookms.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionDAOImpl implements PermissionDAO {
    @Override
    public Permission selectById(int permId) {
        Permission permission = null;
        String sql = "select * from permission where permission_id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, permId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                permission = Permission.builder()
                        .permissionId(rs.getInt("permission_id"))
                        .permissionName(rs.getString("permission_name"))
                        .description(rs.getString("description"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return permission;
    }

    @Override
    public List<Permission> selectAll() {
        List<Permission> list = new ArrayList<>();
        String sql = "select * from permission";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            // 执行sql语句 获取查询结果集
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Permission p = Permission.builder()
                        .permissionId(rs.getInt("permission_id"))
                        .permissionName(rs.getString("permission_name"))
                        .description(rs.getString("description"))
                        .build();
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 查询某个Role的所有权限
    @Override
    public List<Permission> selectByRoleId(int roleId) {
        List<Permission> list = new ArrayList<>();
        String sql = "select * from permission where permission_id in (select permission_id from role_permission where role_id = ?)";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,roleId);
            // 执行sql语句 获取查询结果集
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Permission p = Permission.builder()
                        .permissionId(rs.getInt("permission_id"))
                        .permissionName(rs.getString("permission_name"))
                        .description(rs.getString("description"))
                        .build();
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public int insert(Permission permission) {
        int result = 0;
        String sql = "insert into permission(permission_name,description) values(?,?)";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, permission.getPermissionName());
            ps.setString(2, permission.getDescription());
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int deleteById(int permId) {
        int result = 0;
        String sql = "delete from permission where permission_id = ?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,permId);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Permission selectByName(String permName) {
        Permission permission = null;
        String sql = "select * from permission where permission_name = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, permName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                permission = Permission.builder()
                        .permissionId(rs.getInt("permission_id"))
                        .permissionName(rs.getString("permission_name"))
                        .description(rs.getString("description"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permission;
    }
}
