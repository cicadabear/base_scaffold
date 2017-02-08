package cn.iutils.sys.entity;

import cn.iutils.common.BaseEntity;

/**
 * 任务调度
 * 
 * @author MyCode
 * @version 1.0
 */
public class ScheduleJob extends DataEntity<ScheduleJob> {

	private static final long serialVersionUID = 1L;

	private String jobName;// 任务名称
	private String jobGroup;// 任务分组
	private String cron;// cron表达式
	private String beanClass;// 任务执行时调用哪个类的方法 包名+类名
	private String isConcurrent;// 是否有状态
	private String methodName = "run";// 任务调用的方法名

	public static final String STATUS_RUNNING = "1";
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";

	public ScheduleJob() {
		super();
	}

	public ScheduleJob(String id) {
		super(id);
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	public String getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
