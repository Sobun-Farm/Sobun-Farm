package model.service;

import model.domain.User;
import model.dao.mybatis.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import model.utils.MyBatisUtils;
import java.util.List;

public class UserManager {
    private static UserManager instance = new UserManager();

    private UserManager() {}

    public static UserManager getInstance() {
        return instance;
    }

    public boolean register(User user) {
        try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            // Check for duplicate email or nickname
            if (userMapper.isEmailExists(user.getEmail()) > 0 || userMapper.isNicknameExists(user.getNickname()) > 0) {
                return false;
            }

            // Insert the user
            userMapper.insertUser(user);
            sqlSession.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 모든 사용자 데이터를 가져옵니다.
     * @return 사용자 목록
     */
    public List<User> getAllUsers() {
        try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getAllUsers();
        } catch (Exception e) {
            throw new RuntimeException("사용자 목록 조회 중 오류 발생", e);
        }
    }

    /**
     * 이메일이 존재하는지 확인합니다.
     * @param email 이메일 주소
     * @return 존재 여부 
     */
    public boolean isNicknameExists(String nickname) {
        try (SqlSession session = MyBatisUtils.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            int count = mapper.isNicknameExists(nickname);
            return count > 0; // 닉네임이 존재하면 true 반환
        } catch (Exception e) {
            throw new RuntimeException("닉네임 존재 여부 확인 중 오류 발생", e);
        }
    }
}
