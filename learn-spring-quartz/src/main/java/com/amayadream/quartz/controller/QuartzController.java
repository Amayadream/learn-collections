package com.amayadream.quartz.controller;

import com.amayadream.quartz.exec.QuartzJobFactory;
import com.amayadream.quartz.model.ScheduleJob;
import com.amayadream.quartz.service.ISchedulerService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

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
     * 任务列表首页
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model) {
        List<ScheduleJob> list = schedulerService.list();
        model.addAttribute("jobs", list);
        return "index";
    }

    /**
     * 跳转至添加页面
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "add";
    }

    /**
     * 跳转至编辑页面
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(String jobId, Model model) {
        ScheduleJob job = schedulerService.find(jobId);
        if (job == null)
            model.addAttribute("error", "没有查询到该任务, 请确认后再操作!");
        else
            model.addAttribute("job", job);
        return "update";
    }

    /**
     * 添加任务
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(ScheduleJob job, RedirectAttributes attributes) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        //获取触发器标识
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        //获取触发器trigger
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger != null) {  //任务已经存在
            attributes.addFlashAttribute("error", "该任务已经存在!");
        } else {    //创建任务
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
            scheduler.pauseTrigger(triggerKey);     //默认暂停状态
            //把任务插入数据库
            job.setJobStatus("0");
            ScheduleJob r = schedulerService.add(job);
            attributes.addFlashAttribute("message", "任务[" + job.getJobName() + "]添加成功!");
        }
        return "redirect:/quartz";
    }

    /**
     * 更新任务
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ScheduleJob job, RedirectAttributes attributes) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        ScheduleJob j = schedulerService.find(job.getJobId());
        if (j == null) {
            attributes.addFlashAttribute("error", "数据库中没有找到该任务!");
        } else {
            //获取触发器标识
            TriggerKey triggerKey = TriggerKey.triggerKey(j.getJobName(), j.getJobGroup());
            //获取触发器trigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {  //TODO 如果队列里没有, 应该直接加入
                attributes.addFlashAttribute("error", "任务不存在, 请确认后再操作!");
            } else {    //Trigger已存在，那么更新相应的定时设置
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                //按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder)
                        .build();
                scheduler.rescheduleJob(triggerKey, trigger);   //按新的trigger重新设置job执行
                if ("0".equals(job.getJobStatus())){
                    scheduler.pauseTrigger(triggerKey);     //暂停状态
                }
                //更新数据库中的任务
                int result = schedulerService.update(job);
                if (result > 0)
                    attributes.addFlashAttribute("message", "任务[" + job.getJobName() + "]已更新!");
                else
                    attributes.addFlashAttribute("error", "任务[" + job.getJobName() + "]更新出错, 请重新尝试!");
            }
        }
        return "redirect:/quartz";
    }

    /**
     * 暂停任务
     */
    @RequestMapping(value = "/pause", method = RequestMethod.GET)
    public String pause(String jobId, RedirectAttributes attributes) throws SchedulerException {
        ScheduleJob job = schedulerService.find(jobId);
        if (job == null) {
            attributes.addFlashAttribute("error", "数据库中没有找到该任务!");
        } else {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //获取触发器标识
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            //获取触发器trigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {  //TODO 如果队列里没有, 应该直接加入并暂停
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
                scheduler.pauseTrigger(triggerKey);     //默认暂停状态
                job.setJobStatus("0");
                schedulerService.update(job);
                attributes.addFlashAttribute("message", "检测到当前队列里没有任务[" + job.getJobName() + "], 已自动加入并暂停!");
            } else {
                JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
                scheduler.pauseJob(jobKey);
                job.setJobStatus("0");
                schedulerService.update(job);
                attributes.addFlashAttribute("message", "任务[" + job.getJobName() + "]暂停成功!");
            }
        }
        return "redirect:/quartz";
    }

    /**
     * 恢复任务
     */
    @RequestMapping(value = "/resume")
    public String resume(String jobId, RedirectAttributes attributes) throws SchedulerException {
        ScheduleJob job = schedulerService.find(jobId);
        if (job == null) {
            attributes.addFlashAttribute("error", "没有找到该任务!");
        } else {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //获取触发器标识
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            //获取触发器trigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) { //TODO 如果队列里没有, 应该直接加入
                JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
                        .withIdentity(job.getJobName(), job.getJobGroup())
                        .build();
                jobDetail.getJobDataMap().put("scheduleJob", job);
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                //按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(job.getJobName(), job.getJobGroup())
                        .withSchedule(scheduleBuilder)
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
                job.setJobStatus("1");
                schedulerService.update(job);
                attributes.addFlashAttribute("message", "检测到当前队列里没有任务[" + job.getJobName() + "], 已自动加入!");
            } else {
                JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
                scheduler.resumeJob(jobKey);
                job.setJobStatus("1");
                schedulerService.update(job);
                attributes.addFlashAttribute("message", "任务[" + job.getJobName() + "]恢复成功!");
            }
        }
        return "redirect:/quartz";
    }

    /**
     * 删除任务
     */
    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public String deleteQuartz(String jobId, RedirectAttributes attributes) throws SchedulerException {
        ScheduleJob job = schedulerService.find(jobId);
        if (job == null) {
            attributes.addFlashAttribute("error", "没有找到该任务!");
        } else {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
            scheduler.deleteJob(jobKey);
            schedulerService.remove(jobId);
            attributes.addFlashAttribute("message", "任务[" + job.getJobName() + "]删除成功!");
        }
        return "redirect:/quartz";
    }

}
