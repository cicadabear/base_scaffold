package cn.iutils.common.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.iutils.sys.entity.ScheduleJob;

/**
 * 计划任务执行处 无状态
 * 
 * @author cc
 */
public class QuartzJobFactory implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		TaskUtils.invokMethod(scheduleJob);
	}
}