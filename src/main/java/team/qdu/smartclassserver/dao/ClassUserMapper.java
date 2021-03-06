package team.qdu.smartclassserver.dao;

import org.apache.ibatis.annotations.Param;
import team.qdu.smartclassserver.domain.ClassUser;
import team.qdu.smartclassserver.domain.Inform_User;
import team.qdu.smartclassserver.domain.Material_User;

import java.util.List;
import java.util.Map;

public interface ClassUserMapper {
    int deleteByPrimaryKey(Integer class_user_id);

    int insert(ClassUser record);

    int insertSelective(ClassUser record);

    ClassUser selectByPrimaryKey(Integer class_user_id);

    ClassUser selectMemberInfoByPrimaryKey(Integer class_user_id);

    ClassUser selectByClassIdUserId(ClassUser record);

    List<ClassUser> selectStudentsByClassId(int classId);

    List<ClassUser> selectClassMembersByClassId(Integer class_id);

    ClassUser selectMyClassByClassIdUserId(@Param("class_id") int class_id, @Param("user_id") int user_id);

    ClassUser selectByClassIdAndUserId(Integer class_id, Integer user_id);

    List<ClassUser> selectJoinedClassesByUserId(Integer user_id);

    int updateByPrimaryKeySelective(ClassUser record);

    int updateByClassIdSelective(ClassUser record);

    int updateByClassIdUserIdSelective(ClassUser record);

    int updateByPrimaryKey(ClassUser record);

    int updateIfInClassByClassIdUserId(@Param("class_id") int class_id, @Param("user_id") int user_id);

    //根据class_idh和user_id给class_user表中记录增加exp
    int addExpByClassIdUserId(Map<String, Object> map);

    //根据class_idh和user_id给class_user表中多条记录增加exp
    int addExpsByClassIdUserId(List recordList);

    int deleteUpdateBrowse(List recordList);

    int updateBrowseNum(Material_User record);

    int addExpMaterial(Material_User record);

    int addExpInform(Inform_User record);
}