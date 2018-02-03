package team.qdu.smartclassserver.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.qdu.smartclassserver.dao.ClassMapper;
import team.qdu.smartclassserver.dao.ClassUserMapper;
import team.qdu.smartclassserver.dao.UserMapper;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.domain.Class;
import team.qdu.smartclassserver.domain.ClassUser;
import team.qdu.smartclassserver.domain.User;

import java.util.Date;
import java.util.List;

@Service
public class ClassService  {

    @Autowired
    ClassMapper classMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ClassUserMapper classUserMapper;

    //获取用户班课列表
    public String getJoinedClasses(Integer userId) {
        ApiResponse<List<Class>> apiResponse = new ApiResponse<>("0", "success");
        apiResponse.objList = classMapper.selectJoinedClassesByUserId(userId);
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    //进入班课判断用户是老师还是学生
    public String judgeTitle(int classId, int userId) {
        ApiResponse<Void> apiResponse;
        Class clickedClass = classMapper.selectByPrimaryKey(classId);
        if (userId == clickedClass.getUser_id()) {
            apiResponse = new ApiResponse<>("0", "teacher");
        } else {
            apiResponse = new ApiResponse<>("0", "student");
        }
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    //创建班课
    public String createClass(String name, String course, int userId, String avatarPath) {
        ApiResponse<String> apiResponse;
        User user = userMapper.selectByPrimaryKey(userId);
        //向class表插入班课信息
        Class createdclass = new Class();
        Date date = new Date();
        createdclass.setName(name);
        createdclass.setCourse(course);
        createdclass.setUser_id(userId);
        createdclass.setAvatar(avatarPath);
        createdclass.setUniversity(user.getUniversity());
        createdclass.setDepartment(user.getDepartment());
        createdclass.setPopulation((short) 0);
        createdclass.setIf_allow_to_join("是");
        createdclass.setCreate_date_time(date);
        createdclass.setModify_date_time(date);
        classMapper.insert(createdclass);

        //向class_user表插入老师信息
        ClassUser classUser = new ClassUser();
        classUser.setClass_id(createdclass.getClass_id());
        classUser.setUser_id(userId);
        classUser.setTitle("老师");
        classUser.setIf_in_class("是");
        classUser.setUnread_information_num(0);
        classUser.setExp(0);
        classUser.setCreate_date_time(date);
        classUser.setModify_date_time(date);
        classUserMapper.insert(classUser);

        apiResponse = new ApiResponse<>("0", "success");
        apiResponse.obj = createdclass.getClass_id().toString();
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }
}
