package cn.iutils.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import cn.iutils.common.spring.SpringUtils;
import cn.iutils.sys.dao.IUserDao;
import cn.iutils.sys.entity.User;

/**
 * 用户管理工具
 * 
 * @author cc
 * 
 */
public class UserUtils {

	/**
	 * 用户持久层
	 */
	private static IUserDao userDao = SpringUtils.getBean(IUserDao.class);

	/**
	 * 获取当前访问用户名
	 * 
	 * @return
	 */
	public static String getLoginUserName() {
		Subject subject = SecurityUtils.getSubject();
		String userName = (String) subject.getPrincipal();
		return userName;
	}

	/**
	 * 获取当前登录用户
	 * 
	 * @return
	 */
	public static User getLoginUser() {
		Subject subject = SecurityUtils.getSubject();
		String userName = (String) subject.getPrincipal();
		return userDao.getUserByUserName(userName);
	}

}
