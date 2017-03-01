package cn.iutils.sys.controller;

import cn.iutils.common.controller.BaseController;
import cn.iutils.common.utils.UserUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 * 
 * @author cc
 */
@Controller
@RequestMapping(value = "${adminPath}")
public class LoginController extends BaseController {

	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model) {

        //获取用户登录信息 验证已登录，跳转到管理页
        String userName = UserUtils.getLoginUserName();
        if(userName!=null){
            return "redirect:" + adminPath;
        }
		String exceptionClassName = (String) request
				.getAttribute("shiroLoginFailure");
		model.addAttribute("username", request.getParameter("username"));
		String error = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (IncorrectCredentialsException.class.getName().equals(
				exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (ExcessiveAttemptsException.class.getName().equals(
				exceptionClassName)) {
			error = "错误次数操作超过限制";
		} else if (LockedAccountException.class.getName().equals(
				exceptionClassName)) {
			error = "帐号被锁定";
		} else if (exceptionClassName != null) {
			error = "未知的错误";
		}
		model.addAttribute("message", error);
		if (request.getParameter("forceLogout") != null) {
			error = "您已经被管理员强制退出，请重新登录";
			model.addAttribute("message", error);
		}
		return "login";
	}

}
