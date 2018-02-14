package team.qdu.smartclassserver.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.qdu.smartclassserver.dao.ClassUserMapper;
import team.qdu.smartclassserver.dao.CronMapper;
import team.qdu.smartclassserver.dao.HomeworkAnswerMapper;
import team.qdu.smartclassserver.dao.HomeworkMapper;
import team.qdu.smartclassserver.domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public String publishHomework(String title, Date deadline, String url, int classId) {
        ApiResponse<Void> apiResponse;
        Date date = new Date();
        //插入发布的作业
        Homework homework = new Homework();
        homework.setName(title);
        homework.setDeadline(deadline);
        homework.setUrl(url);
        homework.setHomework_status("进行中");
        homework.setClass_id(classId);
        homework.setExp((byte) 0);
        homework.setCreate_date_time(date);
        homework.setModify_date_time(date);
        if (homeworkMapper.insert(homework) == 1) {
            apiResponse = new ApiResponse<>("0", "发布作业成功");
        } else {
            apiResponse = new ApiResponse<>("1", "发布作业失败");
        }

        //向homework_answer用户提交作业表插入每个学生的作业记录
        List<ClassUser> classUserList = classUserMapper.selectStudentsByClassId(classId);
        List<HomeworkAnswer> homeworkAnswerList = new ArrayList<>();
        for (ClassUser classUser : classUserList) {
            HomeworkAnswer homeworkAnswer = new HomeworkAnswer();
            homeworkAnswer.setHomework_id(homework.getHomework_id());
            homeworkAnswer.setUser_id(classUser.getUser_id());
            homeworkAnswer.setIf_submit("否");
            homeworkAnswer.setClass_id(classId);
            homeworkAnswer.setCreate_date_time(date);
            homeworkAnswer.setModify_date_time(date);
            homeworkAnswerList.add(homeworkAnswer);
        }
        homeworkAnswerMapper.initialInsert(homeworkAnswerList);

        //将定时信息存入定时表
        Cron cron = new Cron();
        cron.setHomework_id(homework.getHomework_id());
        cron.setTime(date);
        cronMapper.insert(cron);

        String jsonResponse = new Gson().toJson(apiResponse);
        return jsonResponse;
    }
}
