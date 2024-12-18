package model.service;

import model.dao.mybatis.MessageDAO;
import model.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class MessageManager {
    private final MessageDAO messageDAO = new MessageDAO();
    private static final Logger logger = LoggerFactory.getLogger(MessageManager.class);

    // 특정 채팅방의 메시지 목록 조회
    public List<Message> getMessagesByChatId(long chatId) {
        try {
            List<Message> messages = messageDAO.getMessagesByChatId(chatId);
            logger.info("채팅방 {}의 메시지 목록 조회 성공", chatId);
            return messages;
        } catch (Exception e) {
            logger.error("채팅방 {} 메시지 조회 실패: {}", chatId, e.getMessage());
            throw new RuntimeException("메시지 조회 실패", e); // 예외 발생 시 런타임 예외를 던짐
        }
    }

    // 새 메시지 삽입
    public void sendMessage(Message message) {
        try {
            messageDAO.insertMessage(message);
            logger.info("메시지 전송 성공: {}", message.getContent());
        } catch (Exception e) {
            logger.error("메시지 전송 실패: {}", e.getMessage());
            throw new RuntimeException("메시지 전송 실패", e); // 예외 발생 시 런타임 예외를 던짐
        }
    }
}
