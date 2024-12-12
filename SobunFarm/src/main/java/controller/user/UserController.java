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
}
