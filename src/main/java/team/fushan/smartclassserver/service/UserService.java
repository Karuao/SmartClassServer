package team.fushan.smartclassserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.fushan.smartclassserver.dao.UserMapper;
import team.fushan.smartclassserver.domain.User;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public int login(String email, String password) {
        User user = userMapper.selectByEmail(email);
        return user.getPassword().equals(password) ? 1:0;
    }
}
