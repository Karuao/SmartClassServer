package team.qdu.smartclassserver.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.qdu.smartclassserver.dao.ClassUserMapper;
import team.qdu.smartclassserver.dao.CronMapper;
import team.qdu.smartclassserver.dao.HomeworkAnswerMapper;
import team.qdu.smartclassserver.dao.HomeworkMapper;
import team.qdu.smartclassserver.domain.*;

import java.io.Serializable;
import java.util.*;

@Service
public class HomeworkService {

    @Autowired
    HomeworkMapper homeworkMapper;

    @Autowired
    HomeworkAnswerMapper homeworkAnswerMapper;

    @Autowired
    CronMapper cronMapper;

    @Autowired
    ClassUserMapper classUserMapper;

    public String publishHomework(String title, String detail, Date deadline, String url, int classId) {
        ApiResponse<Void> apiResponse;
        Date date = new Date();
        //插入发布的作业
        HomeworkWithBLOBs homeworkWithBLOBs = new HomeworkWithBLOBs();
        homeworkWithBLOBs.setName(title);
        homeworkWithBLOBs.setDetail(detail);
        homeworkWithBLOBs.setUrl(url);
        homeworkWithBLOBs.setSubmit_num(0);
        homeworkWithBLOBs.setHomework_status("进行中");
        homeworkWithBLOBs.setDeadline(deadline);
        homeworkWithBLOBs.setClass_id(classId);
        homeworkWithBLOBs.setExp((byte) 3);
        homeworkWithBLOBs.setCreate_date_time(date);
        homeworkWithBLOBs.setModify_date_time(date);
        if (homeworkMapper.insert(homeworkWithBLOBs) == 1) {
            apiResponse = new ApiResponse<>("0", "发布作业成功");
        } else {
            apiResponse = new ApiResponse<>("1", "发布作业失败");
        }

        //向homework_answer用户提交作业表插入每个学生的作业记录
        List<ClassUser> classUserList = classUserMapper.selectStudentsByClassId(classId);
        if (classUserList.size() > 0) {
            List<HomeworkAnswer> homeworkAnswerList = new ArrayList<>();
            for (ClassUser classUser : classUserList) {
                HomeworkAnswer homeworkAnswer = new HomeworkAnswer();
                homeworkAnswer.setHomework_id(homeworkWithBLOBs.getHomework_id());
                homeworkAnswer.setUser_id(classUser.getUser_id());
                homeworkAnswer.setIf_submit("否");
                homeworkAnswer.setClass_id(classId);
                homeworkAnswer.setCreate_date_time(date);
                homeworkAnswer.setModify_date_time(date);
                homeworkAnswerList.add(homeworkAnswer);
            }
            homeworkAnswerMapper.initialInsert(homeworkAnswerList);
        }

        //将定时信息存入定时表
        Cron cron = new Cron();
        cron.setHomework_id(homeworkWithBLOBs.getHomework_id());
        cron.setTime(deadline);
        cronMapper.insert(cron);

        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    public String getHomeworkList(int classId, int userId, String userTitle, String requestStatus) {
        ApiResponse apiResponse;
        if ("teacher".equals(userTitle)) {
             apiResponse= new ApiResponse<Homework>("0", "获取作业列表成功");
            if ("进行中".equals(requestStatus)) {
                apiResponse.setObjList(homeworkMapper.selectTeacherHomeworkListByClassIdUnderway(classId));
            } else {
                apiResponse.setObjList(homeworkMapper.selectTeacherHomeworkListByClassIdFinish(classId));
            }
        } else {
            apiResponse= new ApiResponse<HomeworkAnswerWithBLOBs>("0", "获取作业列表成功");
            Map<String, Serializable> paramMap = new HashMap<>();
            paramMap.put("classId", classId);
            paramMap.put("userId", userId);
            if ("进行中".equals(requestStatus)) {
                apiResponse.setObjList(homeworkAnswerMapper.selectStudentHomeworkListByMapUnderway(paramMap));
            } else {
                apiResponse.setObjList(homeworkAnswerMapper.selectStudentHomeworkListByMapFinish(paramMap));
            }
        }

        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    public String changeHomeworkStatus(int homeworkId, String homeworkStatus) {
        ApiResponse<Void> apiResponse;
        HomeworkWithBLOBs homework = new HomeworkWithBLOBs();
        homework.setHomework_id(homeworkId);
        homework.setModify_date_time(new Date());
        if ("进行中".equals(homeworkStatus)) {
            cronMapper.deleteByHomeworkId(homeworkId);
            homework.setHomework_status("评价中");
            homeworkMapper.updateByPrimaryKeySelective(homework);
            apiResponse = new ApiResponse<>("0", "作业已开始评价");
        } else {
            homework.setHomework_status("已结束");
            homeworkMapper.updateByPrimaryKeySelective(homework);
            apiResponse = new ApiResponse<>("0", "作业已结束");
        }

        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    public String getStuHomeworkDetail(int homeworkAnswerId) {
        ApiResponse<HomeworkAnswerWithBLOBs> apiResponse = new ApiResponse<>("0", "");
        apiResponse.setObj(homeworkAnswerMapper.selectDetailByPrimaryKey(homeworkAnswerId));

        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }

    public String submitHomework(int homeworkAnswerId, String detail, String url) {
        ApiResponse<Void> apiResponse;
        HomeworkAnswerWithBLOBs homeworkAnswer = new HomeworkAnswerWithBLOBs();
        homeworkAnswer.setHomework_answer_id(homeworkAnswerId);
        homeworkAnswer.setDetail(detail);
        homeworkAnswer.setUrl(url);
        if (homeworkAnswerMapper.updateByPrimaryKeySelective(homeworkAnswer) > 0) {
            apiResponse = new ApiResponse("0", "作业提交成功");
        } else {
            apiResponse = new ApiResponse("1", "作业提交失败，请稍后再试");
        }

        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }
}
