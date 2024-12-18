package controller.chat;

import model.domain.Chat;
import model.service.ChatManager;
import controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ChatController implements Controller {
    private final ChatManager chatManager = new ChatManager();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 사용자 ID를 세션에서 가져오기
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if (userId == null) {
    	    // 세션에 userId가 없다면 로그인 페이지로 리다이렉트
    	    return "redirect:/user/login";
    	}


        // 사용자 채팅방 목록 조회
        List<Chat> chatRooms = chatManager.getChatRoomsByUser(userId);

        // 결과를 request attribute에 저장
        request.setAttribute("chatRoomList", chatRooms);

        // Chat.jsp로 포워딩
        return "/chat/Chat.jsp";
    }
}
