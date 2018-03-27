package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.Attendance_user;

import java.util.List;

public interface Attendance_userMapper {
    int deleteByPrimaryKey(Integer attendance_user_id);

    int deleteByAttendanceId(Integer attendance_id);

    int insert(Attendance_user record);

    int insertSelective(Attendance_user record);

    Attendance_user selectByPrimaryKey(Integer attendance_user_id);

    Attendance_user selectByUserIdAndAttendanceId(Integer user_id,Integer attendance_Id);

    List<Attendance_user> selectAttendanceUserByUserIdAndClassId(Integer user_id,Integer class_id);

    List<Attendance_user> selectByAttendanceId(Integer attendance_id);

    int updateByPrimaryKeySelective(Attendance_user record);

    int updateByPrimaryKey(Attendance_user record);
}