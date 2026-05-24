package com.nuist.bookms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class BorrowRecord {
    private Integer recordId;
    private Integer userId;
    private Integer bookId;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    private Integer renewCount;
    private Integer status;
}
