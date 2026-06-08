package com.nuist.bookms.dao;

import com.nuist.bookms.entity.Book;

import java.sql.Connection;
import java.util.List;

public interface BookDAO {
    // 新增图书
    int insert(Book book);

    // 更新图书信息
    int update(Book book);

    // 删除图书（逻辑删除）
    int deleteById(int bookId);

    // 根据ID查询完整图书信息
    Book selectById(int bookId);

    // 分页查询图书(支持按书名、作者、分类ID、出版商、状态等多条件模糊/精确查询) SQL语句需要动态拼接
     List<Book> selectByPage(int pageNum, int pageSize, Book searchCondition);

    // 统计满足条件（书名、作者、分类ID、出版商、状态）的图书总数
    int selectCount(Book searchCondition);

    // 乐观锁扣减/归还库存
    // delta 是书的库存增量，正数表示借阅，负数表示归还
    int updateAvailableStock(int bookId, int delta, int version, Connection connection);

    // 悲观锁方式更新库存
    int updateAvailableStockWithPessimisticLock(int bookId, int delta);

    // 查询借阅册数最多的前N本书
    List<Book> selectTopBorrowed(int limit);

    // 上下架图书(Status)
    int updateStatus(int bookId, int status);
}
