package model.dao.mybatis.mapper;

import model.domain.Transaction;

public interface TransactionMapper {
    void insertTransaction(Transaction transaction);
}
