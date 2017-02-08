package cn.iutils.common.filter;

import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserAgentUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 手机视图拦截器
 * @author iutils.cn
 * @version 1.0
 */
public class MobileFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse rsponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null){
            // 如果是手机或平板访问的话，则跳转到手机视图页面。
            if(UserAgentUtils.isMobileOrTablet(request) && !JStringUtils.startsWithIgnoreCase(modelAndView.getViewName(), "redirect:")){
                modelAndView.setViewName("mobile/" + modelAndView.getViewName());
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
