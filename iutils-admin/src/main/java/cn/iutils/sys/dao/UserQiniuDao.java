package cn.iutils.sys.dao;

import org.apache.ibatis.annotations.Param;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import cn.iutils.sys.entity.UserQiniu;

/**
 * 用户七牛配置 DAO接口
 * 
 * @author MyCode
 * @version 1.0
 */
@MyBatisDao
public interface UserQiniuDao extends ICrudDao<UserQiniu> {

	/**
	 * 根据用户获取七牛配置
	 * 
	 * @param userId
	 * @return
	 */
	public UserQiniu getByUser(@Param("userId") String userId);

}
