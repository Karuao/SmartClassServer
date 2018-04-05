package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.Material_User;

import java.util.List;

public interface Material_UserMapper {
    int deleteByPrimaryKey(Integer material_user_id);

    int insert(Material_User record);

    int insertSelective(Material_User record);

    Material_User selectByPrimaryKey(Integer material_user_id);

    int updateByPrimaryKeySelective(Material_User record);

    int updateByPrimaryKey(Material_User record);

    List<Material_User> selectByClassidUserid(Material_User record);

    int deleteBymaterialId(Integer materialId);

    int downloadMaterial1(Integer material_user_id);

    int addExpRecord(Material_User record);

    String checkIfFirsttime(Integer material_user_id);
}