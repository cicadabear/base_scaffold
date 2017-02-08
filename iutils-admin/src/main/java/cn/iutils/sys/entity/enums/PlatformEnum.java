package cn.iutils.sys.entity.enums;

/**
 * 平台类型
 * 
 * @author cc
 * 
 */
public enum PlatformEnum {

	web("网站"), mobile("手机");

	private final String info;

	private PlatformEnum(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

}
