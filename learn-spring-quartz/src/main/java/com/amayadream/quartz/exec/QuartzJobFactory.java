package com.amayadream.quartz.exec;

import com.amayadream.quartz.model.ScheduleJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Job工厂类
 * @author :  Amayadream
 * @date :  2017.03.09 14:05
 */
public class QuartzJobFactory implements Job{

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("任务成功运行");
        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
        System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]");
        //根据name 与 group组成的唯一标识来判别该执行何种操作……
    }

}
