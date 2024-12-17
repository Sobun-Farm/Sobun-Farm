package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    private RequestMapping rm;

    @Override
    public void init() throws ServletException {
        rm = new RequestMapping();
        rm.initMapping();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        logger.debug("Method : {}, Request URI : {}, ServletPath : {}", 
                request.getMethod(), request.getRequestURI(), request.getServletPath());
        
        // Action 파라미터 확인
        String action = request.getParameter("action");
        if (action != null) {
            System.out.println("Action 파라미터: " + action);
        }
        
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();

        // URL 중 servletPath에 대응되는 controller를 구함
        Controller controller = rm.findController(servletPath);
        
        try {
            // controller를 통해 request 처리 후, 이동할 uri를 반환 받음
            String uri = controller.execute(request, response);
            
            if (uri == null) {
                // JSON 응답의 경우 이미 응답이 완료되었으므로 리턴
                return;
            }

            // 반환된 uri에 따라 forwarding 또는 redirection 여부를 결정하고 이동
            if (uri.startsWith("redirect:")) {
                // redirection 지시
                String targetUri = contextPath + uri.substring("redirect:".length());
                response.sendRedirect(targetUri); // redirect to url            
            } else {
                // forwarding 수행
                String targetUri = "/WEB-INF" + uri;
                RequestDispatcher rd = request.getRequestDispatcher(targetUri);
                rd.forward(request, response); // forward to the view page
            }                   
        } catch (Exception e) {
            logger.error("Exception 발생: ", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"서버 오류가 발생했습니다.\"}");
        }
    }
}
