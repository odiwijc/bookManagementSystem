package com.nuist.bookms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class BorrowRecord {
    private Integer recordId;
    private Integer userId;
    private Integer bookId;
    private java.sql.Date borrowDate;
    private java.sql.Date dueDate;
    private java.sql.Date returnDate;
    private Integer renewCount;
    private Integer status; // 1:未还 2:已还 3:逾期
}
