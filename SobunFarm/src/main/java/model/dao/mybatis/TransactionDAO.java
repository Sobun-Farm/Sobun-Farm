package model.dao.mybatis;

import model.domain.Transaction;
import model.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

public class TransactionDAO {

    public void insertTransaction(Transaction transaction) {
        SqlSession session = null;
        try {
            // SqlSession을 MyBatisSessionFactory로부터 생성
            session = MyBatisSessionFactory.getSqlSession();
            
            // Transaction을 MyBatis Mapper를 통해 삽입
            session.insert("mapper.TransactionMapper.insertTransaction", transaction);
            
            // 커밋
            session.commit();
        } catch (Exception e) {
            if (session != null) {
                session.rollback(); // 예외 발생 시 롤백
            }
            throw new RuntimeException("Transaction 등록 중 오류 발생", e);
        } finally {
            if (session != null) {
                session.close(); // 세션 종료
            }
        }
    }
}
