package controller.item;

import model.dao.mybatis.ItemDAO;
import model.dao.mybatis.UserDAO;
import model.dao.mybatis.ParticipationDAO;
import model.dao.mybatis.TransactionDAO;

import model.domain.Item;
import model.domain.User;
import model.domain.Transaction;
import model.domain.Participation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import controller.Controller;

public class DetailController implements Controller {
    private ItemDAO itemDAO = new ItemDAO();
    private UserDAO userDAO = new UserDAO(); 
    private ParticipationDAO participationDAO = new ParticipationDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션에서 사용자 ID 가져오기
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "/user/login.jsp";
        }
    	
    	String itemIdStr = request.getParameter("itemId");
        if (itemIdStr == null || itemIdStr.isEmpty()) {
            throw new IllegalArgumentException("itemId parameter is missing.");
        }
        
        long itemId = Long.parseLong(itemIdStr);
        System.out.println("itemId는 " + itemId);

        // itemId에 해당하는 아이템 정보 가져오기
        Item item = itemDAO.getItemById(itemId);
        
        // itemId에 해당하는 아이템의 userId 가져오기
        long ownerId = item.getUserId(); // 아이템의 userId 값 가져오기
        
        // ownerId로 사용자 정보 가져오기
        User owner = userDAO.getUserById(ownerId); // UserDAO의 getUserById 메서드 호출
        
        // 현재 사용자 정보 가져오기
        //User user = userDAO.getUserById(userId);
        
        // 아이템 등록자 확인 트루면 들어온 놈이 등록자
        boolean isOwner = item.getUserId() == userId;
        
        // 거래 상태 확인 (참가자 또는 등록자에 맞게 처리)
        Transaction transaction = transactionDAO.getTransactionByItemId(itemId);
        boolean isTransactionFull = transaction != null && transaction.getTransactionStatus().equals("FULL");
        
        // 이미 참여한 사용자 여부 확인
        boolean isParticipated = participationDAO.isUserParticipated(itemId, userId);
        
        if (item.getParticipantsCount() == item.getMaxParticipant()) {
            transactionDAO.updateTransactionStatus(itemId, "FULL");
            isTransactionFull = true;
        }
        
        // 디버깅용 출력
        System.out.println("isOwner: " + isOwner);
        System.out.println("isTransactionFull: " + isTransactionFull);
        System.out.println("isParticipated: " + isParticipated);
        

        // 아이템 정보를 요청에 저장
        request.setAttribute("item", item);
        request.setAttribute("user", owner);
        
        // 버튼 상태를 설정할 모델 데이터
        request.setAttribute("isOwner", isOwner);
        request.setAttribute("isTransactionFull", isTransactionFull);
        request.setAttribute("isParticipated", isParticipated);

        // 등록자가 아닌 경우 참여한 사람이라면 비활성화된 버튼 상태를 위한 데이터
        if (isOwner) {
            // 거래완료 버튼 처리
            if ("complete".equals(request.getParameter("action"))) {
                // 관련 테이블 삭제 (아이템, 그룹, 거래, 참여자 등)
                itemDAO.deleteItem(itemId);
                // 완료 후 홈으로 리다이렉트
                return "redirect:/home";
            }
        } else {
            // 참가자라면 버튼 활성화 조건
            if (isTransactionFull) {
                // 거래가 완료된 경우 버튼 비활성화
                request.setAttribute("isTransactionFull", true);
            } else if (isParticipated) {
                // 이미 참여한 경우 버튼 비활성화
                request.setAttribute("isParticipated", true);
            } else {
                // 참가버튼 클릭시 participation 테이블에 삽입하고 ITEM 테이블에 참여자 수 증가
                if ("participate".equals(request.getParameter("action"))) {
                	// Participation 객체 생성 및 설정
                	
                	System.out.println("참가자 인써트 직전 itemId, userId: " + itemId + ", " + userId);
                    participationDAO.insertParticipation(itemId, userId);
                    itemDAO.incrementParticipantsCount(itemId);
                    
                    if(item.getParticipantsCount() == item.getMaxParticipant()) {
                    	transaction.setTransactionStatus("FULL");
                    }
                    // 참여 후 페이지 새로고침
                    return "redirect:/detail?itemId=" + itemId;
                }
            }
        }
        
        return "/item/detail.jsp";
    }
}