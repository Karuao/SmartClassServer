package team.qdu.smartclassserver.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.qdu.smartclassserver.dao.*;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.domain.Attendance;
import team.qdu.smartclassserver.domain.Attendance_user;
import team.qdu.smartclassserver.domain.Class;
import team.qdu.smartclassserver.domain.ClassUser;
import team.qdu.smartclassserver.util.PushUtil;

import javax.swing.plaf.PanelUI;
import java.util.Date;
import java.util.List;

@Service
public class MemberService {
    @Autowired
    ClassMapper classMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ClassUserMapper classUserMapper;

    @Autowired
    AttendanceMapper attendanceMapper;

    @Autowired
    Attendance_userMapper attendance_userMapper;

    //获取用户班课列表
    public String getClassMembers(Integer classId) {
        ApiResponse<List<ClassUser>> apiResponse = new ApiResponse<>("0", "success");
        apiResponse.setObjList(classUserMapper.selectClassMembersByClassId(classId));
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    //教师获取签到历史记录
    public String getTeacherSignInHistory(Integer classId) {
        ApiResponse<List<Attendance>> apiResponse = new ApiResponse<>("0", "success");
        apiResponse.setObjList(attendanceMapper.selectAttendanceByClassId(classId));
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    //学生获取签到历史记录
    public String getStudentSignInHistory(Integer userId,Integer classId) {
        ApiResponse<List<Attendance_user>> apiResponse = new ApiResponse<>("0", "success");
        apiResponse.setObjList(attendance_userMapper.selectAttendanceUserByUserIdAndClassId(userId,classId));
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    //获取用户班课列表
    public String getMemberInfo(int classUserId){
        ClassUser cu = classUserMapper.selectMemberInfoByPrimaryKey(classUserId);
        ApiResponse<ClassUser> apiResponse;
        if(cu!=null){
            //该用户存在
            apiResponse = new ApiResponse<>("0","成员存在");
            apiResponse.setObj(cu);
        }else{
            //该用户不存在
            apiResponse = new ApiResponse<>("2", "成员不存在");
        }
        String jsonResponse = new Gson().toJson(apiResponse);

        return jsonResponse;
    }

    //获取签到信息
    public String getAttendanceInfo(int classId){
        List<Attendance> at = attendanceMapper.selectByClassId(classId);
        ApiResponse<List<Attendance>> apiResponse;
        if(at!=null){
            apiResponse = new ApiResponse<>("0","签到记录存在");
            apiResponse.setObjList(at);
        }else{
            apiResponse = new ApiResponse<>("2", "签到记录不存在");
        }
        String jsonResponse = new Gson().toJson(apiResponse);

        return jsonResponse;
    }

    //获取签到信息
    public String getAttendanceUserInfo(int attendanceId){
        List<Attendance_user> at = attendance_userMapper.selectByAttendanceId(attendanceId);
        ApiResponse<List<Attendance_user>> apiResponse;
        if(at!=null){
            apiResponse = new ApiResponse<>("0","签到列表存在");
            apiResponse.setObjList(at);
        }else{
            apiResponse = new ApiResponse<>("2", "签到列表不存在");
        }
        String jsonResponse = new Gson().toJson(apiResponse);

        return jsonResponse;
    }

    //移出班课
    public String shiftClass(int classUserId) {
        ApiResponse apiResponse;
        int result = classUserMapper.deleteByPrimaryKey(classUserId);
        if (result == 1) {
            apiResponse = new ApiResponse("0", "已成功移出班课");
        } else {
            apiResponse = new ApiResponse("1", "移出班课失败");
        }
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    //学生开始签到
    public String beginSignInForStudent(int userId,int attendanceId,int classUserId) {
        ApiResponse apiResponse;
        Attendance_user attendance_user = attendance_userMapper.selectByUserIdAndAttendanceId(userId,attendanceId);
        ClassUser classUser = classUserMapper.selectByPrimaryKey(classUserId);
        Attendance attendance = attendanceMapper.selectByPrimaryKey(attendanceId);
        String stuSignInNum = attendance.getAttendance_stu_count().toString();
        int classId = classUser.getClass_id();
        Class aClass= classMapper.selectByPrimaryKey(classId);
        int teaId = aClass.getUser_id();
        if(attendance_user.getAttendance_status().equals("已签到")){
            apiResponse = new ApiResponse("3", "您已签到，请勿重复点击！");
        }else {
            int exp = classUser.getExp() + 5;
            classUser.setExp(exp);
            attendance_user.setAttendance_status("已签到");
            Date date = new Date();
            classUser.setModify_date_time(date);
            attendance_user.setModify_date_time(date);
            PushUtil.getSignInInfoForTeacher(String.valueOf(teaId),stuSignInNum);
            attendance_userMapper.updateByPrimaryKeySelective(attendance_user);
            classUserMapper.updateByPrimaryKeySelective(classUser);
            int result = attendanceMapper.updateSignInNumberByPrimaryKey(attendanceId);
            if (result == 1) {
                apiResponse = new ApiResponse("0", "签到成功");
            } else {
                apiResponse = new ApiResponse("1", "签到失败");
            }
        }
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    //教师端开始签到
    public String beginSignInForTeacher(int classId) {
        ApiResponse<Attendance> apiResponse;
        List<ClassUser> cm = classUserMapper.selectClassMembersByClassId(classId);
        int classMember = cm.size();
        if(classMember==0){
            apiResponse = new ApiResponse("3", "暂无学生加入此班课！");
        }else {
            Attendance attendance = new Attendance();
            attendance.setClass_id(classId);
            attendance.setIf_open("签到中");
            attendance.setAttendance_stu_count(0);
            attendance.setStu_num(classMember);
            Date date = new Date();
            attendance.setCreate_date_time(date);
            attendance.setModify_date_time(date);
            int result = attendanceMapper.insert(attendance);
            for (ClassUser cu : cm) {
                Attendance_user attendance_user = new Attendance_user();
                attendance_user.setAttendance_status("未签到");
                attendance_user.setAttendance_id(attendance.getAttendance_id());
                attendance_user.setUser_id(cu.getUser_id());
                attendance_user.setCreate_date_time(date);
                attendance_user.setModify_date_time(date);
                attendance_userMapper.insert(attendance_user);
            }

            if (result == 1) {
                apiResponse = new ApiResponse("0", "已开始签到");
                apiResponse.setObj(attendance);
            } else {
                apiResponse = new ApiResponse("1", "签到失败");
            }
        }
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    //教师放弃本次签到
    public String giveUpSignIn(int attendanceId) {
        ApiResponse apiResponse;
        attendance_userMapper.deleteByAttendanceId(attendanceId);
        int result = attendanceMapper.deleteByPrimaryKey(attendanceId);
        if (result == 1) {
            apiResponse = new ApiResponse("0", "已放弃本次签到");
        } else {
            apiResponse = new ApiResponse("1", "放弃失败");
        }
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    //教师设置学生为已签到
    public String setStudentSignIn(int attendanceUserId) {
        ApiResponse apiResponse;
        Attendance_user au = attendance_userMapper.selectByPrimaryKey(attendanceUserId);
        au.setAttendance_status("已签到");
        int result = attendance_userMapper.updateByPrimaryKeySelective(au);
        if (result == 1) {
            apiResponse = new ApiResponse("0", "修改成功");
        } else {
            apiResponse = new ApiResponse("1", "修改失败");
        }
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    //教师设置学生为未签到
    public String setStudentNotSignIn(int attendanceUserId) {
        ApiResponse apiResponse;
        Attendance_user au = attendance_userMapper.selectByPrimaryKey(attendanceUserId);
        au.setAttendance_status("未签到");
        int result = attendance_userMapper.updateByPrimaryKeySelective(au);
        if (result == 1) {
            apiResponse = new ApiResponse("0", "修改成功");
        } else {
            apiResponse = new ApiResponse("1", "修改失败");
        }
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    //教师结束本次签到
    public String endSignIn(int attendanceId) {
        ApiResponse apiResponse;
        Attendance attendance = attendanceMapper.selectByPrimaryKey(attendanceId);
        attendance.setIf_open("已结束");
        Date date = new Date();
        attendance.setModify_date_time(date);
        int result = attendanceMapper.updateByPrimaryKeySelective(attendance);
        if (result == 1) {
            apiResponse = new ApiResponse("0", "已结束本次签到");
        } else {
            apiResponse = new ApiResponse("1", "结束失败");
        }
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }
}
