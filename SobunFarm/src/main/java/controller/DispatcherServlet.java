package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@WebServlet(name = "userSevlet", urlPatterns = "/", loadOnStartup = 1)
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
       
       String contextPath = request.getContextPath();
       String servletPath = request.getServletPath();
       
       logger.debug("Context Path: {}", contextPath);  // 컨텍스트 경로 로그
        logger.debug("Servlet Path: {}", servletPath);  // 서블릿 경로 로그
       
       // URL 중 servletPath에 대응되는 controller를 구함
        Controller controller = rm.findController(servletPath);
        
        if (controller == null) {
            logger.error("Controller not found for path: {}", servletPath);
        } else {
            logger.debug("Controller found for path: {}", servletPath);
        }
        
        try {
           // controller를 통해 request 처리 후, 이동할 uri를 반환 받음
            String uri = controller.execute(request, response);
            
            if (uri == null) return;   // Ajax request 처리 완료
            
          // 반환된 uri에 따라 forwarding 또는 redirection 여부를 결정하고 이동 
            if (uri.startsWith("redirect:")) {  
               
                String targetUri = contextPath + uri.substring("redirect:".length());
                
                response.sendRedirect(targetUri); // redirect to URL
                
            } else {
               
               String targetUri = uri.startsWith("/") ? "/WEB-INF" + uri : "/WEB-INF/" + uri;
               logger.debug("Forwarding to: {}", targetUri); // 경로 확인 로그 추가
               
                RequestDispatcher rd = request.getRequestDispatcher(targetUri);
                
                rd.forward(request, response); // forward to the view page
                
            }                 
        } catch (Exception e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }
}
