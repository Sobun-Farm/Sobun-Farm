package controller.item;

import model.dao.mybatis.ItemDAO;

import model.domain.Item;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import controller.Controller;
import javax.servlet.http.HttpSession;

public class HomeController implements Controller {
    private ItemDAO itemDAO = new ItemDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String region = request.getParameter("region");
        String category = request.getParameter("category");
        String pageParam = request.getParameter("page");

        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        int itemsPerPage = 10;

        if (region == null || region.isEmpty()) {
            region = "전체";
        }
        if (category == null || category.isEmpty() || category.equals("전체보기")) {
            category = "%"; // 전체보기 처리
        } else {
            category = "%" + category + "%"; // LIKE 조건에 맞게 변환
        }

        int totalItems = itemDAO.getTotalItemCount(region, category);
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        int offset = (currentPage - 1) * itemsPerPage;
        List<Item> items = itemDAO.getItemsByPage(region, category, offset, itemsPerPage);

        // 요청에 데이터 설정
        request.setAttribute("items", items);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("region", region);
        request.setAttribute("category", category.replace("%", ""));

        System.out.println("Controller 반환된 아이템 개수: " + items.size());
        
        
        
        return "/views/home.jsp";
    }
}
