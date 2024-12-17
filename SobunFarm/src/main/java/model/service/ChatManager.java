package model.service;

import model.dao.mybatis.ChatDAO;
import model.domain.Chat;

import java.util.List;

public class ChatManager {
    private final ChatDAO chatDAO = new ChatDAO();

    // 사용자 ID로 채팅방 목록 조회
    public List<Chat> getChatRoomsByUser(int userId) {
        return chatDAO.getChatRoomsByUser(userId);
    }
}
