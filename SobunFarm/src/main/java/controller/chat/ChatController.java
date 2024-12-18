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
        // 세션에서 사용자 ID 가져오기
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }

        // 사용자 ID로 채팅방 목록 조회
        List<Chat> chatRoomList = chatManager.getChatRoomsByUser(userId);
        for (Chat chat : chatRoomList) {
            logger.info("Chat: ID=" + chat.getChatId() + ", Item Name=" + chat.getItemName());
        }

        // JSP에서 사용할 채팅방 목록 설정
        request.setAttribute("chatRoomList", chatRoomList);

        // Chat.jsp로 forward
        return "/chat/Chat.jsp";
    }
}
