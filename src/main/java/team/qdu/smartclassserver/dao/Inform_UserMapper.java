package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.Inform_User;

import java.util.List;

public interface Inform_UserMapper {
    int deleteByPrimaryKey(Integer inform_user_id);

    int deleteByInformId(Integer inform_id);


    int insert(Inform_User record);

    int insertSelective(Inform_User record);

    Inform_User selectByPrimaryKey(Integer inform_user_id);

    List<Inform_User> selectInformByClassIdUserId(Inform_User record);

    List<Inform_User> selectInformByInformId(Integer inform_id);

    List<Integer> selectReadByInformId(Integer inform_id);

    List<Integer> selectUnReadByInformId(Integer inform_id);

    int clickUpdate1(Inform_User record);

    int clickUpdate2(Inform_User record);

    int clickUpdate3(Inform_User record);

    int deleteUpdate(Inform_User record);

    int updateByPrimaryKeySelective(Inform_User record);

    int updateByPrimaryKey(Inform_User record);
}