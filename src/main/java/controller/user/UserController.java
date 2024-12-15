package controller.user;

import controller.Controller;
import model.domain.User;
import model.service.UserManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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

            case "registerPage":
                return "/user/join.jsp";

            case "homePage":
                return "/views/home.jsp";
                
            case "successPage":
                return "/user/join_complete.jsp";
                
            case "register":
                    return handleRegistration(request, response);

            case "printUsersPage":
                List<User> users = userManager.getAllUsers();
                request.setAttribute("users", users);
                return "/views/print_users.jsp";

            case "logout":
                handleLogout(request);
                return "redirect:/user?action=loginPage";

            default:
                throw new ServletException("Unsupported action: " + action);
        }
    }

    private void handleLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
    private String handleRegistration(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String nickname = request.getParameter("nickname");
        String region = request.getParameter("region");

        if (!password.equals(passwordConfirm)) {
            request.setAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "/user/join.jsp";
        }

        User newUser = new User(email, nickname, password, region);

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
}
