package controller.user;

import controller.Controller;
import model.domain.User;
import model.service.UserManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import model.exception.UserNotFoundException;
import model.exception.PasswordMismatchException;
import model.utils.PasswordUtils;
import model.domain.User;

public class UserController implements Controller {
    private UserManager userManager;

    public UserController() {
        this.userManager = UserManager.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            return "redirect:/user?action=loginPage";
        }

        switch (action) {
            case "loginPage":
                return "/user/login.jsp";
                
            case "login":
                return handleLogin(request, response);

            case "registerPage":
                return "/user/join.jsp";

            case "homePage":
                try {
                    String encodedRegion = URLEncoder.encode("강남구", "UTF-8");
                    String encodedCategory = URLEncoder.encode("전체보기", "UTF-8");

                    response.sendRedirect(request.getContextPath() + "/home?region=" + encodedRegion + "&category=" + encodedCategory);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;

                
            case "successPage":
                return "/user/join_complete.jsp";
                
            case "register":
                 return handleRegistration(request, response);
                 
            case "nicknameCheck":   
                 return handleNicknameCheck(request, response);
                    
            case "emailCheck":
            	 return handleEmailCheck(request, response);

            case "printUsersPage":
                List<User> users = userManager.getAllUsers();
                request.setAttribute("users", users);
                return "/views/print_users.jsp";

            case "logout":
                handleLogout(request);
                return "redirect:/user?action=loginPage";
                
            case "mainPage":
            	response.sendRedirect(request.getContextPath() + "/MainPage");
            	return null;

            default:
                throw new ServletException("Unsupported action: " + action);
        }
    }

    // 로그아웃
    private void handleLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
    
    // 회원가입
    private String handleRegistration(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String userIdStr = request.getParameter("userId");
        Long userId = userIdStr != null ? Long.valueOf(userIdStr) : null;
    	String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String nickname = request.getParameter("nickname");
        String region = request.getParameter("region");

        if (!password.equals(passwordConfirm)) {
            request.setAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "/user/join.jsp";
        }

        // 비밀번호 해시화
        String hashedPassword = PasswordUtils.hashPassword(password);

        // 해시화된 비밀번호를 포함하여 User 객체 생성
        User newUser = new User(userId,email, nickname, hashedPassword, region);

        try {
            boolean isRegistered = userManager.register(newUser);
            if (isRegistered) {
                return "redirect:/user?action=successPage";
            } else {
                request.setAttribute("error", "회원가입 실패: 이메일 또는 닉네임이 이미 존재합니다.");
                return "/user/join.jsp";
            }
        } catch (Exception e) {
            throw new ServletException("회원가입 처리 중 오류 발생", e);
        }
    }

    
    // 로그인
    private String handleLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
        	User user = userManager.login(email, password);
        	if (user == null || user.getUserId() == null) {
                throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
            }

            userManager.login(email, password);
            
            // 세션에 로그인 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", email);
            session.setAttribute("userId", user.getUserId());

            return "redirect:/user?action=homePage";
        } catch (UserNotFoundException | PasswordMismatchException e) {
            // 로그인 실패 시 처리
            request.setAttribute("loginFailed", true);
            request.setAttribute("exception", e);
            return "/user/login.jsp";
        }
    }

    // 이메일 중복체크
    private String handleEmailCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String email = request.getParameter("email");
        boolean exists = userManager.isEmailExists(email);

        JsonObject jsonResponse = new JsonObject();  
        jsonResponse.addProperty("available", !exists);

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
        return null; // JSON 응답만 반환
    }
    
    // 닉네임 중복체크
    private String handleNicknameCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String nickname = request.getParameter("nickname");
        boolean exists = userManager.isNicknameExists(nickname);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("available", !exists);

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
        return null;
    }


}
