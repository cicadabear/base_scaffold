package cn.iutils.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具
 * 
 * @author cc
 * 
 */
public class JRegularUtils {

	/**
	 * 获取匹配的字符串
	 * 
	 * @param content
	 *            内容
	 * @param expression
	 *            表达式
	 * @return
	 */
	public static String getStr(String content, String expression) {
		String result = null;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			result = matcher.group(1);
		}
		return result;
	}

	public static void main(String[] args) {
		String s = getStr("济南139", "([\u4e00-\u9fa5]+)[0-9]*");
		System.out.println(s);
	}
}
