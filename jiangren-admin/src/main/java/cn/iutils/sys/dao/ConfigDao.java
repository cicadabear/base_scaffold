package cn.iutils.sys.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import cn.iutils.sys.entity.Config;

/**
* 公共配置表 DAO接口
* @author iutils.cn
* @version 1.0
*/
@MyBatisDao
public interface ConfigDao extends ICrudDao<Config> {

}
