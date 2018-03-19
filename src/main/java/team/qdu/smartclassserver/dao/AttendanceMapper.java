package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.Attendance;

public interface AttendanceMapper {
    int deleteByPrimaryKey(Integer attendance_id);

    int insert(Attendance record);

    int insertSelective(Attendance record);

    Attendance selectByPrimaryKey(Integer attendance_id);

    int updateByPrimaryKeySelective(Attendance record);

    int updateByPrimaryKey(Attendance record);
}