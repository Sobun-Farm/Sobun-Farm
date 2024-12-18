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
    	//나희
    	mappings.put("/detail", new ForwardController("/item/detail.jsp")); // 상세 페이지
        mappings.put("/edit", new ForwardController("/item/edit.jsp")); // 수정 페이지
        //mappings.put("/new_item", new ForwardController("/item/new_item.jsp")); // 수정 페이지
        mappings.put("/new_item", new ItemController());
        mappings.forEach((key, value) -> System.out.println("경로: " + key + ", Controller: " + value));
        
    	// 각 uri에 대응되는 controller 객체를 생성 및 저장
        mappings.put("/", new ForwardController("index.jsp"));
        mappings.put("/home", new HomeController());
        // UserController 매핑
        mappings.put("/user", new UserController());
        
        mappings.put("/MainPage", new MyPageController());
        mappings.put("/updateDescription", new MyPageController());
        mappings.put("/updateRegion", new MyPageController());
        mappings.put("/updateProfileImage", new MyPageController());

        
        // 채팅 화면 관련 매핑 추가
        mappings.put("/chat", new ChatController());  // 채팅 목록 화면을 처리하는 컨트롤러
        mappings.put("/chatContent", new MessageController());  // 채팅방 내용 및 메시지 전송을 처리하는 컨트롤러	

//        mappings.put("/user/login", new UserController("/user/login.jsp"));
//        mappings.put("/user/login", new UserController());
//        mappings.put("/user/logout", new LogoutController());
//        mappings.put("/user/list", new ListUserController());
//        mappings.put("/user/view", new ViewUserController());
        
        // 회원 가입 폼 요청과 가입 요청 처리 병합 (폼에 커뮤니티 선택 메뉴 추가를 위함)
//      mappings.put("/user/login", new ForwardController("/user/login.jsp"));
//      mappings.put("/user/register", new RegisterUserController());
//        mappings.put("/user/register", new RegisterUserController());

        // 사용자 정보 수정 폼 요청과 수정 요청 처리 병합
//      mappings.put("/user/update/form", new UpdateUserFormController());
//      mappings.put("/user/update", new UpdateUserController());        
//        mappings.put("/user/update", new UpdateUserController());
        
//        mappings.put("/user/delete", new DeleteUserController());
        
        // 커뮤니티 관련 request URI 추가
//        mappings.put("/community/list", new ListCommunityController());
//        mappings.put("/community/view", new ViewCommunityController());
//        mappings.put("/community/create/form", new ForwardController("/community/creationForm.jsp"));
//        mappings.put("/community/create", new CreateCommunityController());
//        mappings.put("/community/update", new UpdateCommunityController());
        
        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}