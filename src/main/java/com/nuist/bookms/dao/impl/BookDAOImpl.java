package com.nuist.bookms.dao.impl;

import com.nuist.bookms.dao.BookDAO;
import com.nuist.bookms.entity.Book;
import com.nuist.bookms.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    @Override
    public int insert(Book book) {
        int result = 0;
        String sql = "insert into Book(title,author,isbn,category_id,publisher,publish_date,total_stock,available_stock,cover_url,status) " +
                "values(?,?,?,?,?,?,?,?,?,?)";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            ps.setInt(4, book.getCategoryId());
            ps.setString(5, book.getPublisher());
            ps.setDate(6, book.getPublishDate());
            ps.setInt(7, book.getTotalStock());
            ps.setInt(8, book.getAvailableStock());
            ps.setString(9, book.getCoverUrl());
            ps.setInt(10, book.getStatus());
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
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            ps.setInt(4, book.getCategoryId());
            ps.setString(5, book.getPublisher());
            ps.setDate(6, book.getPublishDate());
            ps.setInt(7, book.getTotalStock());
            ps.setInt(8, book.getAvailableStock());
            ps.setString(9, book.getCoverUrl());
            ps.setInt(10, book.getStatus());
            ps.setInt(11, book.getBookId());
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
        Book book = null;
        String sql = "select * from book where book_id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                book = Book.builder()
                        .bookId(rs.getInt("book_id"))
                        .title(rs.getString("title"))
                        .author(rs.getString("author"))
                        .isbn(rs.getString("isbn"))
                        .categoryId(rs.getInt("category_id"))
                        .publisher(rs.getString("publisher"))
                        .publishDate(rs.getDate("publish_date"))
                        .totalStock(rs.getInt("total_stock"))
                        .coverUrl(rs.getString("cover_url"))
                        .status(rs.getInt("status"))
                        .version(rs.getInt("version"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public List<Book> selectByPage(int pageNum, int pageSize, Book searchCondition) {

        List<Book> list = new ArrayList<>();
        String sql = "select * from book where 1=1";

        // 动态拼接SQL语句
        if (searchCondition != null) {
            if (searchCondition.getTitle() != null && !searchCondition.getTitle().isEmpty()) {
                sql += " and title like '%" + searchCondition.getTitle() + "%'";
            }
            if (searchCondition.getAuthor() != null && !searchCondition.getAuthor().isEmpty()) {
                sql += " and author like '%" + searchCondition.getAuthor() + "%'";
            }
            if (searchCondition.getCategoryId() != null && searchCondition.getCategoryId() != 0) {
                sql += " and category_id = " + searchCondition.getCategoryId();
            }
            if (searchCondition.getPublisher() != null && !searchCondition.getPublisher().isEmpty()) {
                sql += " and publisher like '%" + searchCondition.getPublisher() + "%'";
            }
        }

        // 加上筛选条件后拼接分页SQL语句
        sql += " limit ?,?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, (pageNum - 1) * pageSize);
            ps.setInt(2, pageSize);

            // 执行sql语句 获取查询结果集
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = Book.builder()
                        .bookId(rs.getInt("book_id"))
                        .title(rs.getString("title"))
                        .author(rs.getString("author"))
                        .isbn(rs.getString("isbn"))
                        .categoryId(rs.getInt("category_id"))
                        .publisher(rs.getString("publisher"))
                        .publishDate(rs.getDate("publish_date"))
                        .totalStock(rs.getInt("total_stock"))
                        .availableStock(rs.getInt("available_stock"))
                        .coverUrl(rs.getString("cover_url"))
                        .status(rs.getInt("status"))
                        .version(rs.getInt("version"))
                        .build();
                list.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 统计符合条件的书的个数
    @Override
    public int selectCount(Book searchCondition) {
        int count = 0;
        String sql = "select count(*) from book where 1=1";
        if (searchCondition != null) {
            if (searchCondition.getTitle() != null && !searchCondition.getTitle().isEmpty()) {
                sql += " and title like '%" + searchCondition.getTitle() + "%'";
            }
            if (searchCondition.getAuthor() != null && !searchCondition.getAuthor().isEmpty()) {
                sql += " and author like '%" + searchCondition.getAuthor() + "%'";
            }
            if (searchCondition.getCategoryId() != null && searchCondition.getCategoryId() != 0) {
                sql += " and category_id = " + searchCondition.getCategoryId();
            }
            if (searchCondition.getPublisher() != null && !searchCondition.getPublisher().isEmpty()) {
                sql += " and publisher like '%" + searchCondition.getPublisher() + "%'";
            }
            if (searchCondition.getStatus() != null) {
                sql += " and status = " + searchCondition.getStatus();
            }
        }

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public int updateAvailableStock(int bookId, int delta, int version) {
        int result = 0;
        String sql = "update book set available_stock = available_stock + ?, version = version + 1 where book_id = ? and version = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, delta);
            ps.setInt(2, bookId);
            ps.setInt(3, version);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateAvailableStockWithPessimisticLock(int bookId, int delta) {
        int result = 0;
        String sql = "update book set available_stock = available_stock + ? where book_id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, delta);
            ps.setInt(2, bookId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Book> selectTopBorrowed(int limit) {
        List<Book> list = new ArrayList<>();
        String sql = "select t1.*, count(t2.book_id) as borrow_count " +
                "from book t1 " +
                "join borrow_record t2 on t1.book_id = t2.book_id " +
                "group by t1.book_id " +
                "order by borrow_count desc " +
                "limit ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = Book.builder()
                        .bookId(rs.getInt("book_id"))
                        .title(rs.getString("title"))
                        .author(rs.getString("author"))
                        .isbn(rs.getString("isbn"))
                        .categoryId(rs.getInt("category_id"))
                        .publisher(rs.getString("publisher"))
                        .publishDate(rs.getDate("publish_date"))
                        .totalStock(rs.getInt("total_stock"))
                        .availableStock(rs.getInt("available_stock"))
                        .coverUrl(rs.getString("cover_url"))
                        .status(rs.getInt("status"))
                        .version(rs.getInt("version"))
                        .build();
                list.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;


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
