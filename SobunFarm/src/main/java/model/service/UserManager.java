package model.service;

import model.domain.User;
import model.dao.mybatis.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import model.utils.MyBatisUtils;
import java.util.List;
import model.exception.UserNotFoundException;
import model.exception.PasswordMismatchException;
import model.dao.mybatis.UserDAO;
import model.utils.PasswordUtils;

public class UserManager {
    private static UserManager instance;
       private UserDAO userDao;

       private UserManager() {
           userDao = new UserDAO();
       }

       public static UserManager getInstance() {
           if (instance == null) {
               instance = new UserManager();
           }
           return instance;
       }


   // 회원가입
    public boolean register(User user) {
        try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            // 이메일, 닉네임 중복확인
            if (userMapper.isEmailExists(user.getEmail()) > 0 || userMapper.isNicknameExists(user.getNickname()) > 0) {
                return false;
            }

            // 유저 디비에 넣기
            userMapper.insertUser(user);
            sqlSession.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 모든 사용자 데이터 가져오기
    public List<User> getAllUsers() {
        try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getAllUsers();
        } catch (Exception e) {
            throw new RuntimeException("사용자 목록 조회 중 오류 발생", e);
        }
    }

    // 닉네임 존재유무 확인
    public boolean isNicknameExists(String nickname) {
        try (SqlSession session = MyBatisUtils.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            int count = mapper.isNicknameExists(nickname);
            return count > 0; // 닉네임이 존재하면 true 반환
        } catch (Exception e) {
            throw new RuntimeException("닉네임 존재 여부 확인 중 오류 발생", e);
        }
    }
    
    public boolean isEmailExists(String email) {
        try (SqlSession session = MyBatisUtils.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.isEmailExists(email) > 0;
        }
    }

    //수정
    
    // 로그인
    public User login(String email, String password) throws UserNotFoundException, PasswordMismatchException {
        // 이메일로 사용자 조회
        User user = userDao.findUserByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User not found for email: " + email);
        }

        // 입력 비밀번호를 해시화하고 저장된 해시와 비교
        if (!PasswordUtils.checkPassword(password, user.getPassword())) {
            throw new PasswordMismatchException("Invalid password for email: " + email);
        }
        return user;
    }


}
