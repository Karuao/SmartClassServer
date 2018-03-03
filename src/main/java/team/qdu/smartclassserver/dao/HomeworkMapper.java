package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.Homework;
import team.qdu.smartclassserver.domain.HomeworkWithBLOBs;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface HomeworkMapper {
    int deleteByPrimaryKey(Integer homework_id);

    int insert(Homework record);

    int insertSelective(Homework record);

    HomeworkWithBLOBs selectByPrimaryKey(Integer homework_id);

    List<Homework> selectStudentHomeworkListByMapUnderway(Map<String, Serializable> map);

    List<Homework> selectStudentHomeworkListByMapFinish(Map<String, Serializable> map);

    List<Homework> selectTeacherHomeworkListByClassIdUnderway(int classId);

    List<Homework> selectTeacherHomeworkListByClassIdFinish(int classId);

    int updateByPrimaryKeySelective(Homework record);

    int updateByPrimaryKeyWithBLOBs(Homework record);

    int updateByPrimaryKey(Homework record);

    int updateHomeworkStatus(List recordList);
}