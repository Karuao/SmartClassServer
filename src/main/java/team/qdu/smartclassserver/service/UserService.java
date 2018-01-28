package team.qdu.smartclassserver.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.qdu.smartclassserver.dao.UserMapper;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.domain.User;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public String login(String account, String password) {
        User user = userMapper.selectByAccount(account);
        ApiResponse<String> apiResponse;

        if (user != null) {
            //该用户存在
            if (user.getPassword().equals(password)) {
                //密码正确
                apiResponse = new ApiResponse("0", "登陆成功");
                apiResponse.obj = Integer.toString(user.getUser_id());
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

    public String register(String account,String password,String question,String answer) {
        User user = userMapper.selectByAccount(account);
        ApiResponse apiResponse;
        if (user != null) {
            apiResponse = new ApiResponse("2", "该用户已被注册");
            //该用户已被注册

        } else {
            // 用户不存在 注册成功
            User user1=new User();
            user1.setAccount(account);
            user1.setPassword(password);
            user1.setSecurity_question(question);
            user1.setSecurity_answer(answer);


            int msg=userMapper.insert(user1);
            apiResponse = new ApiResponse("0", "注册成功");
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
            apiResponse = new ApiResponse<>("0","用户存在");
            apiResponse.obj = user;
        }else{
            //该用户不存在
            apiResponse = new ApiResponse<>("2", "用户不存在");
        }
        String jsonResponse = new Gson().toJson(apiResponse);

        return jsonResponse;
    }

    public String updatePassword(String account,String newPass){
        ApiResponse apiResponse;
        User user = userMapper.selectByAccount(account);
        user.setPassword(newPass);
        Date now=new Date();
        user.setModify_date_time(now);
        int result=userMapper.updateByPrimaryKey(user);
        if(result==1){
            apiResponse = new ApiResponse("0", "修改密码成功");
        }else{
            apiResponse = new ApiResponse("1", "修改密码失败");
        }
        String jsonResponse = new Gson().toJson(apiResponse);

        return jsonResponse;
    }


}
