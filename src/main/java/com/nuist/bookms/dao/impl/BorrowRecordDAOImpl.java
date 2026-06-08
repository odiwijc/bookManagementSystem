package com.nuist.bookms.dao.impl;

import com.nuist.bookms.dao.BorrowRecordDAO;
import com.nuist.bookms.entity.BorrowRecord;
import com.nuist.bookms.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowRecordDAOImpl implements BorrowRecordDAO {
    @Override
    public int insert(BorrowRecord record, Connection connection) {
        int result = 0;
        String sql = "insert into borrow_record(user_id,book_id,borrow_date,due_date,return_date,renew_count,status) values(?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, record.getUserId());
            ps.setInt(2, record.getBookId());
            ps.setDate(3, record.getBorrowDate());
            ps.setDate(4, record.getDueDate());
            ps.setDate(5, record.getReturnDate());
            ps.setInt(6, record.getRenewCount());
            ps.setInt(7, record.getStatus());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(BorrowRecord record, Connection connection) {
        int result = 0;
        String sql = "update borrow_record set user_id=?,book_id=?,borrow_date=?,due_date=?,return_date=?,renew_count=?,status=? where record_id =?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, record.getUserId());
            ps.setInt(2, record.getBookId());
            ps.setDate(3, record.getBorrowDate());
            ps.setDate(4, record.getDueDate());
            ps.setDate(5, record.getReturnDate());
            ps.setInt(6, record.getRenewCount());
            ps.setInt(7, record.getStatus());
            ps.setInt(8, record.getRecordId());
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public BorrowRecord selectById(Integer recordId) {
        BorrowRecord record = null;
        String sql = "select * from borrow_record where record_id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                record = new BorrowRecord(
                        rs.getInt("record_id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getDate("borrow_date"),
                        rs.getDate("due_date"),
                        rs.getDate("return_date"),
                        rs.getInt("renew_count"),
                        rs.getInt("status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return record;
    }

    @Override
    public List<BorrowRecord> selectByPageByUserId(Integer userId, Integer pageNum, Integer pageSize) {
        List<BorrowRecord> records = new ArrayList<>();

        String sql = "select * from borrow_record where user_id = ? limit ?,?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, (pageNum - 1) * pageSize);
            ps.setInt(3, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                records.add(new BorrowRecord(
                        rs.getInt("record_id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getDate("borrow_date"),
                        rs.getDate("due_date"),
                        rs.getDate("return_date"),
                        rs.getInt("renew_count"),
                        rs.getInt("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }

    @Override
    public int selectCountByUserId(Integer userId) {
        int count = 0;
        String sql = "select count(*) from borrow_record where user_id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return count;
    }

    @Override
    public List<BorrowRecord> selectBorrowingByUserId(Integer userId) {
        List<BorrowRecord> records = new ArrayList<>();
        String sql = "select * from borrow_record where user_id = ? and status in (1,3)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BorrowRecord record = BorrowRecord.builder()
                        .recordId(rs.getInt("record_id"))
                        .userId(rs.getInt("user_id"))
                        .bookId(rs.getInt("book_id"))
                        .borrowDate(rs.getDate("borrow_date"))
                        .dueDate(rs.getDate("due_date"))
                        .returnDate(rs.getDate("return_date"))
                        .renewCount(rs.getInt("renew_count"))
                        .status(rs.getInt("status"))
                        .build();
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public int countBorrowingByUserId(Integer userId) {
        int count = 0;
        String sql = "select count(*) from borrow_record where user_id = ? and status in (1,3)";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    // 查找逾期的记录 找到后应该设置状态status为2
    @Override
    public List<BorrowRecord> selectOverdueRecords() {
        List<BorrowRecord> records = new ArrayList<>();
        String sql = "select * from borrow_record where status = 1 and due_date > now()";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BorrowRecord record = BorrowRecord.builder()
                        .recordId(rs.getInt("record_id"))
                        .userId(rs.getInt("user_id"))
                        .bookId(rs.getInt("book_id"))
                        .borrowDate(rs.getDate("borrow_date"))
                        .dueDate(rs.getDate("due_date"))
                        .returnDate(rs.getDate("return_date"))
                        .renewCount(rs.getInt("renew_count"))
                        .status(rs.getInt("status"))
                        .build();
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }


    @Override
    public int updateRecord(Integer recordId, Integer status, Date dueDate, Integer renewCount) {
        int result = 0;
        String sql = "update borrow_record set status = ?, due_date = ?, renew_count = ? where record_id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setDate(2, dueDate);
            ps.setInt(3, renewCount);
            ps.setInt(4, recordId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<BorrowRecord> selectAllByPage(Integer pageNum, Integer pageSize, BorrowRecord searchCondition) {
        List<BorrowRecord> records = new ArrayList<>();
        String sql = "select * from borrow_record where 1=1";
        // 拼接sql语句
        if (searchCondition != null) {
            if (searchCondition.getUserId() != null) {
                sql += " and user_id = " + searchCondition.getUserId();
            }
            if (searchCondition.getStatus() != null) {
                sql += " and status = " + searchCondition.getStatus();
            }
        }
        sql += " limit " + (pageNum - 1) * pageSize + ", " + pageSize;

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BorrowRecord record = BorrowRecord.builder()
                        .recordId(rs.getInt("record_id"))
                        .userId(rs.getInt("user_id"))
                        .bookId(rs.getInt("book_id"))
                        .borrowDate(rs.getDate("borrow_date"))
                        .dueDate(rs.getDate("due_date"))
                        .returnDate(rs.getDate("return_date"))
                        .renewCount(rs.getInt("renew_count"))
                        .status(rs.getInt("status"))
                        .build();
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public int selectAllCount(BorrowRecord searchCondition) {
        int count = 0;
        String sql = "select count(*) from borrow_record where 1=1";
        // 拼接sql语句
        if (searchCondition != null) {
            if (searchCondition.getUserId() != null) {
                sql += " and user_id = " + searchCondition.getUserId();
            }
            if (searchCondition.getBookId() != null) {
                sql += " and book_id = " + searchCondition.getBookId();
            }
            if (searchCondition.getStatus() != null) {
                sql += " and status = " + searchCondition.getStatus();
            }
        }
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getBorrowCount(int bookId) {
        int count = 0;
        String sql = "select count(*) from borrow_record where book_id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public BorrowRecord selectByUserIdAndBookId(Integer userId, Integer bookId) {
        BorrowRecord record = null;
        String sql = "select * from borrow_record where user_id = ? and book_id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                record = BorrowRecord.builder()
                        .recordId(rs.getInt("record_id"))
                        .userId(rs.getInt("user_id"))
                        .bookId(rs.getInt("book_id"))
                        .borrowDate(rs.getDate("borrow_date"))
                        .dueDate(rs.getDate("due_date"))
                        .returnDate(rs.getDate("return_date"))
                        .renewCount(rs.getInt("renew_count"))
                        .status(rs.getInt("status"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;
    }
}
