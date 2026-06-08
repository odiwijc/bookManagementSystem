package com.nuist.bookms.dao;

import com.nuist.bookms.entity.BorrowRecord;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface BorrowRecordDAO {
    // 新增借阅记录（借书时调用）
    int insert(BorrowRecord record, Connection connection);

    // 更新借阅记录（还书、续借时修改状态status、实际还书日期return_date等）
    int update(BorrowRecord record, Connection connection);

    // 根据记录ID查询
    BorrowRecord selectById(Integer recordId);

    // 根据用户id和书id查询
    BorrowRecord selectByUserIdAndBookId(Integer userId, Integer bookId);

    // 分页查询某用户的借阅历史
    List<BorrowRecord> selectByPageByUserId(Integer userId, Integer pageNum, Integer pageSize);

    // 统计某用户的借阅总记录数
    int selectCountByUserId(Integer userId);

    // 查询用户当前借出中的记录（status=1或3）用于判断是否超过借书上限
    List<BorrowRecord> selectBorrowingByUserId(Integer userId);

    // 查询用户当前未还(status=1或3)的记录的总数
    int countBorrowingByUserId(Integer userId);

    // 查询所有逾期未还的记录
    List<BorrowRecord> selectOverdueRecords();

    // 直接修改记录状态（用于续借时修改续借次数和应还日期）
    int updateRecord(Integer recordId, Integer status, Date dueDate, Integer renewCount);

    // 管理员端分页查询所有借阅记录（按用户id、记录状态）
    List<BorrowRecord> selectAllByPage(Integer pageNum, Integer pageSize, BorrowRecord searchCondition);

    // 管理员统计查询条件(借书人id，书id，记录状态)下的记录总数
    int selectAllCount(BorrowRecord searchCondition);

    // 查询某本书的总借阅次数
    int getBorrowCount(int bookId);
}
