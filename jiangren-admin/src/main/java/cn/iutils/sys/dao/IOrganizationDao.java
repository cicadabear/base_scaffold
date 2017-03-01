package cn.iutils.sys.dao;

import org.apache.ibatis.annotations.Param;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import cn.iutils.sys.entity.Organization;

/**
 * 组织机构Dao
 * 
 * @author cc
 */
@MyBatisDao
public interface IOrganizationDao extends ICrudDao<Organization> {

}
