package team.qdu.smartclassserver.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.qdu.smartclassserver.dao.ClassMapper;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.domain.Class;

import java.util.List;

@Service
public class ClassService  {

    @Autowired
    ClassMapper classMapper;

    public String getJoinedClasses(Integer userId) {
        ApiResponse<List<Class>> apiResponse = new ApiResponse<>("0", "success");
        apiResponse.objList = classMapper.selectJoinedClassesByUserId(userId);
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

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
}
