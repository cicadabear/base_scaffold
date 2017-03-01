package cn.iutils.sys.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import cn.iutils.sys.entity.ScheduleJob;

/**
 * 任务调度 DAO接口
 * 
 * @author MyCode
 * @version 1.0
 */
@MyBatisDao
public interface ScheduleJobDao extends ICrudDao<ScheduleJob> {

	/**
	 * 更改任务状态
	 * 
	 * @param scheduleJob
	 * @return
	 */
	public int changeJobStatus(ScheduleJob scheduleJob);

}
