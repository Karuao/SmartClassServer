package team.fushan.smartclass.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import team.fushan.smartclass.domain.User;

@Mapper
@Component
public interface UserMapper {
    int deleteByPrimaryKey(Integer user_id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer user_id);

    User selectByEmail(String email);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}