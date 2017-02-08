package cn.iutils.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import cn.iutils.sys.dao.UserQiniuDao;
import cn.iutils.sys.entity.UserQiniu;

/**
 * 用户七牛配置 Service层
 * 
 * @author MyCode
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class UserQiniuService extends CrudService<UserQiniuDao, UserQiniu> {

	/**
	 * 根据用户获取七牛配置
	 * 
	 * @param userId
	 * @return
	 */
	public UserQiniu getByUser(String userId) {
		return dao.getByUser(userId);
	}
}
