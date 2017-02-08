package cn.iutils.common.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 基础控制器类
 * 
 * @author cc
 */
public abstract class BaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 管理基础路径
	 */
	@Value("${adminPath}")
	protected String adminPath;

	/**
	 * 前端基础路径
	 */
	@Value("${frontPath}")
	protected String frontPath;

	/**
	 * rest接口路径
	 */
	@Value("${restPath}")
	protected String restPath;

	/**
	 * 前端URL后缀
	 */
	@Value("${urlSuffix}")
	protected String urlSuffix;

	/**
	 * 客户端返回JSON字符串
	 * 
	 * @param response
	 * @param object
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object) {
		return renderString(response, JsonMapper.toJsonString(object),
				"application/json");
	}

	/**
	 * 返回JSONP的数据
	 * 
	 * @param response
	 * @param object
	 * @param callback
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object,
			String callback) {
		return renderString(response,
				callback + "(" + JsonMapper.toJsonString(object) + ")",
				"application/text");
	}

	/**
	 * 客户端返回字符串
	 * 
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string,
			String type) {
		try {
			response.reset();
			response.setContentType(type);
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Real-IP");
		if (StringUtils.isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("X-Forwarded-For");
		} else if (StringUtils.isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("Proxy-Client-IP");
		} else if (StringUtils.isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("WL-Proxy-Client-IP");
		}
		return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * 添加Model消息
	 * 
	 * @param messages
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		model.addAttribute("msg", sb.toString());
	}

	/**
	 * 添加Flash消息
	 * 
	 * @param messages
	 */
	protected void addMessage(RedirectAttributes redirectAttributes,
			String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		redirectAttributes.addFlashAttribute("msg", sb.toString());
	}

}
