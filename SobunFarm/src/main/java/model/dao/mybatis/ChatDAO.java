package model.dao.mybatis;

import model.dao.mybatis.mapper.ChatMapper;
import model.domain.Chat;
import model.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class ChatDAO {
    
    // 사용자 ID로 채팅방 목록 조회
    public List<Chat> getChatRoomsByUser(int userId) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSession()) {
            ChatMapper mapper = session.getMapper(ChatMapper.class);
            return mapper.findChatRoomsByUser(userId);
        }
    }

    // 새로운 채팅방 생성
    public void createChatRoom(Chat chat) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSession()) {
            ChatMapper mapper = session.getMapper(ChatMapper.class);
            mapper.createChatRoom(chat);
            session.commit();  // 커밋
        }
    }

    // 마지막 메시지 업데이트 (메시지는 Message 테이블에서 가져와야 함)
    public void updateLastMessage(int chatId, String lastMessage) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSession()) {
            ChatMapper mapper = session.getMapper(ChatMapper.class);
            mapper.updateLastMessage(chatId, lastMessage);
            session.commit();  // 커밋
        }
    }
}
