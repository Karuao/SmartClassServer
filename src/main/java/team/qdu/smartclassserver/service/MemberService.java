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
}
