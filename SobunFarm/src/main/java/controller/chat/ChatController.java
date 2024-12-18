package controller.chat;

import model.domain.Chat;
import model.service.ChatManager;
import controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ChatController implements Controller {
    private final ChatManager chatManager = new ChatManager();
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션에서 사용자 ID를 가져오고 Long 타입으로 처리
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            // 세션에 userId가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/user/login";
        }

        // 사용자 ID로 채팅방 목록 조회
        List<Chat> chatRoomList = chatManager.getChatRoomsByUser(userId);
        for (Chat chat : chatRoomList) {
            logger.info("Chat: ID=" + chat.getChatId() + ", Item Name=" + chat.getItemName());
        }

        // 결과를 request attribute에 저장
        request.setAttribute("chatRoomList", chatRoomList);

        // Chat.jsp로 포워딩
        return "/chat/Chat.jsp";
    }
}
