package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.Homework;

import java.util.List;

public interface HomeworkMapper {
    int deleteByPrimaryKey(Integer homework_id);

    int insert(Homework record);

    int insertSelective(Homework record);

    Homework selectByPrimaryKey(Integer homework_id);

    int updateByPrimaryKeySelective(Homework record);

    int updateByPrimaryKeyWithBLOBs(Homework record);

    int updateByPrimaryKey(Homework record);

    int updateHomeworkStatus(List recordList);
}