package team.qdu.smartclassserver.service;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.qdu.smartclassserver.dao.*;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.domain.Inform;
import team.qdu.smartclassserver.domain.Inform_User;
import team.qdu.smartclassserver.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InformService {
    @Autowired
    InformMapper informMapper;
    @Autowired
    ClassMapper classMapper;
    @Autowired
    Inform_UserMapper inform_userMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ClassUserMapper classUserMapper;
    public String getInform(Integer classid) {
        ApiResponse<List<Inform>> apiResponse = new ApiResponse<>("0", "success");
        apiResponse.setObjList(informMapper.selectInformByClassId(classid));
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }
    public String  getReadPeople(Integer informId){
        ApiResponse<List<User>> apiResponse = new ApiResponse<>("0", "success");
        List<Integer> userIdList=inform_userMapper.selectReadByInformId(informId);
        List<User> userList=new ArrayList<>();
        for(int i=0;i<userIdList.size();i++){
            int id=userIdList.get(i);
            userList.add(userMapper.selectByPrimaryKey(id));
        }
        apiResponse.setObjList(userList);
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }
    public String  getUnReadPeople(Integer informId){
        ApiResponse<List<User>> apiResponse = new ApiResponse<>("0", "success");
        List<Integer> userIdList=inform_userMapper.selectUnReadByInformId(informId);
        List<User> userList=new ArrayList<>();
        for(int i=0;i<userIdList.size();i++){
            int id=userIdList.get(i);
            userList.add(userMapper.selectByPrimaryKey(id));
        }
        apiResponse.setObjList(userList);
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }
    public String  getUnReadNum(Integer informId){
        String unreadNum=String.valueOf(informMapper.selectUnReadPeople(informId));
        ApiResponse<Inform> apiResponse = new ApiResponse<>("0", unreadNum);
        apiResponse.setObj(informMapper.selectByPrimaryKey(informId));
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }
    public String getUserInform(Integer classid,Integer userid) {
        ApiResponse<List<Inform_User>> apiResponse = new ApiResponse<>("0", "success");
        Inform_User inform_user=new Inform_User();
        inform_user.setClass_id(classid);
        inform_user.setUser_id(userid);
        apiResponse.setObjList(inform_userMapper.selectInformByClassIdUserId(inform_user));
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }
    public String judgeClicked(int informid,int userid){
        ApiResponse<Void> apiResponse;

        Inform_User inform_user=new Inform_User();
        inform_user.setInform_id(informid);
        inform_user.setUser_id(userid);
        Inform_User Clicked=informMapper.judgeByInformIdUserId(inform_user);
        if(Clicked.getIf_read().equals("否")){
            apiResponse = new ApiResponse<>("0", "未读");
        }
        else {
            apiResponse = new ApiResponse<>("0", "已读");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }
    public String ClickInform(int informuserId,int classid,int userid){
        ApiResponse<Void> apiResponse;
        Inform_User inform_user=new Inform_User();
        inform_user=inform_userMapper.selectByPrimaryKey(informuserId);
        if(inform_user.getIf_read().equals("否")) {
            int result1 = inform_userMapper.clickUpdate1(inform_user);
            int result2 = inform_userMapper.clickUpdate2(inform_user);
            int result3 = inform_userMapper.clickUpdate3(inform_user);
            int result4 = classUserMapper.addExpInform(inform_user);
            int result5=inform_userMapper.addExpRecord(inform_user);
            apiResponse = new ApiResponse("0", "该信息未读");
        }
        else {
            apiResponse = new ApiResponse("0", "该信息已读");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;

    }
    public String deleteInform(Integer informId){
        ApiResponse apiResponse;
        List<Inform_User> list=inform_userMapper.selectInformByInformId(informId);
        int result=informMapper.deleteByPrimaryKey(informId);
        for(int i=0;i<list.size();i++){
            Inform_User inform_user=list.get(i);
            if(inform_user.getIf_read().equals("否")){
                int result3=inform_userMapper.deleteUpdate(inform_user);
            }
        }
        int result2=inform_userMapper.deleteByInformId(informId);
        if(result==1){
            apiResponse = new ApiResponse("0", "删除通知成功");
        }else{
            apiResponse = new ApiResponse("1", "删除通知失败");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }

    public String createInform(Integer classId, String detail) {
        ApiResponse apiResponse;
        Date now=new Date();
        Inform inform=new Inform();
        inform.setClass_id(classId);
        inform.setDetail(detail);
        inform.setCreate_date_time(now);
        inform.setModify_date_time(now);
        int result=informMapper.insert(inform);
        int result2=classMapper.updateByClassId(classId);
        Inform_User inform_user=new Inform_User();
        inform_user.setInform_id(inform.getInform_id());
        inform_user.setClass_id(classId);
        inform_user.setDetail(detail);
        inform_user.setCreate_date_time(now);
        inform_user.setModify_date_time(now);
        List<Integer> userIdlist = classMapper.selectUserIdByClassId(classId);
        for(int i = 0;i < userIdlist.size(); i ++){
            int userid=userIdlist.get(i);
            inform_user.setUser_id(userid);
            int msg=informMapper.insertBatch(inform_user);
        }


        if(result==1){
            apiResponse = new ApiResponse("0", "创建通知成功");
        }else{
            apiResponse = new ApiResponse("1", "创建通知失败");
        }
        String jsonResponse = JSON.toJSONString(apiResponse);
        return jsonResponse;
    }
}
