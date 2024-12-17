package model.dao.mybatis;

import model.domain.ItemGroup;
import model.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

public class ItemGroupDAO {

    public void insertItemGroup(ItemGroup itemGroup) {
        SqlSession session = null;
        try {
            // SqlSession을 MyBatisSessionFactory로부터 생성
            session = MyBatisSessionFactory.getSqlSession();
            
            // ItemGroup을 MyBatis Mapper를 통해 삽입
            session.insert("mapper.ItemGroupMapper.insertItemGroup", itemGroup);
            
            // 커밋
            session.commit();
        } catch (Exception e) {
            if (session != null) {
                session.rollback(); // 예외 발생 시 롤백
            }
            throw new RuntimeException("ItemGroup 등록 중 오류 발생", e);
        } finally {
            if (session != null) {
                session.close(); // 세션 종료
            }
        }
    }
}
