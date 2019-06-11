package com.tr.schedule.biz.impl;

import com.tr.schedule.biz.api.SchedulerService;
import com.tr.schedule.biz.factory.QuartzFactory;
import com.tr.schedule.biz.job.QuartzJob;
import com.tr.schedule.dal.mybatis.mapper.SchedulerConfigPOMapper;
import com.tr.schedule.dal.mybatis.model.SchedulerConfigPO;
import org.quartz.JobKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SchedulerServiceImpl implements SchedulerService {
	@Resource
	private SchedulerConfigPOMapper schedulerConfigPOMapper;

	@Override
	public void init() throws Exception {
		QuartzFactory quartzFactory = QuartzFactory.init();
		loadJobs(quartzFactory);
	}

	private void loadJobs(QuartzFactory quartzFactory) throws Exception {
		SchedulerConfigPO schedulerConfigSearchPO = new SchedulerConfigPO();
		schedulerConfigSearchPO.setStatus("1");
		List<SchedulerConfigPO> schedulerConfigPOList = schedulerConfigPOMapper.select(schedulerConfigSearchPO);
		for(int i=0; i<schedulerConfigPOList.size(); i++){
			SchedulerConfigPO schedulerConfigPO = schedulerConfigPOList.get(i);
			quartzFactory.addJob(new QuartzJob(), schedulerConfigPO.getId(), schedulerConfigPO.getJobName(), schedulerConfigPO.getJobDesc(), schedulerConfigPO.getCronRule(), schedulerConfigPO.getInvokeUrl());
		}
	}

	@Override
	public void reloadJobs() throws Exception {
		QuartzFactory quartzFactory = QuartzFactory.init();
		quartzFactory.removeJobs();

		loadJobs(quartzFactory);
	}

}
