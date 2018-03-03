package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.Homework;
import team.qdu.smartclassserver.domain.HomeworkAnswer;
import team.qdu.smartclassserver.domain.HomeworkAnswerWithBLOBs;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface HomeworkAnswerMapper {
    int deleteByPrimaryKey(Integer homework_answer_id);

    int insert(HomeworkAnswerWithBLOBs record);

    int insertSelective(HomeworkAnswerWithBLOBs record);

    int initialInsert(List<HomeworkAnswer> recordList);

    List<HomeworkAnswerWithBLOBs> selectByHomeworkId(Integer homework_id);

    List<HomeworkAnswerWithBLOBs> selectStudentHomeworkListByMapUnderway(Map<String, Serializable> map);

    List<HomeworkAnswerWithBLOBs> selectStudentHomeworkListByMapFinish(Map<String, Serializable> map);

    HomeworkAnswerWithBLOBs selectByPrimaryKey(Integer homework_answer_id);

    HomeworkAnswerWithBLOBs selectDetailByPrimaryKey(Integer homework_answer_id);

    int updateByPrimaryKeySelective(HomeworkAnswerWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(HomeworkAnswerWithBLOBs record);

    int updateByPrimaryKey(HomeworkAnswer record);
}