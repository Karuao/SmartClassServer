package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.HomeworkAnswer;
import team.qdu.smartclassserver.domain.HomeworkAnswerWithBLOBs;

import java.util.List;

public interface HomeworkAnswerMapper {
    int deleteByPrimaryKey(Integer homework_answer_id);

    int insert(HomeworkAnswerWithBLOBs record);

    int insertSelective(HomeworkAnswerWithBLOBs record);

    int initialInsert(List<HomeworkAnswer> recordList);

    HomeworkAnswerWithBLOBs selectByPrimaryKey(Integer homework_answer_id);

    int updateByPrimaryKeySelective(HomeworkAnswerWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(HomeworkAnswerWithBLOBs record);

    int updateByPrimaryKey(HomeworkAnswer record);
}