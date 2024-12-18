package controller.item;

import model.dao.mybatis.ItemDAO;
import model.dao.mybatis.UserDAO;
import model.domain.Item;
import model.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.Controller;

public class DetailController implements Controller {
    private ItemDAO itemDAO = new ItemDAO();
    private UserDAO userDAO = new UserDAO(); // UserDAO 인스턴스화

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String itemIdStr = request.getParameter("itemId");
        if (itemIdStr == null || itemIdStr.isEmpty()) {
            throw new IllegalArgumentException("itemId parameter is missing.");
        }
        
        long itemId = Long.parseLong(itemIdStr);
        
        System.out.println("itemId는 " + itemId);

        // itemId에 해당하는 아이템 정보 가져오기
        Item item = itemDAO.getItemById(itemId);
        
        // itemId에 해당하는 아이템의 userId 가져오기
        long userId = item.getUserId(); // 아이템의 userId 값 가져오기

        // userId로 사용자 정보 가져오기
        User user = userDAO.getUserById(userId); // UserDAO의 getUserById 메서드 호출
       

        // 아이템 정보를 요청에 저장
        request.setAttribute("item", item);
        request.setAttribute("user", user);

        return "/item/detail.jsp";
    }
}
