package com.amayadream.quartz.service;

import com.amayadream.quartz.model.ScheduleJob;

import java.util.List;

/**
 * @author :  Amayadream
 * @date :  2017.03.09 15:18
 */
public interface ISchedulerService {

    ScheduleJob find(String jobId);

    List<ScheduleJob> list();

    ScheduleJob add(ScheduleJob job);

    int update(ScheduleJob job);

    int remove(String jobId);

}
