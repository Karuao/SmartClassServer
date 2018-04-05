package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.ClassUserExp;

public interface ClassUserExpMapper {
    int deleteByPrimaryKey(Integer class_user_exp_id);

    int insert(ClassUserExp record);

    int insertSelective(ClassUserExp record);

    ClassUserExp selectByPrimaryKey(Integer class_user_exp_id);

    int updateByPrimaryKeySelective(ClassUserExp record);

    int updateByPrimaryKey(ClassUserExp record);
}