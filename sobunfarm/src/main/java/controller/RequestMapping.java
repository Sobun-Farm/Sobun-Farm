package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    // 요청 URL과 Controller 매핑 정보를 저장할 HashMap
    private Map<String, Controller> mappings = new HashMap<>();

    /**
     * 요청 URL과 Controller 매핑 초기화
     */
    public void initMapping() {
        // Home, Detail, Edit 페이지 관련 매핑
        mappings.put("/home", new ForwardController("/views/home.jsp")); // 홈 화면
        mappings.put("/detail", new ForwardController("/views/detail.jsp")); // 상세 페이지
        mappings.put("/edit", new ForwardController("/views/edit.jsp")); // 수정 페이지

        logger.info("Request Mapping 초기화 완료!");
    }

    /**
     * 주어진 URI에 해당하는 Controller 반환
     *
     * @param uri 요청된 URI
     * @return 매핑된 Controller 객체 또는 null
     */
    public Controller findController(String uri) {
        return mappings.get(uri);
    }
}
