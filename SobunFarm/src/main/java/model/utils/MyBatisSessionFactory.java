package model.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisSessionFactory {
    private static SqlSessionFactory sqlSessionFactory;

    // static 블록에서 SqlSessionFactory 초기화
    static {
        try {
            // MyBatis 설정 파일 경로
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize MyBatis SqlSessionFactory", e);
        }
    }

    // SqlSessionFactory를 반환하는 메서드
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    // SqlSession 객체를 반환하는 메서드
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
