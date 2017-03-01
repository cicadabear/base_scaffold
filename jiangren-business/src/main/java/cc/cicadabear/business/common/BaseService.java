package cc.cicadabear.business.common;

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


}

