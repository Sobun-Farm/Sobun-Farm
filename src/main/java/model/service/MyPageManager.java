package model.service;

import java.sql.SQLException;
import java.util.List;
import model.domain.User;
import model.dao.mybatis.UserDAO;
public class MyPageManager {
	 private static MyPageManager myMan = new MyPageManager();
	 private UserDAO userDao;
	 
	 private MyPageManager() {
		 try {
			 userDao = new UserDAO();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	 
	 public static MyPageManager getInstance() {
			return myMan;
	}
	 
	 public boolean updateProfileImage(String userId, String imageUrl)  throws SQLException, UserNotFoundException{
		 User user = userDao.findUser(userId);
	        if (user == null) {
	            throw new UserNotFoundException(userId + "는 존재하지 않는 사용자입니다.");
	        }

	        return userDao.updateProfileImage(userId, imageUrl);
	 }
}