package com.tr.schedule.biz.job;

import com.alibaba.fastjson.JSON;
import com.tr.common.utils.HttpClientUtil;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yangjie on 2018/5/15.
 */
public class QuartzJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(QuartzJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        Integer jobId = (Integer) jobDetail.getJobDataMap().get("jobId");
        String jobName = (String) jobDetail.getJobDataMap().get("jobName");
        String jobDesc = (String) jobDetail.getJobDataMap().get("jobDesc");
        String cron = (String) jobDetail.getJobDataMap().get("cron");
        String url = (String) jobDetail.getJobDataMap().get("url");

        logger.info("开始执行任务，jobId:{}, jobName:{}, jobDesc:{}, cron:{}, url:{}", new Object[]{jobId, jobName, jobDesc, cron, url});

        try {
            HttpClientUtil.postBody(url, "");
        } catch (Exception e) {
            logger.error("调用接口错误：", e);
        }
    }

}