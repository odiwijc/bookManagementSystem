package com.nuist.bookms.dao.impl;

import com.nuist.bookms.dao.BookDAO;
import com.nuist.bookms.entity.Book;
import com.nuist.bookms.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    @Override
    public int insert(Book book) {
        int result = 0;
        String sql = "insert into Book(title,author,isbn,category_id,publisher,publish_date,total_stock,available_stock,cover_url,status) " +
                "values(?,?,?,?,?,?,?,?,?,?)";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1,book.getTitle());
            ps.setString(2,book.getAuthor());
            ps.setString(3,book.getIsbn());
            ps.setInt(4,book.getCategoryId());
            ps.setString(5,book.getPublisher());
            ps.setDate(6,book.getPublishDate());
            ps.setInt(7,book.getTotalStock());
            ps.setInt(8,book.getAvailableStock());
            ps.setString(9, book.getCoverUrl());
            ps.setInt(10,book.getStatus());
//            ps.setInt(11,book.getVersion());

            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int update(Book book) {
        int result = 0;
        String sql = "update book set title=?,author=?,isbn=?,category_id=?,publisher=?," +
                "publish_date=?,total_stock=?,available_stock=?,cover_url=?,status=? where book_id=?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1,book.getTitle());
            ps.setString(2,book.getAuthor());
            ps.setString(3,book.getIsbn());
            ps.setInt(4,book.getCategoryId());
            ps.setString(5,book.getPublisher());
            ps.setDate(6,book.getPublishDate());
            ps.setInt(7,book.getTotalStock());
            ps.setInt(8,book.getAvailableStock());
            ps.setString(9, book.getCoverUrl());
            ps.setInt(10,book.getStatus());
            ps.setInt(11,book.getBookId());
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int deleteById(int bookId) {
        int result = 0;
        String sql = "update book set status=0 where book_id = ?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Book selectById(int bookId) {
        return null;
    }

    @Override
    public List<Book> selectByPage(int pageNum, int pageSize, Book searchCondition) {
        return List.of();
    }

    @Override
    public int selectCount(Book searchCondition) {
        return 0;
    }

    @Override
    public int updateAvailableStock(int bookId, int delta, int version) {
        return 0;
    }

    @Override
    public int updateAvailableStockWithPessimisticLock(int bookId, int delta) {
        return 0;
    }

    @Override
    public List<Book> selectTopBorrowed(int limit) {
        return List.of();
    }

    @Override
    public int updateStatus(int bookId, int status) {
        int result = 0;
        String sql = "update book set status=? where book_id = ?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setInt(2, bookId);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
