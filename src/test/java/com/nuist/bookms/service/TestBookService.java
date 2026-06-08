package com.nuist.bookms.service;

import com.nuist.bookms.entity.Book;
import com.nuist.bookms.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestBookService {
    private BookService bookService = new BookServiceImpl();

    @Test
    public void testAddBook() {
        Book book = Book.builder()
                .title("《算法导论》")
                .author("<NAME>")
                .isbn("978-7-302-45709-5")
                .categoryId(2)
                .publisher("机械工业出版社")
                .publishDate(new java.sql.Date(System.currentTimeMillis()))
                .availableStock(100)
                .totalStock(100)
                .status(1)
                .build();
        boolean result = bookService.addBook(book);
        System.out.println(result);
    }

    @Test
    public void testUpdateBook() {
        Book book = Book.builder()
                .bookId(11)
                .title("《算法导论》")
                .author("<NAME>")
                .isbn("978-7-302-45709-5")
                .categoryId(2)
                .publisher("机械工公出版社")
                .publishDate(new java.sql.Date(System.currentTimeMillis()))
                .availableStock(100)
                .totalStock(100)
                .status(1)
                .build();
        boolean result = bookService.updateBook(book);
        System.out.println(result);
    }

    @Test
    public void testDeleteBookById() {
        boolean result = bookService.deleteBookById(11);
        System.out.println(result);
    }

    @Test
    public void testGetBookById() {
        Book book = bookService.getBookById(1);
        System.out.println(book);
    }

    @Test
    public void testGetBooksByPage() {
        Book searchCondition = Book.builder()
                .publisher("工业")
                .build();
        List<Book> books = bookService.getBooksByPage(1, 5, searchCondition);
        System.out.println(books);
    }

    @Test
    public void testGetTopBorrowedBooks() {
        List<Book> books = bookService.getTopBorrowedBooks(5);
        System.out.println(books);
    }

}
