package model.dao.mybatis.mapper;

import model.domain.Participation;
import org.apache.ibatis.annotations.Param;

public interface ParticipationMapper {
    // 참여 정보 삽입
    void insertParticipation(@Param("itemId") long itemId, @Param("userId") long userId);

    // 특정 아이템에 특정 참여자가 참가했는지 조회
    boolean isUserParticipated(@Param("itemId") long itemId, @Param("userId") long userId);
}
