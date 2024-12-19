package model.service;

import model.dao.mybatis.ChatDAO;
import model.domain.Chat;

import java.util.List;

public class ChatManager {
    private final ChatDAO chatDAO = new ChatDAO();

    // 사용자 ID로 채팅방 목록 조회
    public List<Chat> getChatRoomsByUser(long userId) {
        return chatDAO.getChatRoomsByUser(userId);
    }

    // 특정 채팅방 조회
    public Chat findChatRoomById(long chatId) {
        return chatDAO.findChatRoomById(chatId);
    }

    // 새로운 채팅방 생성
    public void createChatRoom(Chat chat) {
        chatDAO.createChatRoom(chat);
    }
}
