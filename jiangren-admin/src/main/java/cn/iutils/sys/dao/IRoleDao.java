package cn.iutils.sys.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import cn.iutils.sys.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限Dao接口
 * 
 * @author cc
 */
@MyBatisDao
public interface IRoleDao extends ICrudDao<Role> {

    /**
     * 获取权限资源ID
     * @param roleIds
     * @return
     */
    public List<Role> getRoles(@Param("roleIds")String[] roleIds);

}
