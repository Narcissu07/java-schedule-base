package com.tr.schedule.dal.mybatis.model;

import javax.persistence.*;

@Table(name = "scheduler_config")
public class SchedulerConfigPO {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 任务名
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * 任务描述
     */
    @Column(name = "job_desc")
    private String jobDesc;

    /**
     * cron调度规则
     */
    @Column(name = "cron_rule")
    private String cronRule;

    /**
     * 调用接口url
     */
    @Column(name = "invoke_url")
    private String invokeUrl;

    /**
     * 1:生效；0：不生效
     */
    private String status;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取任务名
     *
     * @return job_name - 任务名
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 设置任务名
     *
     * @param jobName 任务名
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * 获取任务描述
     *
     * @return job_desc - 任务描述
     */
    public String getJobDesc() {
        return jobDesc;
    }

    /**
     * 设置任务描述
     *
     * @param jobDesc 任务描述
     */
    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    /**
     * 获取cron调度规则
     *
     * @return cron_rule - cron调度规则
     */
    public String getCronRule() {
        return cronRule;
    }

    /**
     * 设置cron调度规则
     *
     * @param cronRule cron调度规则
     */
    public void setCronRule(String cronRule) {
        this.cronRule = cronRule;
    }

    /**
     * 获取调用接口url
     *
     * @return invoke_url - 调用接口url
     */
    public String getInvokeUrl() {
        return invokeUrl;
    }

    /**
     * 设置调用接口url
     *
     * @param invokeUrl 调用接口url
     */
    public void setInvokeUrl(String invokeUrl) {
        this.invokeUrl = invokeUrl;
    }

    /**
     * 获取1:生效；0：不生效
     *
     * @return status - 1:生效；0：不生效
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置1:生效；0：不生效
     *
     * @param status 1:生效；0：不生效
     */
    public void setStatus(String status) {
        this.status = status;
    }
}