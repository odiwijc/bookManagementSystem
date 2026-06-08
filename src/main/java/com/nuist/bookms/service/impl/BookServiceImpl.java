package com.nuist.bookms.service.impl;

import com.nuist.bookms.dao.BookDAO;
import com.nuist.bookms.dao.impl.BookDAOImpl;
import com.nuist.bookms.entity.Book;
import com.nuist.bookms.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDAO bookDAO = new BookDAOImpl();

    @Override
    public boolean addBook(Book book) {
        if (bookDAO.insert(book) > 0) {
            return true;
        } else return false;
    }

    @Override
    public boolean updateBook(Book book) {
        if (bookDAO.update(book) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteBookById(int id) {
        if (bookDAO.deleteById(id) > 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Book getBookById(int id) {
        return bookDAO.selectById(id);
    }

    @Override
    public List<Book> getBooksByPage(int pageNum, int pageSize, Book searchCondition) {
        return bookDAO.selectByPage(pageNum, pageSize, searchCondition);
    }

    @Override
    public List<Book> getTopBorrowedBooks(int limit) {
        return bookDAO.selectTopBorrowed(limit);
    }
}
