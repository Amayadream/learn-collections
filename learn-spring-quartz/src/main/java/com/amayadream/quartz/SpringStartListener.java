package com.amayadream.quartz;

import com.amayadream.quartz.exec.QuartzJobFactory;
import com.amayadream.quartz.model.ScheduleJob;
import com.amayadream.quartz.service.ISchedulerService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 初始化监听器
 * @author :  Amayadream
 * @date :  2016.12.19 22:12
 */
@Component
public class SpringStartListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired(required = false)
    private SchedulerFactoryBean schedulerFactoryBean;

    @Resource
    private ISchedulerService schedulerService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            List<ScheduleJob> jobs = schedulerService.list();
            for (ScheduleJob job : jobs) {
                if ("1".equals(job.getJobStatus())){    //初始化正常的任务
                    TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
                    try {
                        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);   //获取trigger
                        if (null == trigger) {  //如果Trigger不存在, 则创建
                            JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(job.getJobName(), job.getJobGroup()).build();
                            jobDetail.getJobDataMap().put("scheduleJob", job);
                            //表达式调度构建器
                            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                            //按新的cronExpression表达式构建一个新的trigger
                            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
                            scheduler.scheduleJob(jobDetail, trigger);
                        } else {    //如果Trigger已存在, 则更新
                            //表达式调度构建器
                            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                            //按新的cronExpression表达式重新构建trigger
                            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                            //按新的trigger重新设置job执行
                            scheduler.rescheduleJob(triggerKey, trigger);
                        }
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
