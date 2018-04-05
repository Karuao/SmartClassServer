package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.ClassUserExp;

import java.util.List;

public interface ClassUserExpMapper {
    int deleteByPrimaryKey(Integer class_user_exp_id);

    int insert(ClassUserExp record);

    int insertSelective(ClassUserExp record);

    //插入多条记录
    int insertRecordList(List<ClassUserExp> recordList);

    ClassUserExp selectByPrimaryKey(Integer class_user_exp_id);

    int updateByPrimaryKeySelective(ClassUserExp record);

    int updateByPrimaryKey(ClassUserExp record);
}