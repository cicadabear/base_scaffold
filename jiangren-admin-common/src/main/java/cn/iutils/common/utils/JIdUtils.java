package cn.iutils.common.utils;

import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * 
 * @author cc
 */
public class JIdUtils {

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
