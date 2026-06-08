package com.nuist.bookms.service;

import com.nuist.bookms.entity.BorrowRecord;
import com.nuist.bookms.service.impl.BorrowServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestBorrowService {
    private BorrowService borrowService = new BorrowServiceImpl();

    @Test
    public void testBorrowBook() {
        boolean result = borrowService.borrowBook(5, 2);
        System.out.println(result);
    }

    @Test
    public void testReturnBook() {
        boolean result = borrowService.returnBook(5, 2);
        System.out.println(result);
    }

    @Test
    public void testRenewBook() {
        boolean result = borrowService.renewBook(5, 2);
        System.out.println(result);
    }

    @Test
    public void testSelectBorrowRecordByPageByUserId() {
        List<BorrowRecord> records = borrowService.selectBorrowRecordByPageByUserId(1, 10, 2);
        for (BorrowRecord record : records) {
            System.out.println(record);
        }
    }

    @Test void testSelectBorrowRecordCountByUserId() {
        int count = borrowService.selectBorrowRecordCountByUserId(2);
        System.out.println(count);
    }

}
