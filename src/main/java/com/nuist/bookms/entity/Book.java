package com.nuist.bookms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer bookId;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Integer categoryId;
    private java.sql.Date publishDate;
    private Integer totalStock;
    private Integer availableStock;
    private String coverUrl;
    private Integer status; // 0:下架 1:上架
    private Integer version;

}
