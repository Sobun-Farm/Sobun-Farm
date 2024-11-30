package controller;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class UserController extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService(); // UserService 초기화
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 요청 데이터 인코딩 설정
        response.setCharacterEncoding("UTF-8"); // 응답 데이터 인코딩 설정
        response.setContentType("text/html; charset=UTF-8"); // 응답 컨텐츠 타입 설정
        String action = request.getParameter("action");

        switch (action) {
            case "registerPage":
                // 회원가입 페이지로 포워딩
                request.getRequestDispatcher("/join.jsp").forward(request, response);
                break;

            case "loginPage":
                // 로그인 페이지로 포워딩
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                break;

            case "printUsersPage":
                // 사용자 목록 페이지로 포워딩
                List<User> users = userService.getAllUsers();
                request.setAttribute("users", users);
                request.getRequestDispatcher("/print_users.jsp").forward(request, response);
                break;
                
            case "homePage":
                // 홈페이지로 포워딩
                request.getRequestDispatcher("/home.jsp").forward(request, response);
                break;
            
            case "successPage":
                // 회원가입 페이지로 포워딩
                request.getRequestDispatcher("/join_complete.jsp").forward(request, response);
                break;
                
            case "logout":
                handleLogout(request, response);
                break;

            case "emailCheck":
                handleEmailCheck(request, response);
                break;

            case "nicknameCheck":
                handleNicknameCheck(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 요청 데이터 인코딩 설정
        response.setCharacterEncoding("UTF-8"); // 응답 데이터 인코딩 설정
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");

        switch (action) {
            case "register":
                handleRegistration(request, response);
                break;

            case "login":
                handleLogin(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String nickname = request.getParameter("nickname");
        String region = request.getParameter("region");

        if (!password.equals(passwordConfirm)) {
            // 에러 메시지를 설정하고 회원가입 페이지로 포워딩
            request.setAttribute("error", "비밀번호가 일치하지 않습니다.");
            request.getRequestDispatcher("/join.jsp").forward(request, response);
            return;
        }

        // 새로운 사용자 생성 및 등록
        User newUser = new User(email, nickname, password, region);
        boolean isRegistered = userService.register(newUser);

        if (isRegistered) {
            // 성공 시 회원가입 완료 페이지로 리다이렉트
            response.sendRedirect("user?action=successPage");
        } else {
            // 실패 시 에러 메시지를 팝업으로 띄우고 회원가입 페이지로 리다이렉트
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script type='text/javascript'>");
            response.getWriter().println("alert('회원가입에 실패했습니다. 이메일 또는 닉네임이 이미 존재합니다.');");
            response.getWriter().println("location='user?action=registerPage';");
            response.getWriter().println("</script>");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userService.authenticate(email, password);
        if (user != null) {
            // 로그인 성공 시 세션에 사용자 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);
            response.sendRedirect("user?action=homePage"); // 로그인 후 사용자 목록 페이지로 이동
        } else {
            // 로그인 실패 시 에러 메시지를 설정하고 로그인 페이지로 포워딩
            request.setAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
    

    private void handleEmailCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        boolean isEmailAvailable = userService.isEmailAvailable(email);

        // JSON 형태로 결과 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"available\": " + isEmailAvailable + "}");
    }

    private void handleNicknameCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nickname = request.getParameter("nickname");
        boolean isNicknameAvailable = userService.isNicknameAvailable(nickname);

        // JSON 형태로 결과 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"available\": " + isNicknameAvailable + "}");
    }
    
    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 세션 무효화
        HttpSession session = request.getSession(false); // 세션이 존재하지 않을 경우 null 반환
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }

        // 로그아웃 후 홈 페이지로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/user?action=homePage");
    }
}
