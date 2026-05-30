package com.nuist.bookms.dao;

import com.nuist.bookms.entity.BorrowRecord;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface BorrowRecordDAO {
    // 新增借阅记录（借书时调用）
    int insert(BorrowRecord record);

    // 更新借阅记录（还书、续借时修改状态status、实际还书日期return_date等）
    int update(BorrowRecord record);

    // 根据记录ID查询
    BorrowRecord selectById(Integer recordId);

    // 分页查询某用户的借阅历史
    List<BorrowRecord> selectByUserId(Integer userId, Integer pageNum, Integer pageSize);

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

    // 管理员统计查询条件下的记录总数
    int selectAllCount(BorrowRecord searchCondition);

    // 查询某本书的总借阅次数
    int getBorrowCount(int bookId);
}
