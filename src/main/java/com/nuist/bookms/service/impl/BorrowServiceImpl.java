package com.nuist.bookms.service.impl;

import com.nuist.bookms.dao.BookDAO;
import com.nuist.bookms.dao.BorrowRecordDAO;
import com.nuist.bookms.dao.impl.BookDAOImpl;
import com.nuist.bookms.dao.impl.BorrowRecordDAOImpl;
import com.nuist.bookms.entity.Book;
import com.nuist.bookms.entity.BorrowRecord;
import com.nuist.bookms.entity.User;
import com.nuist.bookms.service.BookService;
import com.nuist.bookms.service.BorrowService;
import com.nuist.bookms.service.UserService;
import com.nuist.bookms.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BorrowServiceImpl implements BorrowService {
    public static final int BORROW_LIMIT = 5; // 每个人的借书上限

    private BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAOImpl();
    private BookDAO bookDAO = new BookDAOImpl();


    private UserService userService = new UserServiceImpl();
    private BookService bookService = new BookServiceImpl();

    @Override
    public boolean borrowBook(int bookId, int userId) {

        // 用户是否存在且未被禁用(status==0)
        User user = userService.selectByUserId(userId);
        if (user == null
                || user.getStatus() == 0) {
            System.out.println("用户不存在或已被禁用");
            return false;
        }
        // 检查书存在 且未下架 且有库存
        if (bookService.getBookById(bookId).getStatus() != 1
                || bookService.getBookById(bookId).getAvailableStock() <= 0
                || bookService.getBookById(bookId).getTotalStock() <= 0
                || bookService.getBookById(bookId) == null) {
            System.out.println("书不存在或下架或没有库存");
            return false;
        }
        // 检测是否超过借书上限，超了不给借
        if (borrowRecordDAO.countBorrowingByUserId(userId) >= BORROW_LIMIT) {
            System.out.println("用户借书数量已达上限");
            return false;
        }
        // 检测用户是否已经借过该书并且没还书
        // 未还状态
        BorrowRecord searchCondition1 = BorrowRecord.builder()
                .userId(userId)
                .bookId(bookId)
                .status(1)
                .build();
        // 逾期状态
        BorrowRecord searchCondition2 = BorrowRecord.builder()
                .userId(userId)
                .bookId(bookId)
                .status(3)
                .build();
        // 如果用户有这本书的未还或逾期的借阅记录，不给借
        if (borrowRecordDAO.selectAllCount(searchCondition1) > 0
                || borrowRecordDAO.selectAllCount(searchCondition2) > 0) {
            System.out.println("用户已借该书未还");
            return false;
        }

        try (Connection c = DBUtil.getConnection();) {

            c.setAutoCommit(false); // 开启事务

            // 判定可以借书后，插入借阅记录
            BorrowRecord record = BorrowRecord.builder()
                    .userId(userId)
                    .bookId(bookId)
                    .borrowDate(new java.sql.Date(System.currentTimeMillis()))
                    .dueDate(new java.sql.Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 默认借阅7天
                    .returnDate(null)
                    .renewCount(0)
                    .status(1)
                    .build();
            System.out.println("借书成功");
            borrowRecordDAO.insert(record, c);

            // 乐观锁扣减库存
            int version = bookService.getBookById(bookId).getVersion();
            int result = bookDAO.updateAvailableStock(bookId, -1, version, c);
            if (result == 0) {
                // 如果结果（受影响的行数）是0，说明并发冲突更新失败
                c.rollback();
                return false;
            }

            // 没有问题就提交
            c.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean returnBook(int bookId, int userId) {

        // 用户是否存在且未被禁用(status==0)
        User user = userService.selectByUserId(userId);
        if (user == null
                || user.getStatus() == 0) {
            System.out.println("用户不存在或已被禁用");
            return false;
        }
        // 检查书存在 且未下架 且有库存
        if (bookService.getBookById(bookId).getStatus() != 1
                || bookService.getBookById(bookId).getAvailableStock() <= 0
                || bookService.getBookById(bookId).getTotalStock() <= 0
                || bookService.getBookById(bookId) == null) {
            System.out.println("书不存在或下架或没有库存");
            return false;
        }
        // 检测用户是否已经借过该书并且没还书
        // 未还状态
        BorrowRecord searchCondition1 = BorrowRecord.builder()
                .userId(userId)
                .bookId(bookId)
                .status(1)
                .build();
        // 逾期状态
        BorrowRecord searchCondition2 = BorrowRecord.builder()
                .userId(userId)
                .bookId(bookId)
                .status(3)
                .build();
        // 如果用户没有这本书的未还或逾期的借阅记录，不给还
        if (borrowRecordDAO.selectAllCount(searchCondition1) <= 0
                && borrowRecordDAO.selectAllCount(searchCondition2) <= 0) {
            System.out.println("用户没有借这本书，不需要还书");
            return false;
        }
        // 获取借阅记录
        BorrowRecord record = borrowRecordDAO.selectByUserIdAndBookId(userId, bookId);


        try (Connection c = DBUtil.getConnection();) {
            c.setAutoCommit(false); // 开启事务

            // 更新借阅记录为已归还
            record.setStatus(2);
            record.setReturnDate(new java.sql.Date(System.currentTimeMillis()));
            borrowRecordDAO.update(record, c);

            // 增加图书库存（还是用乐观锁）
            Book book = bookDAO.selectById(record.getBookId());
            int result = bookDAO.updateAvailableStock(book.getBookId(), 1, book.getVersion(), c);
            if (result == 0) {
                // 如果结果（受影响的行数）是0，说明并发冲突更新失败
                c.rollback();
                System.out.println("由于并发冲突还书失败，请重试");
                return false;
            }
            c.commit();
            System.out.println("还书成功");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public boolean renewBook(int bookId, int userId) {

        // 用户是否存在且未被禁用(status==0)
        User user = userService.selectByUserId(userId);
        if (user == null
                || user.getStatus() == 0) {
            System.out.println("用户不存在或已被禁用");
            return false;
        }
        // 检查书存在 且未下架 且有库存
        if (bookService.getBookById(bookId).getStatus() != 1
                || bookService.getBookById(bookId).getAvailableStock() <= 0
                || bookService.getBookById(bookId).getTotalStock() <= 0
                || bookService.getBookById(bookId) == null) {
            System.out.println("书不存在或下架或没有库存");
            return false;
        }
        // 检测用户是否已经借过该书并且没还书
        // 未还状态
        BorrowRecord searchCondition1 = BorrowRecord.builder()
                .userId(userId)
                .bookId(bookId)
                .status(1)
                .build();
        // 逾期状态
        BorrowRecord searchCondition2 = BorrowRecord.builder()
                .userId(userId)
                .bookId(bookId)
                .status(3)
                .build();
        // 如果用户没有这本书的未还或逾期的借阅记录，不给续借
        if (borrowRecordDAO.selectAllCount(searchCondition1) <= 0
                && borrowRecordDAO.selectAllCount(searchCondition2) <= 0) {
            System.out.println("用户没有借这本书，不需要续借");
            return false;
        }

        // 获取借阅记录
        BorrowRecord record = borrowRecordDAO.selectByUserIdAndBookId(userId, bookId);

        // 如果续借次数已经达到上限，不允许续借
        if (record.getRenewCount() >= BORROW_LIMIT) {
            System.out.println("续借次数已达上限，不许续借");
            return false;
        }

        try (Connection c = DBUtil.getConnection()) {
            c.setAutoCommit(false);
            // 更新借阅记录为已续借
            record.setDueDate(new java.sql.Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000));
            record.setRenewCount(record.getRenewCount() + 1);
            borrowRecordDAO.update(record, c);
            c.commit();
            System.out.println("续借成功");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("续借失败");
        return false;
    }

    @Override
    public List<BorrowRecord> selectBorrowRecordByPageByUserId(int pageNum, int pageSize, int userId) {
        List<BorrowRecord> records = borrowRecordDAO.selectByPageByUserId(userId, pageNum, pageSize);
        return records;
    }

    @Override
    public int selectBorrowRecordCountByUserId(int userId) {
        int count = borrowRecordDAO.selectCountByUserId(userId);
        return count;
    }
}
