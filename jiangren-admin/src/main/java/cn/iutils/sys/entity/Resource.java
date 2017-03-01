package cn.iutils.sys.entity;

import cn.iutils.common.BaseEntity;
import cn.iutils.sys.entity.enums.ResourceEnum;

import java.util.Set;

/**
 * 资源表
 * 
 * @author cc
 */
public class Resource extends DataEntity<Resource> {

	private static final long serialVersionUID = 1L;

	private String name; // 资源名称
	private ResourceEnum type = ResourceEnum.menu; // 资源类型
	private String icon;//图标
	private String url; // 资源路径
	private String permission; // 权限字符串
	private String parentId; // 父编号
	private Resource resource;//上级资源
	private String parentIds; // 父编号列表
	private int sort=0;// 排序
	private Boolean available = Boolean.TRUE;//是否可用

	public Resource() {
		super();
	}

	public Resource(String id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ResourceEnum getType() {
		return type;
	}

	public void setType(ResourceEnum type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public boolean isRootNode() {
		return parentId.equals("0");
	}

	public String makeSelfAsParentIds() {
		return getParentIds() + getId() + "/";
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
