package team.qdu.smartclassserver.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.qdu.smartclassserver.dao.ClassMapper;
import team.qdu.smartclassserver.dao.ClassUserMapper;
import team.qdu.smartclassserver.dao.UserMapper;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.domain.ClassUser;

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

    //教师端开始签到
    public String beginSignInForTeacher(int classId) {
        ApiResponse<Attendance> apiResponse;
        Attendance attendance = new Attendance();
        attendance.setClass_id(classId);
        attendance.setIf_open("签到中");
        Date date = new Date();
        attendance.setCreate_date_time(date);
        attendance.setModify_date_time(date);
        int result = attendanceMapper.insert(attendance);
        if (result == 1) {
            apiResponse = new ApiResponse("0", "已开始签到");
            apiResponse.setObj(attendance);
        } else {
            apiResponse = new ApiResponse("1", "签到失败");
        }
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    //教师放弃本次签到
    public String giveUpSignIn(int attendanceId) {
        ApiResponse apiResponse;
        int result = attendanceMapper.deleteByPrimaryKey(attendanceId);
        if (result == 1) {
            apiResponse = new ApiResponse("0", "已放弃本次签到");
        } else {
            apiResponse = new ApiResponse("1", "放弃失败");
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
