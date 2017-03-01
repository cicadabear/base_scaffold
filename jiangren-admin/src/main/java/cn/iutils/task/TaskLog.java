package cn.iutils.task;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import cn.iutils.common.service.BaseService;
import cn.iutils.common.spring.SpringUtils;
import cn.iutils.sys.entity.SLog;
import cn.iutils.sys.service.SLogService;

import com.google.common.collect.Lists;

/**
 * 日志记录任务
 * 
 * @author cc
 * 
 */
public class TaskLog extends BaseService {

	private volatile static TaskLog singleton;

	public TaskLog() {
	}

	/**
	 * 获取日志单例
	 * 
	 * @return
	 */
	public static TaskLog getSingleton() {
		if (singleton == null) {
			synchronized (TaskLog.class) {
				if (singleton == null) {
					singleton = new TaskLog();
				}
			}
		}
		return singleton;
	}

	/**
	 * 日志记录队列
	 */
	private static BlockingQueue<SLog> queueLog = new ArrayBlockingQueue<SLog>(
			100);

	private static SLogService logService = SpringUtils
			.getBean(SLogService.class);// 日志服务

	/**
	 * 添加记录
	 * 
	 * @param o
	 */
	public void add(SLog log) {
		queueLog.offer(log);
		logger.info("记录了一条日志：" + log.getRequestUri());
	}

	/**
	 * 将日志入库任务
	 */
	public void run() {
		if (queueLog.size() > 0)
			logger.info("当前日志队列总数：" + queueLog.size());
		int i = 0;
		List<SLog> logs = Lists.newArrayList();// 临时日志堆栈
		while (queueLog.size() > 0) {
			if (i > 100) {
				break;
			}
			SLog log = queueLog.poll();
			logs.add(log);
			i++;
		}
		if (logs.size() > 0) {
			logService.saveBatch(logs);
			logger.info("当前日志入库队列数：" + logs.size());
		}

	}
}
