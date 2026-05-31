package com.nuist.bookms.dao;

import com.nuist.bookms.dao.impl.BookDAOImpl;
import com.nuist.bookms.entity.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestBookDAO {
    private BookDAO bookDAO = new BookDAOImpl();

    @Test
    public void testInsert(){
        Book book = Book.builder().title("论语")
                .isbn("123456789098765")
                .author("孔子")
                .categoryId(3)
                .totalStock(1)
                .availableStock(1)
                .status(1)
                .version(0)
                .build();
        int result = bookDAO.insert(book);
        System.out.println(result);
    }

    @Test
    public void testUpdate(){
        Book book = Book.builder().title("论语")
                .isbn("123456789098765")
                .author("孔龙")
                .categoryId(3)
                .totalStock(1)
                .availableStock(1)
                .status(1)
                .version(0)
                .bookId(10)
                .build();
        int result = bookDAO.update(book);
        System.out.println(result);
    }

    @Test
    public void testDelete(){
        int result = bookDAO.deleteById(10);
        System.out.println(result);
    }

    @Test
    public void testUpdateStatus(){
        int result = bookDAO.updateStatus(10,1);
        System.out.println(result);
    }

    @Test
    public void testSelectById(){
        Book book = bookDAO.selectById(10);
        System.out.println(book);
    }

    @Test
    public void testSelectByPage(){
        Book searchCondition = Book.builder()
                .author("尔")
                .status(1)
                .build();
        List<Book> list = bookDAO.selectByPage(1, 5, searchCondition);
        System.out.println(list);
    }

    @Test
    public void testSelectCount(){
        Book searchCondition = Book.builder()
                .author("尔")
                .status(1)
                .build();
        int count = bookDAO.selectCount(searchCondition);
        System.out.println(count);
    }

    @Test
    public void testUpdateAvailableStock(){
        int result = bookDAO.updateAvailableStock(10,1,0);
        System.out.println(result);
    }

    @Test
    public void testUpdateAvailableStockWithPessimisticLock(){
        int result = bookDAO.updateAvailableStockWithPessimisticLock(10,1);
        System.out.println(result);
    }

    @Test
    public void testSelectTopBorrowed(){
        List<Book> list = bookDAO.selectTopBorrowed(5);
        System.out.println(list);
    }
}
