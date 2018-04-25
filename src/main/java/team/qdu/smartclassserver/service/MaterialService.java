package team.qdu.smartclassserver.service;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.qdu.smartclassserver.config.MyWebMvcConfigurer;
import team.qdu.smartclassserver.dao.ClassUserMapper;
import team.qdu.smartclassserver.dao.MaterialMapper;
import team.qdu.smartclassserver.dao.Material_UserMapper;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.domain.Material;
import team.qdu.smartclassserver.domain.Material_User;
import team.qdu.smartclassserver.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MaterialService {
    @Autowired
    MaterialMapper materialMapper;
    @Autowired
    Material_UserMapper material_userMapper;
    @Autowired
    ClassUserMapper classUserMapper;

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
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }

    public String getStuMaterial(Integer classid,Integer userid) {
        ApiResponse<List<Material_User>> apiResponse = new ApiResponse<>("0", "success");
        Material_User material_user=new Material_User();
        material_user.setClass_id(classid);
        material_user.setUser_id(userid);
        int result=material_userMapper.updateBrowse(material_user);
        int result2=classUserMapper.updateBrowseNum(material_user);
        apiResponse.setObjList(material_userMapper.selectByClassidUserid(material_user));
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }
    public String downloadMaterial(Integer classid,Integer userid,String name,Integer material_user_id) {
        String Ifresult=material_userMapper.checkIfFirsttime(material_user_id);
        if(Ifresult.equals("否")) {
            ApiResponse<List<Material_User>> apiResponse = new ApiResponse<>("0", "success");
            Date date = new Date();
            Material_User material_user = new Material_User();
            material_user.setClass_id(classid);
            material_user.setUser_id(userid);
            material_user.setName(name);
            material_user.setCreate_date_time(date);
            material_user.setModify_date_time(date);
            int result = material_userMapper.downloadMaterial1(material_user_id);
            int result2 = classUserMapper.addExpMaterial(material_user);
            int result3 = material_userMapper.addExpRecord(material_user);
            String jsonResponse = JSON.toJSONString(apiResponse);
            return jsonResponse;
        }
        else {
            ApiResponse<List<Material_User>> apiResponse = new ApiResponse<>("1", "已经下载过此资源");
            String jsonResponse = JSON.toJSONString(apiResponse);
            return jsonResponse;
        }
    }
    public String deleteMaterial(Integer materialId){
        ApiResponse apiResponse;
        Material material=materialMapper.selectByPrimaryKey(materialId);
        String url=material.getUrl();
        List<Material_User> unBrowseList=material_userMapper.selectUnBrowseList(materialId);
        if(unBrowseList.size()!=0 ){
            int result3 = classUserMapper.deleteUpdateBrowse(unBrowseList);
        }
        int result1=materialMapper.deleteByPrimaryKey(materialId);
        int result2=material_userMapper.deleteBymaterialId(materialId);

        if (url != null) {
            FileUtil.deleteDir(new File(MyWebMvcConfigurer.UPLOAD_PATH + "resources/" + url));
        }
        if(result1==1){
            apiResponse = new ApiResponse("0", "删除资源成功");
        }else{
            apiResponse = new ApiResponse("1", "删除资源失败");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }




}
