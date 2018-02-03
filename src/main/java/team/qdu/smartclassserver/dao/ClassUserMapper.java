package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.ClassUser;

public interface ClassUserMapper {
    int deleteByPrimaryKey(Integer class_user_id);

    int insert(ClassUser record);

    int insertSelective(ClassUser record);

    ClassUser selectByPrimaryKey(Integer class_user_id);

    int updateByPrimaryKeySelective(ClassUser record);

    int updateByPrimaryKey(ClassUser record);
}