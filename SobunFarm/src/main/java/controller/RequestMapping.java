package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.user.*;
import controller.item.*;
//import controller.comm.*;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    // 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {
    	//나희
    	mappings.put("/detail", new DetailController()); //12/18여기 수정
        mappings.put("/edit", new ForwardController("/item/edit.jsp")); // 수정 페이지
        mappings.put("/new_item", new ItemController());
        mappings.forEach((key, value) -> System.out.println("경로: " + key + ", Controller: " + value));
        
    	// 각 uri에 대응되는 controller 객체를 생성 및 저장
        mappings.put("/", new ForwardController("index.jsp"));
        mappings.put("/home", new HomeController());
        // UserController 매핑
        mappings.put("/user", new UserController());

        
        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}