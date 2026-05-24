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
    private Integer categoryId;
    private Date publishDate;
    private Integer totalStock;
    private Integer availableStock;
    private String coverUrl;
    private Integer status;
    private Integer version;

}
