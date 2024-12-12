// UserManager.java
package model.service;

import model.dao.mybatis.UserDAO;
import model.domain.User;
import model.utils.PasswordUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * 사용자 관리 API를 사용하는 개발자들이 직접 접근하게 되는 클래스.
 * UserDAO를 이용하여 데이터베이스에 데이터 조작 작업이 가능하도록 하며,
 * 데이터베이스의 데이터들을 이용하여 비지니스 로직을 수행하는 역할을 한다.
 */
public class UserManager {
    private static final UserManager instance = new UserManager();
    private final UserDAO userDAO;

    private UserManager() {
        userDAO = new UserDAO();
    }

    public static UserManager getInstance() {
        return instance;
    }
    /**
     * 사용자 등록
     * @param user 등록할 사용자 객체
     * @return 등록 성공 여부
     * @throws SQLException 데이터베이스 오류 발생 시
     */
    public boolean register(User user) throws SQLException {
        if (userDAO.isEmailExists(user.getEmail()) || userDAO.isNicknameExists(user.getNickname())) {
            return false;
        }
        user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
        return userDAO.insertUser(user);
    }

    /**
     * 사용자 조회
     * @param userId 사용자 ID
     * @return 사용자 객체
     * @throws SQLException 데이터베이스 오류 발생 시
     */
    public User findUser(String userId) throws SQLException {
        User user = userDAO.findUser(userId);
        if (user == null) {
            throw new RuntimeException(userId + "는 존재하지 않는 아이디입니다.");
        }
        return user;
    }
    /**
     * 사용자 업데이트
     * @param user 업데이트할 사용자 객체
     * @return 업데이트 성공 여부
     * @throws SQLException 데이터베이스 오류 발생 시
     */
    public boolean update(User user) throws SQLException {
        return userDAO.update(user);
    }

    /**
     * 사용자 삭제
     * @param userId 삭제할 사용자 ID
     * @return 삭제 성공 여부
     * @throws SQLException 데이터베이스 오류 발생 시
     */
    public boolean remove(String userId) throws SQLException {
        return userDAO.remove(userId);
    }
    /**
     * 사용자 로그인
     * @param email 이메일
     * @param password 비밀번호
     * @return 인증 성공 여부
     * @throws SQLException 데이터베이스 오류 발생 시
     */
    public boolean login(String email, String password) throws SQLException {
        User user = userDAO.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("사용자가 존재하지 않습니다.");
        }
        if (!PasswordUtils.hashPassword(password).equals(user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return true;
    }

    /**
     * 모든 사용자 목록 조회
     * @return 사용자 목록
     * @throws SQLException 데이터베이스 오류 발생 시
     */
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }
    
    /**
     * 이메일로 사용자 조회
     * @param email 사용자 이메일
     * @return 사용자 객체
     * @throws SQLException 데이터베이스 오류 발생 시
     */
    public User findUserByEmail(String email) throws SQLException {
        User user = userDAO.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException(email + "는 존재하지 않는 이메일입니다.");
        }
        return user;
    }
    /**
     * 닉네임 중복 여부 확인
     * @param nickname 확인할 닉네임
     * @return 중복 여부 (true: 중복, false: 중복 아님)
     * @throws SQLException 데이터베이스 오류 발생 시
     */
    public boolean isNicknameExists(String nickname) throws SQLException {
        return userDAO.isNicknameExists(nickname);
    }
    
    /**
     * 닉네임 중복 여부 확인
     * @param nickname 확인할 닉네임
     * @return 중복 여부 (true: 중복, false: 중복 아님)
     * @throws SQLException 데이터베이스 오류 발생 시
     */
    public boolean isEmailExists(String email) throws SQLException {
        return userDAO.isEmailExists(email);
    }
}