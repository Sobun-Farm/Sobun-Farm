package controller.item;

import controller.Controller;
import model.dao.mybatis.ItemDAO;
import model.domain.Item;
import model.dto.ItemDTO;

import model.dao.mybatis.ItemGroupDAO;
import model.dao.mybatis.TransactionDAO;
import model.dao.mybatis.ChatDAO;

import model.domain.ItemGroup;
import model.domain.Transaction;
import model.domain.Chat;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@MultipartConfig // 파일 업로드를 처리하기 위해 추가
public class ItemController implements Controller {
	 private final ItemDAO itemDAO = new ItemDAO(); // DAO 인스턴스 생성
	 private final ItemGroupDAO itemGroupDAO = new ItemGroupDAO();
	 private final TransactionDAO transactionDAO = new TransactionDAO();
	 private final ChatDAO chatDAO = new ChatDAO();


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 요청 메서드 확인
            String method = request.getMethod();
            System.out.println("HTTP 요청 메서드: " + method);

            if ("GET".equalsIgnoreCase(method)) {
                return handleGet(request, response);
            } else if ("POST".equalsIgnoreCase(method)) {
                return handlePost(request, response);
            } else {
                throw new ServletException("허용되지 않은 요청 메서드: " + method);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("ItemController 처리 중 오류 발생", e);
        }
    }

    /**
     * GET 요청 처리 - 거래 등록 페이지로 이동
     */
    private String handleGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("GET 요청 처리 - 거래 등록 페이지 반환");
        return "/item/new_item.jsp"; // JSP 경로 반환
    }

    /**
     * POST 요청 처리 - 거래 등록 처리
     */
    private String handlePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                return "/user/login.jsp";
            }

            // 데이터 읽기 (파일 업로드를 위해 MultipartRequest 사용)
            String directory = "C:\\Users\\kimna\\Downloads\\Sobun-Farm-hayoun\\Sobun-Farm-hayoun\\SobunFarm\\src\\main\\webapp\\uploaded"; // 업로드 디렉토리 경로
            int sizeLimit = 100 * 1024 * 1024; // 100MB 제한
            
            MultipartRequest multi = new MultipartRequest(request, directory, sizeLimit, "UTF-8", new DefaultFileRenamePolicy() );

            String title = multi.getParameter("title");
            String itemName = multi.getParameter("itemName");
            String priceStr = multi.getParameter("price");
            double price = (priceStr != null && !priceStr.trim().isEmpty()) ? Double.parseDouble(priceStr) : 0.0;
            String maxParticipantStr = multi.getParameter("maxParticipant");
            int maxParticipant = (maxParticipantStr != null) ? Integer.parseInt(maxParticipantStr) : 0;
            String region = multi.getParameter("region");
            String category = multi.getParameter("category");
            String description = multi.getParameter("description");
            String deadlineStr = multi.getParameter("deadline");
            Date deadline = (deadlineStr != null && !deadlineStr.trim().isEmpty()) ? Date.valueOf(deadlineStr) : null;
            String purchaseLocation = multi.getParameter("purchaseLocation");
            String transactionLocation = multi.getParameter("transactionLocation");
            String transactionTime = multi.getParameter("transactionTime");
            
            // 데이터 출력 (디버깅용)
            System.out.println("===== 입력받은 데이터 =====");
            System.out.println("Title: " + title);
            System.out.println("Item Name: " + itemName);
            System.out.println("Price: " + price);
            System.out.println("Max Participants: " + maxParticipant);
            System.out.println("Region: " + region);
            System.out.println("Category: " + category);
            System.out.println("Description: " + description);
            System.out.println("Deadline: " + deadline);
            System.out.println("Purchase Location: " + purchaseLocation);
            System.out.println("Transaction Location: " + transactionLocation);
            System.out.println("Transaction Time: " + transactionTime);
            System.out.println("==========================");

            // 파일 처리 (파일 이름을 가져오기)
            String fileName = multi.getOriginalFileName("photo");  // 업로드된 파일의 원본 이름
            String fileRealName = multi.getFilesystemName("photo"); // 서버에 저장된 파일 이름

            System.out.println("Uploaded file: " + fileName + ", Saved as: " + fileRealName);
            System.out.println("디렉토리 " + directory);

            // ItemDTO 객체 생성
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setTitle(title);
            itemDTO.setItemName(itemName);
            itemDTO.setPrice(price);
            itemDTO.setParticipantsCount(0);
            itemDTO.setMaxParticipant(maxParticipant);
            itemDTO.setJoinable(true);
            itemDTO.setRegion(region);
            itemDTO.setCategory(category);
            itemDTO.setDescription(description);
            itemDTO.setDeadline(deadline);
            itemDTO.setItemStatus("AVAILABLE");
            itemDTO.setUserId(userId);
            itemDTO.setPurchaseLocation(purchaseLocation);
            itemDTO.setFileName(fileName); // 파일명
            itemDTO.setFileRealName(fileRealName); // 실제 파일명
            itemDTO.setTransactionLocation(transactionLocation);
            itemDTO.setTransactionTime(transactionTime);
            
            // Item 객체로 변환
            Item item = new Item();
            item.setTitle(itemDTO.getTitle());
            item.setItemName(itemDTO.getItemName());
            item.setPrice(itemDTO.getPrice());
            item.setParticipantsCount(itemDTO.getParticipantsCount());
            item.setMaxParticipant(itemDTO.getMaxParticipant());
            item.setJoinable(itemDTO.isJoinable());
            item.setRegion(itemDTO.getRegion());
            item.setCategory(itemDTO.getCategory());
            item.setDescription(itemDTO.getDescription());
            item.setDeadline(itemDTO.getDeadline());
            item.setItemStatus(itemDTO.getItemStatus());
            item.setUserId(itemDTO.getUserId());
            item.setPurchaseLocation(itemDTO.getPurchaseLocation());
            item.setFileName(itemDTO.getFileName());
            item.setFileRealName(itemDTO.getFileRealName());
            item.setTransactionLocation(itemDTO.getTransactionLocation());
            item.setTransactionTime(itemDTO.getTransactionTime());

            // DAO를 통해 DB에 저장
            itemDAO.insertItem(item);
            // itemId를 가져와서 필요한 후속 작업
            Long itemId = itemDAO.getItemId();  // 이 값이 바로 시퀀스 값
            
            System.out.println("등록된 itemId: " + itemId);  // 디버깅용 로그
            
            // 아이템 그룹 등록
            ItemGroup itemGroup = new ItemGroup(itemId);
            itemGroupDAO.insertItemGroup(itemGroup);
            System.out.println("등록된 itemGroup itemId: " + itemGroup.getItemId());

            // 트랜잭션 등록
            Transaction transaction = new Transaction(itemId);
            transactionDAO.insertTransaction(transaction);
            System.out.println("등록된 Transaction itemId: " + transaction.getItemId());

            // 챗 등록
            Chat chat = new Chat(itemId);
            chatDAO.createChatRoom(chat);
            System.out.println("등록된 Chat itemId: " + chat.getItemId());
            		
            response.sendRedirect(request.getContextPath() + "/home");
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            
            System.out.println("===== 에러 발생 =====");
            System.out.println("입력 데이터: ");
            System.out.println("Title: " + request.getParameter("title"));
            System.out.println("Item Name: " + request.getParameter("itemName"));
            System.out.println("Price: " + request.getParameter("price"));
            System.out.println("Max Participants: " + request.getParameter("maxParticipant"));
            System.out.println("Region: " + request.getParameter("region"));
            System.out.println("Category: " + request.getParameter("category"));
            System.out.println("Description: " + request.getParameter("description"));
            System.out.println("Deadline: " + request.getParameter("deadline"));
            System.out.println("Purchase Location: " + request.getParameter("purchaseLocation"));
            System.out.println("Transaction Location: " + request.getParameter("transactionLocation"));
            System.out.println("Transaction Time: " + request.getParameter("transactionTime"));
            System.out.println("==========================");

            throw new ServletException("거래 등록 중 오류 발생", e);
        }
    }
}
