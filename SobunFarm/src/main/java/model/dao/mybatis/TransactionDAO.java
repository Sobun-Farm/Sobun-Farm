package model.dao.mybatis;

import model.domain.Transaction;
import model.dao.mybatis.mapper.TransactionMapper;
import org.apache.ibatis.session.SqlSession;
import model.utils.MyBatisUtils;

public class TransactionDAO {

    // 트랜잭션 등록
    public void insertTransaction(Transaction transaction) {
        try (SqlSession session = MyBatisUtils.getSqlSession()) {
        	TransactionMapper mapper = session.getMapper(TransactionMapper.class);
            // TransactionMapper를 통해 insert 실행
            mapper.insertTransaction(transaction);  // MyBatis 매퍼의 insertTransaction 호출
            session.commit(); // 커밋
        } catch (Exception e) {
            throw new RuntimeException("Transaction 등록 중 오류 발생", e);
        }
    }
    
    public Transaction getTransactionByItemId(long itemId) {
	    try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
	        return session.selectOne("model.dao.mybatis.mapper.TransactionMapper.getTransactionByItemId", itemId);
	    } 
	}
    
 // 트랜잭션 상태 업데이트
    public void updateTransactionStatus(long itemId, String status) {
        try (SqlSession session = MyBatisUtils.getSqlSession()) {
            TransactionMapper mapper = session.getMapper(TransactionMapper.class);
            mapper.updateTransactionStatus(itemId, status);
            session.commit(); // 커밋
        } catch (Exception e) {
            throw new RuntimeException("Transaction 상태 업데이트 중 오류 발생", e);
        }
    }
}
