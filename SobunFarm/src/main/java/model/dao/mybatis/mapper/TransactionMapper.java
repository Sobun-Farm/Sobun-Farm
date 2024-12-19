package model.dao.mybatis.mapper;

import model.domain.Transaction;
import org.apache.ibatis.annotations.Param;

public interface TransactionMapper {
    void insertTransaction(Transaction transaction);
    
 // 트랜잭션 상태 업데이트
    void updateTransactionStatus(@Param("itemId") long itemId, @Param("status") String status);
}

