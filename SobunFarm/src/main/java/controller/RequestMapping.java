package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.user.*;
import controller.chat.*;
//import controller.comm.*;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    // 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {
    	// 각 uri에 대응되는 controller 객체를 생성 및 저장
        mappings.put("/", new ForwardController("index.jsp"));
        mappings.put("/home", new ForwardController("/views/home.jsp"));
        // UserController 매핑
        mappings.put("/user", new UserController());
        
        
        // 채팅 화면 관련 매핑 추가

        mappings.put("/chat", new ChatController());  // 채팅 목록 화면을 처리하는 컨트롤러
        mappings.put("/chatContent", new MessageController());  // 채팅방 내용 및 메시지 전송을 처리하는 컨트롤러

        
        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}