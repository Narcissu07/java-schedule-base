package com.tr.schedule.biz.factory;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjie on 2018/5/15.
 */
public class QuartzFactory {
    private static QuartzFactory instance;
    private static Scheduler scheduler;
    private static List<String> jobNameList = new ArrayList<String>();

    public static synchronized QuartzFactory init() throws Exception {
        if(instance == null){
            instance = new QuartzFactory();
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        }
        return instance;
    }

    public void addJob(Job job, Integer jobId, String jobName, String jobDesc, String cron, String url) throws Exception {
        jobNameList.add(jobName);

        //创建工作明细，用于存放 变量
        JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity(jobName).build();
        jobDetail.getJobDataMap().put("jobId", jobId);
        jobDetail.getJobDataMap().put("jobName", jobName);
        jobDetail.getJobDataMap().put("jobDesc", jobDesc);
        jobDetail.getJobDataMap().put("cron", cron);
        jobDetail.getJobDataMap().put("url", url);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .forJob(jobName)
                .build();


        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    public void removeJobs() throws Exception {
        for(int i=0; i<jobNameList.size(); i++){
            JobKey jobKey =new JobKey(jobNameList.get(i));
            scheduler.deleteJob(jobKey);
        }
        jobNameList = new ArrayList<String>();
    }
}
