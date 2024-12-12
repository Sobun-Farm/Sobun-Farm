// UserDAO.java
package model.dao.mybatis;

import model.domain.User;
import model.utils.PasswordUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final String DB_URL = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb"; // Oracle DB URL
    private final String DB_USER = "dbp240201"; // DB 사용자명
    private final String DB_PASSWORD = "111135"; // DB 비밀번호

    public UserDAO() {
        try {
            // Oracle JDBC 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oracle JDBC 드라이버 로드 실패", e);
        }
    }
    /**
     * 사용자 정보 저장
     *
     * @param user 저장할 사용자 객체
     * @return 저장 성공 여부
     */
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
    
    /**
     * 사용자 정보 업데이트
     *
     * @param user 업데이트할 사용자 객체
     * @return 업데이트 성공 여부
     */
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
    /**
     * 사용자 삭제
     *
     * @param userId 삭제할 사용자 ID
     * @return 삭제 성공 여부
     */
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

    /**
     * 이메일 중복 여부 확인
     *
     * @param email 확인할 이메일
     * @return 중복 여부 (true: 중복, false: 중복 아님)
     */
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
    /**
     * 닉네임 중복 여부 확인
     *
     * @param nickname 확인할 닉네임
     * @return 중복 여부 (true: 중복, false: 중복 아님)
     */
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
    /**
     * 모든 사용자 데이터를 반환
     *
     * @return 사용자 데이터 List
     */
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM APPUSER";
        List<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
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
    } /**
     * 이메일로 사용자 찾기
    *
    * @param email 이메일 주소
    * @return 사용자 객체 (없을 경우 null)
    */
   public User findUserByEmail(String email) {
       String sql = "SELECT * FROM APPUSER WHERE email = ?";

       try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

           stmt.setString(1, email);
           ResultSet rs = stmt.executeQuery();

           if (rs.next()) {
               return new User(
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
   /**
    * 사용자 ID로 사용자 찾기
    * @param userId 사용자 ID
    * @return 사용자 객체 (없을 경우 null)
    */
   public User findUser(String userId) {
       String sql = "SELECT * FROM APPUSER WHERE USERID = ?";

       try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

           stmt.setString(1, userId);
           ResultSet rs = stmt.executeQuery();

           if (rs.next()) {
               return new User(
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
