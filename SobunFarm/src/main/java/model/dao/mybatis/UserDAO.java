// UserDAO.java
package model.dao.mybatis;

import model.domain.User;
import model.utils.PasswordUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final String DB_URL = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb"; // Oracle DB URL
    private final String DB_USER = "dbp240201"; // DB 사용자명
    private final String DB_PASSWORD = "111135"; // DB 비밀번호
    
    public byte[] getProfileImageAsBytes(Long userId) throws SQLException {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        
        String query = "SELECT PHOTO FROM APPUSER WHERE USERID = ?";

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            psmt = conn.prepareStatement(query);
            psmt.setLong(1, userId);
            rs = psmt.executeQuery();

            if (rs.next()) {
                Blob blob = rs.getBlob("PHOTO");
                if (blob != null) {
                    return blob.getBytes(1, (int) blob.length());
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (psmt != null) psmt.close();
            if (conn != null) conn.close();
        }
        
        return null;
    }
    
    //이미지
    public InputStream getProfileImage(Long userId) throws SQLException {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        
        String query = "SELECT PHOTO FROM APPUSER WHERE USERID = ?"; // 'PHOTO' 컬럼을 선택
        
        try {
            // 데이터베이스 연결 및 쿼리 실행
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            psmt = conn.prepareStatement(query);
            psmt.setLong(1, userId);  // userId로 쿼리 실행
            rs = psmt.executeQuery();

            if (rs.next()) {
                return rs.getBinaryStream("PHOTO"); // 'PHOTO' 컬럼에서 이미지 데이터 읽기
            }
        } catch (SQLException e) {
            throw e;  // 예외 재발생 시켜 상위 메서드에서 처리
        } finally {
            // 리소스 정리
            if (rs != null) rs.close();
            if (psmt != null) psmt.close();
            if (conn != null) conn.close();
        }

        
        return null; // 이미지가 없을 경우 null 반환
    }

    
    public void updateProfileImage(Long userId, InputStream imageStream) {
        String sql = "UPDATE APPUSER SET PHOTO = ? WHERE USERID = ?";

        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setBlob(1, imageStream);
            stmt.setLong(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("프로필 이미지 업데이트 중 오류 발생", e);
        }
    }

    
    //유저 이름
    public String findNicknameByUserId(Long userId) {
        String sql = "SELECT NICKNAME FROM APPUSER WHERE USERID = ?";
        String nickname = null;

        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nickname = rs.getString("NICKNAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("닉네임 조회 중 오류 발생", e);
        }

        return nickname;
    }
    
    //성공소분수
    public int getSuccessfulTransaction(String email) throws Exception {
        String query = "SELECT SUCCESSFULTRANSACTIONS FROM APPUSER WHERE EMAIL = ?";
        int successfulTransaction = 0;

        try (
        	Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    successfulTransaction = rs.getInt("SUCCESSFULTRANSACTIONS"); // 컬럼 값 가져오기
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("성공 거래 수 조회 실패", e);
        }

        return successfulTransaction; // 조회된 성공 거래 수 반환
    }

    
    
    //지역 수정
    public String getRegionByEmail(String email) throws Exception {
        String query = "SELECT REGION FROM APPUSER WHERE EMAIL = ?";
        String region = null;

        try (
        	Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)
        ) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    region = rs.getString("region"); // region 값 가져오기
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("지역 정보 조회 실패", e);
        }

        return region; // 조회한 region 값 반환
    }
    public void updateRegion(String email, String region) throws Exception {
        String query = "UPDATE APPUSER SET REGION = ? WHERE EMAIL = ?";
        try (
        	Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)
        ) {
            pstmt.setString(1, region);
            pstmt.setString(2, email);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("지역 정보 업데이트 실패", e);
        }
    }
    
    //소개글 수정
    public void updateTextBox(String email, String newText) {
        String sql = "UPDATE APPUSER SET TEXTBOX = ? WHERE EMAIL = ?";

        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            // 파라미터 설정
            pstmt.setString(1, newText);
            pstmt.setString(2, email);

            // SQL 실행
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DB 업데이트 중 오류 발생: " + e.getMessage(), e);
        }
    }
    public String getTextBox(String email) {
    	String sql = "SELECT TEXTBOX FROM APPUSER WHERE EMAIL = ?";
    	String text = null;
    	
    	try (
    	        // 데이터베이스 연결
    			Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    	        // SQL 실행 준비
    	        PreparedStatement pstmt = conn.prepareStatement(sql)
    	    ) {
    	        // 이메일 파라미터 설정
    	        pstmt.setString(1, email);

    	        // 쿼리 실행
    	        try (ResultSet rs = pstmt.executeQuery()) {
    	            if (rs.next()) {
    	            	text = rs.getString(1);   	            
    	            }
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace(); // 예외 출력
    	    }

    	    return text;
    }

    public UserDAO() {
        try {
            // Oracle JDBC 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oracle JDBC 드라이버 로드 실패", e);
        }
    }
 
    // 유저 디비에 저장
    public boolean insertUser(User user) {
        String sql = "INSERT INTO APPUSER (USERID, EMAIL, PASSWORD, NICKNAME, REGION) " +
                "VALUES (APPUSER_SEQ.NEXTVAL, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String hashedPassword = PasswordUtils.hashPassword(user.getPassword()); // 비밀번호 암호화

            stmt.setString(1, user.getEmail());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, user.getNickname());
            stmt.setString(4, user.getRegion());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // 저장 성공 여부 반환
        } catch (SQLIntegrityConstraintViolationException e) {
            return false; // 중복된 데이터로 인해 저장 실패
        } catch (SQLException e) {
            throw new RuntimeException("사용자 저장 중 오류 발생", e);
        }
    }
    
    
    // 유저 정보 업데이트
    public boolean update(User user) {
        String sql = "UPDATE APPUSER SET EMAIL = ?, PASSWORD = ?, NICKNAME = ?, REGION = ? WHERE EMAIL = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String hashedPassword = PasswordUtils.hashPassword(user.getPassword()); // 비밀번호 암호화

            stmt.setString(1, user.getEmail());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, user.getNickname());
            stmt.setString(4, user.getRegion());
            stmt.setString(5, user.getEmail());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // 업데이트 성공 여부 반환
        } catch (SQLException e) {
            throw new RuntimeException("사용자 업데이트 중 오류 발생", e);
        }
    }
  
    // 유저 삭제
    public boolean remove(String userId) {
        String sql = "DELETE FROM APPUSER WHERE USERID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // 삭제 성공 여부 반환
        } catch (SQLException e) {
            throw new RuntimeException("사용자 삭제 중 오류 발생", e);
        }
    }

    // 이메일 중복여부 확인
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM APPUSER WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // 중복 여부 반환
            }
        } catch (SQLException e) {
            throw new RuntimeException("이메일 중복 확인 중 오류 발생", e);
        }

        return false;
    }

    // 닉네임 중복여부 확인
    public boolean isNicknameExists(String nickname) {
        String sql = "SELECT COUNT(*) FROM APPUSER WHERE nickname = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nickname);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // 중복 여부 반환
            }
        } catch (SQLException e) {
            throw new RuntimeException("닉네임 중복 확인 중 오류 발생", e);
        }

        return false;
    }
 
    // 모든 사용자 데이터 반환
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM APPUSER";
        List<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                //나희 추가
                	rs.getLong("userId"),
                    rs.getString("email"),
                    rs.getString("nickname"),
                    rs.getString("password"),
                    rs.getString("region")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("모든 사용자 데이터 조회 중 오류 발생", e);
        }

        return users;
    } 
    
   // 이메일로 사용자 찾기
   public User findUserByEmail(String email) {
       String sql = "SELECT * FROM APPUSER WHERE email = ?";

       try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

           stmt.setString(1, email);
           ResultSet rs = stmt.executeQuery();

           if (rs.next()) {
               return new User(
            		   //나희 추가
            	   rs.getLong("userId"),
                   rs.getString("email"),
                   rs.getString("nickname"),
                   rs.getString("password"),
                   rs.getString("region")
               );
           }
       } catch (SQLException e) {
           throw new RuntimeException("이메일로 사용자 찾기 중 오류 발생", e);
       }

       return null;
   }

   // userId로 사용자 찾기
   public User findUser(String userId) {
       String sql = "SELECT * FROM APPUSER WHERE USERID = ?";

       try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

           stmt.setString(1, userId);
           ResultSet rs = stmt.executeQuery();

           if (rs.next()) {
               return new User(
            		   //나희 추가
            	   rs.getLong("userId"),
                   rs.getString("email"),
                   rs.getString("nickname"),
                   rs.getString("password"),
                   rs.getString("region")
               );
           }
       } catch (SQLException e) {
           throw new RuntimeException("사용자 ID로 사용자 찾기 중 오류 발생", e);
       }

       return null;
   }
   
  
}
