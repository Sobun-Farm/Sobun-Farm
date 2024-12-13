package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    // 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {
    	// 각 uri에 대응되는 controller 객체를 생성 및 저장
        mappings.put("/home", new ForwardController("/views/home.jsp")); // 홈 화면
        mappings.put("/detail", new ForwardController("/views/detail.jsp")); // 상세 페이지
        mappings.put("/edit", new ForwardController("/views/edit.jsp")); // 수정 페이지

        logger.info("Request Mapping 초기화 완료!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}