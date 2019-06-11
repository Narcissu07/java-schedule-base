package com.tr.schedule.api.controller;

import com.tr.schedule.biz.api.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/schedule")
public class SchedulerController {
	private static final Logger logger = LoggerFactory.getLogger(SchedulerController.class);

	@Resource
	private SchedulerService schedulerService;

	@RequestMapping(value = "/reloadJobs", method = RequestMethod.GET)
	@ResponseBody
	public void reloadJobs() throws Exception {
		schedulerService.reloadJobs();
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	@ResponseBody
	public void test() throws Exception {
		logger.info("---------------------测试任务开始执行");
	}
}