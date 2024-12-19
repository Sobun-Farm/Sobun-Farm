package controller.chat;

import model.dao.mybatis.ChatDAO;
import model.domain.Chat;
import model.domain.Message;
import model.service.MessageManager;
import controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageController implements Controller {
	private final MessageManager messageManager = new MessageManager();
	
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getPathInfo(); // URI에서 추가 경로 추출
        
        // 세션에서 userId를 가져오기
        Long userId = (Long) request.getSession().getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        // 메세지 chatId 통해 가져오기
        String chatIdStr = request.getParameter("chatId");
        logger.info("chatId 파라미터 값: " + chatIdStr);
        
        if (chatIdStr == null || chatIdStr.trim().isEmpty()) {
            logger.error("chatId 파라미터가 비어있습니다!");
            request.setAttribute("invalidChatId", true); // invalidChatId 플래그 추가
            return "/chat/chatContent.jsp"; // 그대로 chatContent.jsp로 포워딩
        }

        Long chatId = Long.parseLong(chatIdStr); // 정상적인 chatId 값으로 변환

        
        // chatId 통해 메시지 처리	
        if (request.getMethod().equals("GET")) { // GET 요청: 특정 채팅방의 메시지 조회
            // 메시지 목록을 가져와서 request에 설정
            List<Message> messages = messageManager.getMessagesByChatId(chatId);
            
            
            // 채팅방 정보 가져오기 (itemName 포함)
            Chat chatRoom = new ChatDAO().findChatRoomById(chatId); // ChatDAO 호출
            String itemName = (chatRoom != null) ? chatRoom.getItemName() : "알 수 없는 채팅방";
            
            request.setAttribute("chatId", chatId);
            request.setAttribute("messageList", messages);
            request.setAttribute("itemName", itemName);
            
            logger.error("get으로 내용 나옴");
            // 채팅방 내용 페이지로 포워딩
            return "/chat/chatContent.jsp";
        }
        else if(request.getMethod().equals("POST")) { // POST 요청: 새 메시지 전송
        	logger.info("post 요청 받음");
        	
        	
            String content = request.getParameter("messageContent");
            logger.info("MessageContent : " + content);

            if (content != null && !content.trim().isEmpty()) {  // 메시지 내용이 비어있지 않으면
            	Message message = new Message();
            	message.setChatId(chatId);
            	message.setUserId(userId);
            	message.setContent(content);
            	message.setTimestamp(LocalDateTime.now());
            	// messageId는 설정하지 않음
                
                messageManager.sendMessage(message);  // 메시지 전송
                logger.info("Message 전송 완료");
                
                String uri = "redirect:/chatContent?chatId=" + chatId;
                logger.info("Redirect URI: " + uri);
                
                return uri;

            } else {
                // 메시지가 비어있으면 에러 처리
            	logger.error("messageContent 파라미터가 비어있습니다!");
                return "redirect:/chatContent?chatId=" + chatId + "&error=emptyMessage"; 
            }
        }
        
        return null;
        
       
    }

}
