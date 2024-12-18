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
}