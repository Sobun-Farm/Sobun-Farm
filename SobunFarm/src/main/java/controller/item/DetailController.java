package controller.item;

import model.dao.mybatis.ItemDAO;
import model.domain.Item;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.Controller;

public class DetailController implements Controller {
    private ItemDAO itemDAO = new ItemDAO();

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
       

        // 아이템 정보를 요청에 저장
        request.setAttribute("item", item);

        return "/item/detail.jsp";
    }
}
