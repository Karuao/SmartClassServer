package team.qdu.smartclassserver.service;

import com.alibaba.fastjson.JSON;
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
        ApiResponse<List<ClassUser>> apiResponse = new ApiResponse<>("0", "success");
        apiResponse.setObjList(classUserMapper.selectJoinedClassesByUserId(userId));
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }

    public List<Class> getClasses(Integer userId) {
        List<Class> classlist=classMapper.getClass(userId);
        return classlist;
    }

    //进入班课判断用户是老师还是学生
    public String jumpClass(int classId, int userId) {
        ApiResponse<ClassUser> apiResponse;
        ClassUser clickedClass = classUserMapper.selectMyClassByClassIdUserId(classId, userId);
        apiResponse = new ApiResponse<>("0", "");
        apiResponse.setObj(clickedClass);
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }

    //不允许加入班课
    public String notAllowToJoin(int classId) {
        ApiResponse apiResponse;
        Class cls = classMapper.selectByPrimaryKey(classId);
        cls.setIf_allow_to_join("否");
        int result = classMapper.updateByPrimaryKey(cls);
        if (result == 1) {
            apiResponse = new ApiResponse("0", "已设置为不允许加入");
        } else {
            apiResponse = new ApiResponse("1", "设置失败");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }

    public String allowToJoin(int classId) {
        ApiResponse apiResponse;
        Class cls = classMapper.selectByPrimaryKey(classId);
        cls.setIf_allow_to_join("是");
        int result = classMapper.updateByPrimaryKey(cls);
        if (result == 1) {
            apiResponse = new ApiResponse("0", "已设置为允许加入");
        } else {
            apiResponse = new ApiResponse("1", "设置失败");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }

    public String getClassInfor(int classId) {
        Class cls = classMapper.selectByPrimaryKey(classId);
        ApiResponse<Class> apiResponse;
        if (cls != null) {
            //该课程存在
            apiResponse = new ApiResponse<>("0", "课程存在");
            apiResponse.setObj(cls);
        } else {
            //该课程不存在
            apiResponse = new ApiResponse<>("2", "课程不存在");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);

        return jsonResponse;
    }

    public String finishClass(int classId) {
        ApiResponse apiResponse;
        Class cls = classMapper.selectByPrimaryKey(classId);
        cls.setIf_allow_to_join("已结束");
        int result = classMapper.updateByPrimaryKey(cls);
        if (result == 1) {
            apiResponse = new ApiResponse("0", "此班课已结束");
        } else {
            apiResponse = new ApiResponse("1", "失败");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }

    public String deleteClass(int classId) {
        ApiResponse apiResponse;
        Class cls = classMapper.selectByPrimaryKey(classId);
        cls.setIf_allow_to_join("已删除");
        int result = classMapper.updateByPrimaryKey(cls);
        if (result == 1) {
            apiResponse = new ApiResponse("0", "此班课已删除");
        } else {
            apiResponse = new ApiResponse("1", "删除失败");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }

    //创建班课
    public String createClass(String name, String course, int userId, String avatarPath) {
        ApiResponse<ClassUser> apiResponse;
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
        classUser.setUnbrowse_material_num(0);
        classUser.setIf_new_homework("否");
        classUser.setIf_new_class_thing("否");
        classUser.setUnread_information_num(0);
        classUser.setExp(0);
        classUser.setCreate_date_time(date);
        classUser.setModify_date_time(date);
        classUserMapper.insert(classUser);

        apiResponse = new ApiResponse<>("0", "创建班课成功");
        ClassUser apiResponseObj = new ClassUser();
        apiResponseObj.setClass_user_id(classUser.getClass_user_id());
        apiResponseObj.setClass_id(classUser.getClass_id());
        apiResponseObj.setMy_class(new Class());
        apiResponseObj.getMy_class().setAvatar(createdclass.getAvatar());
        apiResponse.setObj(apiResponseObj);
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }

    //修改班课信息
    public String modifyClass(int classId, String avatarPath, String className, String course, String university, String department, String goal, String exam) {
        ApiResponse apiResponse;
        Class cls = classMapper.selectByPrimaryKey(classId);
        cls.setName(className);
        cls.setCourse(course);
        cls.setUniversity(university);
        cls.setDepartment(department);
        cls.setDetail(goal);
        cls.setExam_shedule(exam);
        cls.setAvatar(avatarPath);
        Date date = new Date();
        cls.setModify_date_time(date);
        int result = classMapper.updateByPrimaryKeySelective(cls);
        if (result == 1) {
            apiResponse = new ApiResponse("0", "修改班课信息成功");
        } else {
            apiResponse = new ApiResponse("1", "修改班课信息失败");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }

    //加入班课
    public String joinClass(int classId, int userId) {
        ApiResponse apiResponse;
        Class joinedClass = classMapper.selectJoinClassByClassId(classId);
        ClassUser classUser = new ClassUser();
        classUser.setClass_id(classId);
        classUser.setUser_id(userId);
        if (joinedClass == null) {
            //班课不存在
            apiResponse = new ApiResponse("1", "班课不存在");
        } else if (joinedClass.getIf_allow_to_join().equals("已结束")) {
            //班课已结束
            apiResponse = new ApiResponse("2", "班课已结束");
        } else if (joinedClass.getIf_allow_to_join().equals("已删除")) {
            //班课已删除
            apiResponse = new ApiResponse("3", "班课已删除");
        } else if (joinedClass.getIf_allow_to_join().equals("否")) {
            //班课不允许加入
            apiResponse = new ApiResponse("4", "班课不允许加入");
        } else if (classUserMapper.selectByClassIdUserId(classUser) != null) {
            //用户已加入班课
            apiResponse = new ApiResponse("5", "您已加入该班课");
        } else {
            apiResponse = new ApiResponse("0", "查找班课成功");
            apiResponse.setObj(joinedClass);
        }

        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }

    public String confirmjoinClass(int classId, int userId) {
        ApiResponse<Integer> apiResponse;
        if (classUserMapper.updateIfInClassByClassIdUserId(classId, userId) == 1) {
            //处理该学生以前在班课，退出了，重新加入的情况
        } else {
            //生成要插入到ClassUser表的记录
            ClassUser classUser = new ClassUser();
            Date date = new Date();
            classUser.setClass_id(classId);
            classUser.setUser_id(userId);
            classUser.setTitle("学生");
            classUser.setIf_in_class("是");
            classUser.setUnbrowse_material_num(0);
            classUser.setIf_new_homework("否");
            classUser.setIf_new_class_thing("否");
            classUser.setUnread_information_num(0);
            classUser.setExp(0);
            classUser.setCreate_date_time(date);
            classUser.setModify_date_time(date);
            classUserMapper.insert(classUser);
        }
        apiResponse = new ApiResponse("0", "加入班课成功");
        apiResponse.setObj(classUserMapper.selectByClassIdAndUserId(classId, userId).getClass_user_id());
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }

    public String quitClass(int classId, int userId) {
        ApiResponse apiResponse;
        ClassUser obj = classUserMapper.selectByClassIdAndUserId(classId, userId);
        obj.setIf_in_class("否");
        Date date = new Date();
        obj.setModify_date_time(date);
        int result = classUserMapper.updateByPrimaryKey(obj);
        if (result == 1) {
            apiResponse = new ApiResponse("0", "成功退出班课");
        } else {
            apiResponse = new ApiResponse("1", "退出班课失败");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }

    public String readNew(int classUserId, String whichPage) {
        ClassUser classUser = new ClassUser();
        classUser.setClass_user_id(classUserId);
        if ("material".equals(whichPage)) {
            classUser.setIf_new_class_thing("否");
        } else if ("homework".equals(whichPage)){
            classUser.setIf_new_homework("否");
            classUser.setIf_new_class_thing("否");
        } else {
            classUser.setIf_new_class_thing("否");
        }
        classUserMapper.updateByPrimaryKeySelective(classUser);
        return null;
    }
}
