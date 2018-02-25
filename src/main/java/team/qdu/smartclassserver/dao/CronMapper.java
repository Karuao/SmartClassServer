package team.qdu.smartclassserver.dao;

import team.qdu.smartclassserver.domain.Cron;

import java.util.Date;
import java.util.List;

public interface CronMapper {
    int deleteByPrimaryKey(Integer cron_id);

    int deleteFinishedCrons(List recordList);

    int insert(Cron record);

    int insertSelective(Cron record);

    Cron selectByPrimaryKey(Integer cron_id);

    int updateByPrimaryKeySelective(Cron record);

    int updateByPrimaryKey(Cron record);

    List<Cron> selectByTime(Date date);
}