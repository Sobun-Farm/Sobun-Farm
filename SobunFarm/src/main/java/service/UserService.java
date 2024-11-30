package service;

import dao.UserDAO;
import model.User;

import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO(); // UserDAO 초기화
    }

    /**
     * 사용자 등록
     *
     * @param user 등록할 사용자 객체
     * @return 등록 성공 여부
     */
    public boolean register(User user) {
        // 이메일 또는 닉네임 중복 확인
        if (userDAO.isEmailExists(user.getEmail()) || userDAO.isNicknameExists(user.getNickname())) {
            return false; // 중복된 경우 등록 실패
        }
        return userDAO.insertUser(user); // 사용자 정보 저장
    }

    /**
     * 이메일 사용 가능 여부 확인
     *
     * @param email 확인할 이메일
     * @return 사용 가능 여부 (true: 사용 가능, false: 중복)
     */
    public boolean isEmailAvailable(String email) {
        return !userDAO.isEmailExists(email);
    }

    /**
     * 닉네임 사용 가능 여부 확인
     *
     * @param nickname 확인할 닉네임
     * @return 사용 가능 여부 (true: 사용 가능, false: 중복)
     */
    public boolean isNicknameAvailable(String nickname) {
        return !userDAO.isNicknameExists(nickname);
    }

    /**
     * 모든 사용자 데이터를 출력
     *
     * @return 사용자 목록
     */
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    /**
     * 로그인 인증
     *
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @return 인증 성공 시 사용자 객체, 실패 시 null
     */
    public User authenticate(String email, String password) {
        User user = userDAO.findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
