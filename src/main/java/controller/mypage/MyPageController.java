package controller.mypage;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import controller.Controller;
import model.service.MyPageManager;

public class MyPageController implements Controller {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션에서 사용자 ID 확인
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null) {
            return "redirect:/login"; // 세션에 userId가 없으면 로그인 페이지로 리다이렉트
        }

        // 파일 업로드 요청 처리
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(request);

                for (FileItem item : items) {
                    if (!item.isFormField() && "profileImage".equals(item.getFieldName())) { // "profileImage" 필드 처리
                        String uploadDir = request.getServletContext().getRealPath("/uploads");
                        File uploadFolder = new File(uploadDir);
                        if (!uploadFolder.exists()) {
                            uploadFolder.mkdir(); // 업로드 폴더가 없으면 생성
                        }

                        String fileName = System.currentTimeMillis() + "_" + item.getName();
                        File uploadedFile = new File(uploadDir, fileName);
                        item.write(uploadedFile);

                        // 업로드된 파일 URL 생성
                        String imageUrl = "/uploads/" + fileName;

                        // DB에 이미지 URL 업데이트
                        MyPageManager manager = MyPageManager.getInstance();
                        boolean isUpdated = manager.updateProfileImage(userId, imageUrl);

                        if (isUpdated) {
                            // 세션에 이미지 경로 업데이트
                            request.getSession().setAttribute("profileImage", imageUrl);
                        } else {
                            throw new Exception("프로필 이미지 업데이트 실패");
                        }
                    }
                }
                // 성공 시 마이페이지로 리다이렉트
                return "redirect:/mypage";

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "프로필 이미지 업데이트 중 오류가 발생했습니다.");
                return "/mypage.jsp"; // 에러 메시지를 포함하여 마이페이지로 포워딩
            }
        }

        // 기본적으로 마이페이지로 이동
        return "/mypage.jsp";
    }
	
}