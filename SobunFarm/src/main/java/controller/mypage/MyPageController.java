package controller.mypage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import controller.Controller;
import model.domain.Item;
import model.service.ItemManager;
import model.service.UserManager;

public class MyPageController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("userId") == null) {
//            response.sendRedirect(request.getContextPath() + "/user?action=loginPage");
//            return null;
//        }

        Long userId = (Long) session.getAttribute("userId");
        String email = (String) session.getAttribute("loggedInUser");
        String nickName= "로그인필요";
        
        String itemIdStr = request.getParameter("itemId");
        session.setAttribute("itemId", itemIdStr);

        // 세션에 저장된 사용자 정보가 없는 경우 리다이렉트
//        if (userId == null || email == null) {
//            response.sendRedirect(request.getContextPath() + "/user?action=loginPage");
//            return null;
//        }

        UserManager umanager = UserManager.getInstance();
        ItemManager imanager = ItemManager.getInstance();
        
        if(userId !=null) {
           String profileImageUrl = "profileImage?userId=" + userId;
          request.setAttribute("profileImageUrl", profileImageUrl);
          
          if (ServletFileUpload.isMultipartContent(request)) {
                try {
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    List<FileItem> multiparts = upload.parseRequest(request);

                    for (FileItem item : multiparts) {
                        if (!item.isFormField() && "profileImage".equals(item.getFieldName())) {
                            InputStream inputStream = item.getInputStream();
                            if (userId != null) {
                                umanager.updateProfileImage(userId, inputStream);
                            }
                        }
                    }
                    request.setAttribute("message", "이미지 업로드 성공");
                } catch (Exception e) {
                    request.setAttribute("message", "이미지 업로드 실패: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        
        if(userId != null) {
         try {
               byte[] imageData = umanager.getProfileImageAsBytes(userId);
               if (imageData != null) {
                   String base64Image = Base64.getEncoder().encodeToString(imageData);
                   session.setAttribute("base64Image", base64Image);
                   //response.sendRedirect(request.getContextPath() + "/profile.jsp");
                   //return "/MainPage.jsp";
               } 
           } catch (SQLException e) {
               throw new ServletException("Database error while fetching the image", e);
           }
       }
        
        
        //진행 중인 소분 수
        int count = 0;
        if (userId != null) {
           count = imanager.countMyItem(userId);
        }

        session.setAttribute("count", count);


        if(userId != null) {
           try {
                // 유저 정보 처리
                nickName = umanager.getNicknameByUserId(userId);
                session.setAttribute("nickname", nickName);

                // 지역 정보 처리
                String newRegion = request.getParameter("region");
                if (newRegion != null && !newRegion.trim().isEmpty()) {
                    umanager.updateRegion(email, newRegion);
                }
                String region = umanager.getRegion(email);
                session.setAttribute("region", region);

                // 소개글 처리
                String newText = request.getParameter("text");
                if (newText != null && !newText.trim().isEmpty()) {
                    umanager.updateTextBox(email, newText);
                }
                String text = umanager.getTextBox(email);
                session.setAttribute("text", text);

                // 성공 소분 수 처리
                int successfulTransaction = umanager.getSuccessfulTransaction(email);
                session.setAttribute("successfulTransaction", successfulTransaction);

                // 나의 소분 리스트
                List<Item> itemList = imanager.findItemList(userId);
                request.setAttribute("ItemList", itemList);

                // 참여중인 소분 리스트
                List<Item> participationItemList = imanager.findParticipationItemByUserId(userId);
                request.setAttribute("participationItemList", participationItemList);

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "마이페이지 로드 중 문제가 발생했습니다.");
                return "/error.jsp";
            }
        }

        return "/MainPage.jsp";
    }
}
