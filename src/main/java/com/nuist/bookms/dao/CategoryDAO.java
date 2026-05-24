package com.nuist.bookms.dao;

import com.nuist.bookms.entity.Category;

import java.util.List;

public interface CategoryDAO {
    // 新增分类
    int insert(Category category);

    // 修改分类名或排序号
    int update(Category category);

    // 删除分类（检查是否有图书使用这个分类）
    int deleteById(int categoryId);

    // 根据ID查询分类
    Category selectById(int categoryId);

    // 查询所有分类
    List<Category> selectAll();

    // 分页查询分类
    List<Category> selectByPage(int pageNum,int pageSize);

    // 查询分类总数
    int selectCount();


}
