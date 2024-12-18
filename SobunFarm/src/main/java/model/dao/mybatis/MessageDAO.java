package model.dao.mybatis;

import model.dao.mybatis.mapper.MessageMapper;
import model.domain.Message;
import org.apache.ibatis.session.SqlSession;
import model.utils.MyBatisSessionFactory;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageDAO {
    
    private static final Logger logger = LoggerFactory.getLogger(MessageDAO.class);

    // 특정 채팅방의 메시지 목록 조회
    public List<Message> getMessagesByChatId(long chatId) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSession()) {
            MessageMapper mapper = session.getMapper(MessageMapper.class);
            return mapper.findMessagesByChatId(chatId);
        } catch (Exception e) {
            logger.error("메시지 조회 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("메시지 조회 실패", e); // 예외를 런타임 예외로 감싸서 던짐
        }
    }

    // 메시지 삽입
    public void insertMessage(Message message) {
        try (SqlSession session = MyBatisSessionFactory.getSqlSession()) {
            MessageMapper mapper = session.getMapper(MessageMapper.class);
            mapper.insertMessage(message);
            session.commit();  // 커밋
        } catch (Exception e) {
            logger.error("메시지 삽입 중 오류 발생: ", e);
            throw new RuntimeException("메시지 삽입 실패", e);
        }
    }

}
