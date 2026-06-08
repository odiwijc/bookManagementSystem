package com.nuist.bookms.service;

import com.nuist.bookms.entity.Book;

import java.util.List;

public interface BookService {
    // 添加书籍
    public boolean addBook(Book book);

    // 更新书籍信息
    public boolean updateBook(Book book);

    // 删除书籍
    public boolean deleteBookById(int id);

    // 查询书籍
    public Book getBookById(int id);

    // 根据条件分页查询
    public List<Book> getBooksByPage(int pageNum, int pageSize, Book searchCondition);

    // 获取热门图书列表
    public List<Book> getTopBorrowedBooks(int limit);
}
