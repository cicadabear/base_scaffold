package cn.iutils.common.task;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import cn.iutils.common.utils.JStringUtils;
import cn.iutils.sys.entity.ScheduleJob;

public class TaskUtils {

	public final static Logger logger = Logger.getLogger(TaskUtils.class);

	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * 
	 * @param scheduleJob
	 */
	public static void invokMethod(ScheduleJob scheduleJob) {
		Object object = null;
		Class<?> clazz = null;
		if (JStringUtils.isNotBlank(scheduleJob.getBeanClass())) {
			try {
				clazz = Class.forName(scheduleJob.getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		}
		if (object == null) {
			logger.error("任务名称 = [" + scheduleJob.getJobName()
					+ "]---------------未启动成功，请检查是否配置正确！！！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
		} catch (NoSuchMethodException e) {
			logger.error("任务名称 = [" + scheduleJob.getJobName()
					+ "]---------------未启动成功，方法名设置错误！！！");
		} catch (SecurityException e) {
			logger.error(e.getMessage(), e);
		}
		if (method != null) {
			try {
				method.invoke(object);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		logger.debug("任务名称 = [" + scheduleJob.getJobName() + "]----------启动成功");
	}
}
