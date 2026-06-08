package com.nuist.bookms.service;

import com.nuist.bookms.entity.BorrowRecord;

import java.util.List;

public interface BorrowService {
    // 借书
    boolean borrowBook(int bookId, int userId);

    // 还书
    boolean returnBook(int bookId, int userId);

    // 续借
    boolean renewBook(int bookId, int userId);

    // 查询个人借阅记录
    List<BorrowRecord> selectBorrowRecordByPageByUserId(int pageNum, int pageSize, int userId);

    // 查询个人借阅记录数
    int selectBorrowRecordCountByUserId(int userId);
}
