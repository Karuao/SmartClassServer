package team.qdu.smartclassserver.service;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.qdu.smartclassserver.dao.UserMapper;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.domain.Class;
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
                apiResponse.setObj(Integer.toString(user.getUser_id()));
            } else {
                //密码错误
                apiResponse = new ApiResponse("1", "密码错误");
            }
        } else {
            // 用户不存在
            apiResponse = new ApiResponse("2", "用户不存在");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);

        return jsonResponse;
    }
    public String weblogin(String account, String password) {
        User user = userMapper.selectByAccount(account);
        String event=null;

        if (user != null) {
            //该用户存在
            if (user.getPassword().equals(password)) {
                //密码正确
                event="0";
            } else {
                //密码错误
                event = "1";
            }
        } else {
            // 用户不存在
           event="2";
        }

        return event;
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
            Date date=new Date();
            user1.setCreate_date_time(date);
            user1.setModify_date_time(date);
            int msg=userMapper.insert(user1);
            apiResponse = new ApiResponse("0", "注册成功");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);

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
            apiResponse.setObj(user);
        }else{
            //该用户不存在
            apiResponse = new ApiResponse<>("2", "用户不存在");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);

        return jsonResponse;
    }

    public String getUserInforById(int userId){
        User user = userMapper.selectByPrimaryKey(userId);
        ApiResponse<User> apiResponse;
        if(user!=null){
            //该用户存在
            apiResponse = new ApiResponse<>("0","用户存在");
            apiResponse.setObj(user);
        }else{
            //该用户不存在
            apiResponse = new ApiResponse<>("2", "用户不存在");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);

        return jsonResponse;
    }
    public int getUserIdByaccount(String account){
        int userid=userMapper.getUserIdByaccount(account);
        return userid;
    }

    //找回密码时修改密码
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
        String jsonResponse = JSON.toJSONString(apiResponse);

        return jsonResponse;
    }

    //修改个人信息
    public String updateUserInformation(String avatarPath,String account,String name,String gender,String sno,String university,
                                        String department,String motto){
        ApiResponse apiResponse;
        User user = userMapper.selectByAccount(account);
        user.setAvatar(avatarPath);
        user.setName(name);
        user.setGender(gender);
        user.setSno(sno);
        user.setUniversity(university);
        user.setDepartment(department);
        user.setStatus_message(motto);
        Date now=new Date();
        user.setModify_date_time(now);
        int result=userMapper.updateByPrimaryKeySelective(user);
        if(result==1){
            apiResponse = new ApiResponse("0", "修改个人信息成功");
        }else{
            apiResponse = new ApiResponse("1", "修改个人信息失败");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);

        return jsonResponse;
    }



}
