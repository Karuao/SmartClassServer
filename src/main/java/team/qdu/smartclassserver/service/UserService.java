package team.qdu.smartclassserver.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.qdu.smartclassserver.dao.UserMapper;
import team.qdu.smartclassserver.domain.A;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.domain.B;
import team.qdu.smartclassserver.domain.User;

import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public String login(String account, String password) {
        User user = userMapper.selectByAccount(account);
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

    public String register(String account, String password) {
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        ApiResponse apiResponse;
        HashMap

        int result = userMapper.insert(user);

        if (result == 0) {
            apiResponse = new ApiResponse("1", "注册失败");
        } else {
            apiResponse = new ApiResponse("0", "注册成功");
        }
        System.out.println(result);
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }
}
