package team.qdu.smartclassserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team.qdu.smartclassserver.dao.CronMapper;
import team.qdu.smartclassserver.dao.HomeworkMapper;
import team.qdu.smartclassserver.domain.Cron;
import team.qdu.smartclassserver.domain.Homework;
import team.qdu.smartclassserver.util.PushUtil;

import java.util.Date;
import java.util.List;

/**
 * 定时任务
 */

@Component
public class CronJob {

    public final static long ONE_Minute = 60 * 1000;

    @Autowired
    CronMapper cronMapper;

    @Autowired
    HomeworkMapper homeworkMapper;

    List<Cron> cronList;

    Homework homework;

    Date currentDate;

//    @Scheduled(fixedDelay = ONE_Minute)
//    public void fixedDelayJob() {
//        currentDate = new Date();
//        cronList = cronMapper.selectByTime(currentDate);
//        if (!cronList.isEmpty()) {
//            //将cronList中homework_id对应的记录状态更新为评价中
//            homeworkMapper.updateHomeworkStatus(cronList);
//            for (Cron cron:
//                    cronList) {
//                System.out.print(cron.getHomework_id() + " ");
//            }
//            System.out.println("fixed进入评价状态");
//            cronMapper.deleteFinishedCrons(cronList);
//        }
//    }
//
//    @Scheduled(fixedRate =  ONE_Minute)
//    public void fixedRateJob() {
//        System.out.println(new Date() + " >>fixedRate执行...");
//    }

//    @Scheduled(cron = "0 * * * * ?")
//    public void cronJob1() {
//        PushUtil.testSendPushWithCustomConfig();
//    }

    //每隔一小时执行一次
    @Scheduled(cron = "0 0 * * * ?")
    public void cronJob() {
//        PushUtil.testSendPushWithCustomConfig();
        currentDate = new Date();
        System.out.println(currentDate + "执行");
        cronList = cronMapper.selectByTime(currentDate);
        if (!cronList.isEmpty()) {
            //将cronList中homework_id对应的记录状态更新为评价中
            homeworkMapper.updateHomeworkStatus(cronList);
            cronMapper.deleteFinishedCrons(cronList);
            for (Cron cron: cronList) {
                System.out.print(cron.getHomework_id() + " ");
            }
            System.out.println("进入评价状态");
        }
    }
}
