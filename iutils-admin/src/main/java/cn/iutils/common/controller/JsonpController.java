package cn.iutils.common.controller;

import cn.iutils.common.ResultVo;
import cn.iutils.common.utils.net.JHttpUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * JSONP接口代理服务 解决，ajax跨域问题
 * 
 * @author cc
 * 
 */
@RestController
@RequestMapping(value = "/jsonp/api")
public class JsonpController extends BaseController {

	/**
	 * GET服务
	 * 
	 * @param url
	 * @param callback
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(String url, String callback, HttpServletResponse response) {
		ResultVo resultVo = null;
		try {
			url = URLDecoder.decode(url, "utf-8");
			String content = Base64.encodeBase64String(JHttpUtils.doGet(url)
					.getBytes("utf-8"));
			resultVo = new ResultVo(ResultVo.SUCCESS, "1", "调用成功", content);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			resultVo = new ResultVo(ResultVo.FAILURE, "0", "调用失败", null);
		}
		return renderString(response, resultVo, callback);
	}

}
