package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.Inform;
import team.qdu.smartclassserver.domain.Inform_User;

import java.util.List;

public interface InformMapper {
    int deleteByPrimaryKey(Integer inform_id);

    int insert(Inform record);

    int selectUnReadPeople(Integer informid);

    int insertSelective(Inform record);

    Inform selectByPrimaryKey(Integer inform_id);

    int updateByPrimaryKeySelective(Inform record);

    int updateByPrimaryKey(Inform record);

    List<Inform> selectInformByClassId(Integer class_id);

    Inform_User judgeByInformIdUserId(Inform_User record);

    int insertBatch(Inform_User record);

}