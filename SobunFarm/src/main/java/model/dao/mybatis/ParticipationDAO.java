package model.dao.mybatis;

import model.dao.mybatis.mapper.ParticipationMapper;
import model.domain.Participation;
import org.apache.ibatis.session.SqlSession;
import model.utils.MyBatisUtils;

public class ParticipationDAO {

    // 참여 정보 삽입
    public void insertParticipation(long itemId, long userId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            session.getMapper(ParticipationMapper.class).insertParticipation(itemId, userId);
            session.commit();
        }
    }

    // 특정 아이템에 특정 참여자가 참가했는지 조회
    public boolean isUserParticipated(long itemId, long userId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            return session.getMapper(ParticipationMapper.class).isUserParticipated(itemId, userId);
        }
    }
}
