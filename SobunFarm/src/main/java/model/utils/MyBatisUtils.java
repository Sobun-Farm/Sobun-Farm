package model.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    // static 블록에서 SqlSessionFactory 초기화
    static {
        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException("MyBatis 설정 로드 실패", e);
        }
    }

    // SqlSessionFactory 가져오기
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    // SqlSession 객체를 반환하는 메서드 추가
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
