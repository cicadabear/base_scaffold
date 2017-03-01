package cn.iutils.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import cn.iutils.sys.dao.ConfigDao;
import cn.iutils.sys.entity.Config;

/**
* 公共配置表 Service层
* @author iutils.cn
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class ConfigService extends CrudService<ConfigDao, Config> {

}
