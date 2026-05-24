package com.nuist.bookms.dao.impl;

import com.nuist.bookms.dao.RolePermissionDAO;
import com.nuist.bookms.entity.Permission;
import com.nuist.bookms.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolePermissionDAOImpl implements RolePermissionDAO {
    @Override
    public int insert(int roleId, int permId) {
        int result = 0;
        String sql = "insert into role_permission(role_id,permission_id) values(?,?)";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ps.setInt(2, permId);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int delete(int roleId, int permId) {
        int result = 0;
        String sql = "delete from role_permission where role_id = ? and permission_id = ?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ps.setInt(2, permId);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int deleteByRoleId(int roleId) {
        int result = 0;
        String sql = "delete from role_permission where role_id = ?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Integer> selectPermIdByRoleId(int roleId) {
        List<Integer> list = new ArrayList<>();
        String sql = "select permission_id from role_permission where role_id = ?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, roleId);

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
    public List<Permission> selectPermissionsByRoleId(int roleId) {
        List<Permission> list = new ArrayList<>();
        String sql = "select permission_id, permission_name, description from permission where permission_id in (select permission_id from role_permission where role_id = ?)";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, roleId);

            // 执行sql语句 获取查询结果集
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Permission permission = Permission.builder()
                        .permissionId(rs.getInt("permission_id"))
                        .permissionName(rs.getString("permission_name"))
                        .description(rs.getString("description"))
                        .build();
                list.add(permission);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean exists(int roleId, int permId) {
        int result = 0;
        String sql = "select count(*) from role_permission where role_id = ? and permission_id = ?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ps.setInt(2, permId);
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
