package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.user.*;
import controller.chat.ChatController;
import controller.chat.MessageController;
import controller.item.*;
import controller.mypage.MyPageController;
//import controller.comm.*;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    // 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {

    	mappings.put("/", new ForwardController("index.jsp"));

        // 홈
        mappings.put("/home", new HomeController());
        
        // 회원
        mappings.put("/user", new UserController());
        
        // 마이페이지
        mappings.put("/MainPage", new MyPageController());
        mappings.put("/updateDescription", new MyPageController());
        mappings.put("/updateRegion", new MyPageController());
        mappings.put("/updateProfileImage", new MyPageController());
        
        // 채팅
        mappings.put("/chat", new ChatController());  // 채팅 목록 화면을 처리
        mappings.put("/chatContent", new MessageController());  // 채팅방 내용 및 메시지 전송을 처리

        // 물품
        mappings.put("/detail", new DetailController()); 
        mappings.put("/new_item", new ItemController());
        mappings.forEach((key, value) -> System.out.println("경로: " + key + ", Controller: " + value));
    	mappings.put("/detail", new ForwardController("/item/detail.jsp")); // 상세 페이지
        mappings.put("/edit", new ForwardController("/item/edit.jsp")); // 수정 페이지

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}