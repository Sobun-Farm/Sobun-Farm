package model.dao.mybatis.mapper;

import model.domain.Chat;
import java.util.List;

public interface ChatMapper {
    // 사용자 ID로 채팅방 목록 조회
    List<Chat> findChatRoomsByUser(int userId);

    // 채팅방 생성
    void createChatRoom(Chat chat);

    // 마지막 메시지 업데이트
    void updateLastMessage(int chatId, String lastMessage);
}
