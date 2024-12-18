package model.dao.mybatis.mapper;

import model.domain.Message;
import java.util.List;

public interface MessageMapper {
    // 특정 채팅방의 메시지 목록 조회
    List<Message> findMessagesByChatId(long chatId);

    // 메시지 삽입
    void insertMessage(Message message);
}
