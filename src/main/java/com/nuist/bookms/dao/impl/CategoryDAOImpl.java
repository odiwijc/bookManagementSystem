package com.nuist.bookms.dao.impl;

import com.nuist.bookms.dao.CategoryDAO;
import com.nuist.bookms.entity.Category;
import com.nuist.bookms.entity.Role;
import com.nuist.bookms.entity.User;
import com.nuist.bookms.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public int insert(Category category) {
        int result = 0;
        String sql = "insert into category(category_name,sort_order) values(?,?)";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setInt(2, category.getSortOrder());
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int update(Category category) {
        int result = 0;
        String sql = "update category set category_name=?, sort_order=? where category_id=?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setInt(2, category.getSortOrder());
            ps.setInt(3, category.getCategoryId());
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int deleteById(int categoryId) {
        int result = 0;
        String sql = "delete from category where category_id = ?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Category selectById(int categoryId) {
        Category category = null;
        String sql = "select * from category where category_id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                category = Category.builder()
                        .categoryId(categoryId)
                        .categoryName(rs.getString("category_name"))
                        .sortOrder(rs.getInt("sort_order"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public List<Category> selectAll() {
        List<Category> list = new ArrayList<>();
        String sql = "select * from category";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            // 执行sql语句 获取查询结果集
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = Category.builder()
                        .categoryId(rs.getInt("category_id"))
                        .categoryName(rs.getString("category_name"))
                        .sortOrder(rs.getInt("sort_order"))
                        .build();
                list.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Category> selectByPage(int pageNum, int pageSize) {
        List<Category> list = new ArrayList<>();
        String sql = "select * from category limit ?,?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, (pageNum - 1) * pageSize);
            ps.setInt(2, pageSize);

            // 执行sql语句 获取查询结果集
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = Category.builder()
                        .categoryId(rs.getInt("category_id"))
                        .categoryName(rs.getString("category_name"))
                        .sortOrder(rs.getInt("sort_order"))
                        .build();
                list.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public int selectCount() {
        int result = -1;
        String sql = "select count(*) from category";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            // 执行sql语句 获取查询结果集
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result=rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
