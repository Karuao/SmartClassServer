package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer user_id);

    int insert(User record);

    int insertSelective(User record);

    User selectByAccount(String account);

    User selectByPrimaryKey(Integer user_id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}