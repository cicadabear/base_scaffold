package cn.iutils.sys.entity.enums;

/**
 * 数据范围
 * 
 * @author cc
 * 
 */
public enum DataScopeEnum {

	all("所有数据"),orgFollow("所在机构及以下数据"), org("所在机构数据"),self("仅本人数据");

	private final String info;

	private DataScopeEnum(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

}
