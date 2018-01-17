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

    public String checkAccount(String account){
        User user = userMapper.selectByAccount(account);
        ApiResponse<User> apiResponse;
        //ApiResponse<User> apiResponse = new ApiResponse<User>("0", "返回用户信息");
        //apiResponse.obj = user;
        if(user!=null){
            //该用户存在
            user.setPassword("");
            user.setSecurity_question(user.getSecurity_question());
            apiResponse = new ApiResponse<>("0","用户存在");
            apiResponse.obj = user;
        }else{
            //该用户不存在
            apiResponse = new ApiResponse<>(user,"2", "用户不存在");
        }
        String jsonResponse = new Gson().toJson(apiResponse);

        return jsonResponse;
    }

}
