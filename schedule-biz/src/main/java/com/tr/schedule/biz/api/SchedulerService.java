package com.tr.schedule.biz.api;


public interface SchedulerService {
	public void init() throws Exception;

	public void reloadJobs() throws Exception;
}