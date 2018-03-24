package team.qdu.smartclassserver.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.qdu.smartclassserver.dao.MaterialMapper;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.domain.Inform;
import team.qdu.smartclassserver.domain.Inform_User;
import team.qdu.smartclassserver.domain.Material;

import java.util.Date;
import java.util.List;

@Service
public class MaterialService {
    @Autowired
    MaterialMapper materialMapper;

    public void uploadFile(String name, String url, int classid) {
        Date date = new Date();
        Material material=new Material();
        material.setName(name);
        material.setUrl(url);
        material.setClass_id(classid);
    }

    public String getTeaMaterial(Integer classid) {
        ApiResponse<List<Material>> apiResponse = new ApiResponse<>("0", "success");
        apiResponse.setObjList(materialMapper.selectByClassid(classid));
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    public String getStuMaterial(Integer classid) {
        ApiResponse<List<Material>> apiResponse = new ApiResponse<>("0", "success");
        apiResponse.setObjList(materialMapper.selectByClassid(classid));
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }
    public String deleteMaterial(Integer materialId){
        ApiResponse apiResponse;
        int result=materialMapper.deleteByPrimaryKey(materialId);
        if(result==1){
            apiResponse = new ApiResponse("0", "删除资源成功");
        }else{
            apiResponse = new ApiResponse("1", "删除资源失败");
        }
        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }




}
