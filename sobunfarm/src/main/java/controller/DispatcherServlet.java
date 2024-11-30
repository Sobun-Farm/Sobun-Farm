package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "dispatcherServlet", urlPatterns = "/*", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private RequestMapping requestMapping;

    @Override
    public void init() throws ServletException {
        // RequestMapping 초기화 (컨트롤러와 요청 매핑)
        requestMapping = new RequestMapping();
        requestMapping.initMapping();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 요청 경로와 컨텍스트 경로 가져오기
            String contextPath = request.getContextPath();
            String servletPath = request.getServletPath();

            // 해당 경로에 대한 컨트롤러 찾기
            Controller controller = requestMapping.findController(servletPath);

            if (controller == null) {
                // 컨트롤러를 찾지 못한 경우 404 에러
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Requested page not found.");
                return;
            }

            // 컨트롤러 실행 및 결과 URI 반환
            String uri = controller.execute(request, response);

            if (uri == null) {
                // Ajax 요청 등으로 URI 반환이 없는 경우 처리 종료
                return;
            }

            // 반환된 URI에 따라 리다이렉션 또는 포워딩 수행
            if (uri.startsWith("redirect:")) {
                // 리다이렉션 처리
                String targetUri = contextPath + uri.substring("redirect:".length());
                response.sendRedirect(targetUri);
            } else {
                // 포워딩 처리
                String targetUri = "/WEB-INF" + uri;
                RequestDispatcher rd = request.getRequestDispatcher(targetUri);
                rd.forward(request, response);
            }
        } catch (Exception e) {
            // 예외 처리 (500 에러)
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
