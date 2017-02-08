package cn.iutils.sys.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import cn.iutils.common.task.QuartzJobFactory;
import cn.iutils.common.task.QuartzJobFactoryDisallowConcurrentExecution;
import cn.iutils.sys.dao.ScheduleJobDao;
import cn.iutils.sys.entity.ScheduleJob;

/**
 * 任务调度 Service层
 * 
 * @author MyCode
 * @version 1.0
 */
@SuppressWarnings("restriction")
@Service
@Transactional(readOnly = true)
public class ScheduleJobService extends CrudService<ScheduleJobDao, ScheduleJob> {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	/**
	 * 更改任务状态
	 * 
	 * @param scheduleJob
	 * @return
	 */
	@Transactional(readOnly = false)
	public int changeJobStatus(ScheduleJob scheduleJob) {
		try {
			if (ScheduleJob.STATUS_NOT_RUNNING.equals(scheduleJob.getStatus())) {
				deleteJob(scheduleJob);
			} else if (ScheduleJob.STATUS_RUNNING.equals(scheduleJob.getStatus())) {
				addJob(scheduleJob);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return dao.changeJobStatus(scheduleJob);
	}

	/**
	 * 添加任务
	 * 
	 * @param job
	 * @throws SchedulerException
	 */
	@SuppressWarnings("unchecked")
	public void addJob(ScheduleJob job) throws SchedulerException {
		if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getStatus())) {
			return;
		}
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			Class<?> clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;
			JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put("scheduleJob", job);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	/**
	 * 初始化，动态定时器
	 * 
	 * @throws Exception
	 */
	@PostConstruct
	public void init() throws Exception {
		// 这里获取任务信息数据
		List<ScheduleJob> jobList = dao.findList(null);
		for (ScheduleJob job : jobList) {
			addJob(job);
		}
	}

	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Transactional(readOnly = false)
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
		dao.changeJobStatus(scheduleJob);
	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Transactional(readOnly = false)
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
		dao.changeJobStatus(scheduleJob);
	}

	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 更新job时间表达式
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron());
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
		scheduler.rescheduleJob(triggerKey, trigger);
	}

}
