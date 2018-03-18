package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.Material;

import java.util.List;

public interface MaterialMapper {
    int deleteByPrimaryKey(Integer material_id);

    int insert(Material record);

    int insertSelective(Material record);

    Material selectByPrimaryKey(Integer material_id);

    int updateByPrimaryKeySelective(Material record);

    List<Material> selectByClassid(Integer class_id);

    int updateByPrimaryKey(Material record);

}