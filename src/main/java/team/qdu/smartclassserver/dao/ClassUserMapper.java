package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.ClassUser;

import java.util.List;

public interface ClassUserMapper {
    int deleteByPrimaryKey(Integer class_user_id);

    int insert(ClassUser record);

    int insertSelective(ClassUser record);

    ClassUser selectByPrimaryKey(Integer class_user_id);

    int updateByPrimaryKeySelective(ClassUser record);

    int updateByPrimaryKey(ClassUser record);

    ClassUser selectByClassIdUserId(ClassUser record);

    List<ClassUser> selectStudentsByClassId(int classId);
}