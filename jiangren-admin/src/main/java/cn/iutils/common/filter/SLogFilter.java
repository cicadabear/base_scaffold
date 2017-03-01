package cn.iutils.common.filter;

import cn.iutils.common.utils.DateUtils;
import cn.iutils.common.utils.LogUtils;
import cn.iutils.common.utils.UserUtils;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统日志拦截器
 * @author iutils.cn
 * @version 1.0
 */
public class SLogFilter extends AdviceFilter {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static final ThreadLocal<Long> startTimeThreadLocal =
			new NamedThreadLocal<Long>("ThreadLocal StartTime");

	@Override
	protected boolean preHandle(ServletRequest requestParm, ServletResponse response)
			throws Exception {
		HttpServletRequest request = (HttpServletRequest)requestParm;
		long beginTime = System.currentTimeMillis();//1、开始时间
		startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）
		return true;
	}

	@Override
	protected void postHandle(ServletRequest request, ServletResponse response)
			throws Exception {

	}

	@Override
	public void afterCompletion(ServletRequest requestParm,
			ServletResponse response, Exception exception) throws Exception {
		HttpServletRequest request = (HttpServletRequest)requestParm;
		long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
		long endTime = System.currentTimeMillis(); 	//2、结束时间
		// 保存系统日志
		LogUtils.saveSLog(request,
				UserUtils.getLoginUser(), exception, DateUtils.formatDateTime(endTime - beginTime));
	}

}
