package team.qdu.smartclassserver.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.qdu.smartclassserver.dao.ClassMapper;
import team.qdu.smartclassserver.dao.ClassUserMapper;
import team.qdu.smartclassserver.dao.UserMapper;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.domain.ClassUser;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    ClassMapper classMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ClassUserMapper classUserMapper;

    //获取用户班课列表
    public String getClassMembers(Integer classId) {
        ApiResponse<List<ClassUser>> apiResponse = new ApiResponse<>("0", "success");
        apiResponse.setObjList(classUserMapper.selectClassMembersByClassId(classId));
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
}
