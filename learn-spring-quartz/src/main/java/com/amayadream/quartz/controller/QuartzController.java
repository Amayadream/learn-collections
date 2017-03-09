package com.amayadream.quartz.controller;

import com.alibaba.fastjson.JSON;
import com.amayadream.quartz.QuartzJobFactory;
import com.amayadream.quartz.model.ScheduleJob;
import com.amayadream.quartz.service.ISchedulerService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author :  Amayadream
 * @date :  2017.03.09 14:07
 */
@Controller
@RequestMapping(value = "/quartz")
public class QuartzController {

    @Autowired(required = false)
    private SchedulerFactoryBean schedulerFactoryBean;

    @Resource
    private ISchedulerService schedulerService;

    /**
     * 任务创建与更新(未存在的就创建，已存在的则更新)
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String updateQuartz(ScheduleJob job) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            if (job != null) {
                //获取触发器标识
                TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
                //获取触发器trigger
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                if (trigger == null) {//不存在任务
                    //创建任务
                    JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
                            .withIdentity(job.getJobName(), job.getJobGroup())
                            .build();
                    jobDetail.getJobDataMap().put("scheduleJob", job);
                    //表达式调度构建器
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                            .getCronExpression());
                    //按新的cronExpression表达式构建一个新的trigger
                    trigger = TriggerBuilder.newTrigger()
                            .withIdentity(job.getJobName(), job.getJobGroup())
                            .withSchedule(scheduleBuilder)
                            .build();
                    scheduler.scheduleJob(jobDetail, trigger);
                    //把任务插入数据库
                    ScheduleJob r = schedulerService.add(job);
                    return JSON.toJSONString(r);
                } else {//存在任务
                    // Trigger已存在，那么更新相应的定时设置
                    //表达式调度构建器
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                            .getCronExpression());
                    //按新的cronExpression表达式重新构建trigger
                    trigger = trigger.getTriggerBuilder()
                            .withIdentity(triggerKey)
                            .withSchedule(scheduleBuilder)
                            .build();
                    //按新的trigger重新设置job执行
                    scheduler.rescheduleJob(triggerKey, trigger);
                    //更新数据库中的任务
                    int result = schedulerService.update(job);
                    if (result == 1) {
                        return "更新成功!";
                    } else {
                        return "更新失败!";
                    }
                }
            } else {
                return "请输入参数!";
            }
        } catch (SchedulerException e) {
            return "错误: " + e.getMessage();
        }
    }

    /**
     * 暂停任务
     */
    @RequestMapping(value = "/pause", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String pauseQuartz(ScheduleJob scheduleJob) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "暂停成功!";
    }

    /**
     * 恢复任务
     */
    @RequestMapping(value = "/resume", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String resumeQuartz(ScheduleJob scheduleJob) {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "恢复成功!";
    }

    /**
     * 删除任务
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String deleteQuartz(ScheduleJob scheduleJob) {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "删除成功!";
    }

}
