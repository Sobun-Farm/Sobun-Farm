package model.dao.mybatis;

import model.dao.mybatis.mapper.ChatMapper;
import model.domain.Chat;
import model.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class ChatDAO {

    // 사용자 ID로 채팅방 목록 조회
    public List<Chat> getChatRoomsByUser(long userId) {
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

    // 특정 채팅방 조회
    public Chat findChatRoomById(long chatId) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSession()) {
            ChatMapper mapper = session.getMapper(ChatMapper.class);
            return mapper.findChatRoomById(chatId);
        }
    }
}
