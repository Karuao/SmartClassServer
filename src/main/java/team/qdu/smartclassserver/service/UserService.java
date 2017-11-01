package team.qdu.smartclassserver.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.qdu.smartclassserver.dao.UserMapper;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.domain.User;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public String login(String email, String password) {
        User user = userMapper.selectByEmail(email);
        ApiResponse apiResponse;

        if (user != null) {
            //该用户存在
            if (user.getPassword().equals(password)) {
                //密码正确
                apiResponse = new ApiResponse("0", "登陆成功");
            } else {
                //密码错误
                apiResponse = new ApiResponse("1", "密码错误");
            }
        } else {
            // 用户不存在
            apiResponse = new ApiResponse("2", "用户不存在");
        }
        String jsonResponse = new Gson().toJson(apiResponse);

        return jsonResponse;
    }
}
