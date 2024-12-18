package controller.mypage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import controller.Controller;
import model.domain.Item;
import model.service.ItemManager;
import model.service.MyPageManager;
import model.service.UserManager;
import model.domain.User;

import java.io.OutputStream;

public class MyPageController implements Controller {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long userId = (Long)session.getAttribute("userId");
		String email = (String) session.getAttribute("loggedInUser");
		String nickName= "로그인필요";
		
		UserManager umanager = UserManager.getInstance();
		ItemManager imanager = ItemManager.getInstance();
		
		//이미지
		// 프로필 이미지 URL을 request에 설정
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
		 
		 if(userId != null) {
				try {
		            byte[] imageData = umanager.getProfileImageAsBytes(userId);
		            if (imageData != null) {
		                String base64Image = Base64.getEncoder().encodeToString(imageData);
		                session.setAttribute("base64Image", base64Image);
		                //response.sendRedirect(request.getContextPath() + "/profile.jsp");
		                //return "/MainPage.jsp";
		            } else {
		                response.sendError(HttpServletResponse.SC_NOT_FOUND);
		            }
		        } catch (SQLException e) {
		            throw new ServletException("Database error while fetching the image", e);
		        }
		    }
		
		//유저 이름
		if(userId != null) {
			nickName = umanager.getNicknameByUserId(userId);
		}
		//성공 소분 수
		int successfulTransaction = umanager.getSuccessfulTransaction(email);
		
		//지역 수정
		String newregion = request.getParameter("region");
		if (newregion != null && !newregion.trim().isEmpty()) {
		    // region 값이 들어왔을 때만 업데이트
		    umanager.updateRegion(email, newregion);
		}
		String region = umanager.getRegion(email);
		
		//소개글 수정
        String newText = request.getParameter("text");
        if (newText != null && !newText.trim().isEmpty()) {
            umanager.updateTextBox(email, newText);
        }
        String text = umanager.getTextBox(email);
        
        //나의 소분 리스트
        if (userId != null) {
        	List<Item> itemList = imanager.findItemList(userId);
        	request.setAttribute("ItemList", itemList);
		}
        //참여중인 소분 리스트
        if (userId != null) {
        	List<Item> paticipationItemList = imanager.findParticipationItemByUserId(userId);
        	request.setAttribute("paticipationItemList", paticipationItemList);
        }
        
        session.setAttribute("id", userId);
        session.setAttribute("nickname", nickName);
        session.setAttribute("text", text);
		session.setAttribute("region", region);
		session.setAttribute("successfulTransaction", successfulTransaction);
		
        // 기본적으로 마이페이지로 이동
        return "/MainPage.jsp";
    }
	
}