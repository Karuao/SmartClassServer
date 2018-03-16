package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.Attendance_user;

public interface Attendance_userMapper {
    int deleteByPrimaryKey(Integer attendance_user_id);

    int insert(Attendance_user record);

    int insertSelective(Attendance_user record);

    Attendance_user selectByPrimaryKey(Integer attendance_user_id);

    int updateByPrimaryKeySelective(Attendance_user record);

    int updateByPrimaryKey(Attendance_user record);
}