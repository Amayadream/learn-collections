package com.amayadream.quartz.serviceImpl;

import com.amayadream.quartz.model.ScheduleJob;
import com.amayadream.quartz.service.ISchedulerService;
import com.amayadream.quartz.util.StringUtil;
import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :  Amayadream
 * @date :  2017.03.09 15:18
 */
@Service
public class SchedulerServiceImpl implements ISchedulerService {

    @Resource
    private MongoTemplate template;

    @Override
    public ScheduleJob find(String jobId) {
        return template.findById(jobId, ScheduleJob.class);
    }

    @Override
    public List<ScheduleJob> list() {
        return template.findAll(ScheduleJob.class);
    }

    @Override
    public ScheduleJob add(ScheduleJob job) {
        job.setJobId(StringUtil.generateGuid());
        template.insert(job);
        return find(job.getJobId());
    }

    @Override
    public int update(ScheduleJob job) {
        Update update = new Update();
        update.set("jobName", job.getJobName());
        update.set("jobGroup", job.getJobGroup());
        update.set("jobStatus", job.getJobStatus());
        update.set("cronExpression", job.getCronExpression());
        update.set("desc", job.getDesc());
        WriteResult r = template.updateFirst(Query.query(Criteria.where("jobId").is(job.getJobId())), update, ScheduleJob.class);
        return r.getN();
    }

    @Override
    public int remove(String jobId) {
        WriteResult r = template.remove(Query.query(Criteria.where("jobId").is(jobId)), ScheduleJob.class);
        return r.getN();
    }
}
