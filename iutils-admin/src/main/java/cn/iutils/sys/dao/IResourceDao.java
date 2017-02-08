package cn.iutils.sys.dao;

import org.apache.ibatis.annotations.Param;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import cn.iutils.sys.entity.Resource;

import java.util.List;
import java.util.Set;

/**
 * 资源Dao接口
 * 
 * @author cc
 */
@MyBatisDao
public interface IResourceDao extends ICrudDao<Resource> {

	/**
	 * 获取资源排序值
	 * 
	 * @param id
	 * @return
	 */
	public int getResourceSort(@Param("id") String id);

	/**
	 * 获取权限标识
	 * @param resourceIds
	 * @return
	 */
	public List<Resource> getResources(@Param("resourceIds")Set<String> resourceIds);

}
