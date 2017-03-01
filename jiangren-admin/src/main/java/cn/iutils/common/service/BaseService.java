package cn.iutils.common.service;

import cn.iutils.sys.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service基类
 * 
 * @author cc
 */
@Transactional(readOnly = true)
public abstract class BaseService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 数据权限过滤
     * @param user
     * @return
     */
    public static String dataScopeFilter(User user){

        return null;
    }

}

