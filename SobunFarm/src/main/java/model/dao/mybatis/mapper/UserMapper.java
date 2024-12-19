package model.dao.mybatis.mapper;

import model.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import java.util.List;


public interface UserMapper {
    List<User> getAllUsers();

    int isEmailExists(String email);

    int isNicknameExists(String nickname);

    void insertUser(User user);
}
