package com.nuist.bookms;

import com.nuist.bookms.dao.BorrowRecordDAO;
import com.nuist.bookms.dao.impl.BorrowRecordDAOImpl;
import com.nuist.bookms.entity.BorrowRecord;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

public class TestBorrowRecord {
    private BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAOImpl();

    @Test
    public void testInsert() {
        BorrowRecord record = BorrowRecord.builder()
                .userId(1)
                .bookId(1)
                .borrowDate(new Date(System.currentTimeMillis()))
                .dueDate(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .returnDate( null)
                .renewCount(0)
                .status(1)
                .build();
        System.out.println(record);
        int result = borrowRecordDAO.insert(record);
        System.out.println(result);
    }

    @Test
    public void testUpdate() {
        BorrowRecord record = BorrowRecord.builder()
                .recordId(10)
                .userId(1)
                .bookId(1)
                .borrowDate(new Date(System.currentTimeMillis()))
                .dueDate(new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000))
                .returnDate(new Date(System.currentTimeMillis()))
                .renewCount(1)
                .status(1)
                .build();
        int result = borrowRecordDAO.update(record);
        System.out.println(result);
    }

    @Test
    public void testSelectById() {
        BorrowRecord record = borrowRecordDAO.selectById(10);
        System.out.println(record);
    }

    @Test
    public void testSelectByUserId() {
        List<BorrowRecord> records = borrowRecordDAO.selectByUserId(4, 1, 5);
        System.out.println(records);
    }

    @Test
    public void testSelectCountByUserId() {
        int count = borrowRecordDAO.selectCountByUserId(4);
        System.out.println(count);
    }

    @Test
    public void testSelectBorrowingByUserId() {
        List<BorrowRecord> records = borrowRecordDAO.selectBorrowingByUserId(1);
        System.out.println(records);
    }

    @Test
    public void testCountBorrowingByUserId() {
        int count = borrowRecordDAO.countBorrowingByUserId(1);
        System.out.println(count);
    }

    @Test
    public void testSelectOverdueRecords() {
        List<BorrowRecord> records = borrowRecordDAO.selectOverdueRecords();
        System.out.println(records);
    }

    @Test
    public void testUpdateRecord() {
        int result = borrowRecordDAO.updateRecord(10, 2, new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000), 1);
        System.out.println(result);
    }

    @Test
    public void testSelectAllByPage() {
        BorrowRecord searchCondition = BorrowRecord.builder()
                .userId(1)
                .build();

        List<BorrowRecord> records = borrowRecordDAO.selectAllByPage(1, 5, searchCondition);
        System.out.println(records);
    }

    @Test
    public void testSelectAllCount() {
        BorrowRecord searchCondition = BorrowRecord.builder()
                .userId(1)
                .build();
        int count = borrowRecordDAO.selectAllCount(searchCondition);
        System.out.println(count);
    }

    @Test
    public void testGetBorrowCount() {
        int result = borrowRecordDAO.getBorrowCount(1);
        System.out.println(result);
    }


}
