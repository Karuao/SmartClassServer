package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.Class;

import java.util.List;

public interface ClassMapper {
    int deleteByPrimaryKey(Integer class_id);

    int insert(Class record);

    int insertSelective(Class record);

    Class selectByPrimaryKey(Integer class_id);

    int updateByPrimaryKeySelective(Class record);

    int updateByPrimaryKey(Class record);

    List<Class> selectJoinedClassesByUserId(Integer user_id);

    List<Class> getClass(Integer user_id);

    List<Integer> selectUserIdByClassId(Integer class_id);

    int updateByClassId(Integer classId);

    int updateMaterialByClassId(Integer classId);

    Class selectJoinClassByClassId(Integer class_id);

}