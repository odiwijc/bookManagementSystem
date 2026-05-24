package com.nuist.bookms;

import com.nuist.bookms.dao.CategoryDAO;
import com.nuist.bookms.dao.impl.CategoryDAOImpl;
import com.nuist.bookms.entity.Category;
import com.nuist.bookms.entity.Permission;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestCategoryDAO {
    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Test
    public void testInsert(){
        Category c = Category.builder()
                .categoryName("小说")
                .sortOrder(6)
                .build();
        int result = categoryDAO.insert(c);
        System.out.println(result);
    }

    @Test
    public void testUpdate(){
        Category c = Category.builder()
                .categoryName("小说")
                .sortOrder(67)
                .categoryId(6)
                .build();
        int result = categoryDAO.update(c);
        System.out.println(result);
    }

    @Test
    public void testDeleteById(){
        int result = categoryDAO.deleteById(6);
        System.out.println(result);
    }

    @Test
    public void testSelectById(){
        Category category = categoryDAO.selectById(1);
        System.out.println(category);
    }

    @Test
    public void testSelectAll(){
        List<Category> list = categoryDAO.selectAll();
        System.out.println(list);
    }

    @Test
    public void testSelectByPage(){
        List<Category> list = categoryDAO.selectByPage(2,3);
        System.out.println(list);
    }

    @Test
    public void testSelectCount(){
        int result = categoryDAO.selectCount();
        System.out.println(result);
    }
}
