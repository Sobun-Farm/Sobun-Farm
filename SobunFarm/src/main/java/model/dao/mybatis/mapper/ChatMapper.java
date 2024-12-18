package model.dao.mybatis.mapper;

import model.domain.Chat;
import java.util.List;

public interface ChatMapper {

    // 사용자 ID로 채팅방 목록 조회
    List<Chat> findChatRoomsByUser(long userId);

    // 채팅방 생성
    void createChatRoom(Chat chat);

    // 특정 채팅방 조회 (추가)
    Chat findChatRoomById(long chatId);
}
