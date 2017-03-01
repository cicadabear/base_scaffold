package cn.iutils.sys.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cn.iutils.sys.entity.enums.DataScopeEnum;

/**
 * 权限表
 * 
 * @author cc
 */
public class Role extends DataEntity<Role> {

	private static final long serialVersionUID = 1L;
	private String role; // 角色标识
	private String name; // 角色名称
	private String organizationId;//机构ID
	private Organization organization;//归属机构
	private DataScopeEnum dataScope = DataScopeEnum.self; // 数据范围
	private List<String> resourceIds; // 拥有的资源
	private String resourceIdsStr; // 拥有的资源
	private Boolean available = Boolean.TRUE; // 是否可用
	
	public static enum RoleType {
		/** 管理员 */
		SECURITY_ROLE("security-role","管理员"),
		/** 可进行任务分配 */
		ASSIGNMENT("assignment", "可进行任务分配"),
		/** 普通用户 */
		USER("user", "普通用户");
		private RoleType(String typeName, String typeDesc) {
			this.typeName = typeName;
			this.typeDesc = typeDesc;
		}
		private String typeName;
		private String typeDesc;

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public String getTypeDesc() {
			return typeDesc;
		}

		public void setTypeDesc(String typeDesc) {
			this.typeDesc = typeDesc;
		}
		
	}

	public Role() {
		super();
	}

	public Role(String id) {
		super(id);
	}

	public Role(String role, String name, Boolean available) {
		super();
		this.role = role;
		this.name = name;
		this.available = available;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataScopeEnum getDataScope() {
		return dataScope;
	}

	public void setDataScope(DataScopeEnum dataScope) {
		this.dataScope = dataScope;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<String> getResourceIds() {
		if (resourceIds == null) {
			resourceIds = new ArrayList<String>();
		}
		return resourceIds;
	}

	public void setResourceIds(List<String> resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getResourceIdsStr() {
		if (CollectionUtils.isEmpty(resourceIds)) {
			return "";
		}
		StringBuilder s = new StringBuilder();
		for (String resourceId : resourceIds) {
			s.append(resourceId);
			s.append(",");
		}
		return s.toString();
	}

	public void setResourceIdsStr(String resourceIdsStr) {
		if (StringUtils.isEmpty(resourceIdsStr)) {
			return;
		}
		String[] resourceIdStrs = resourceIdsStr.split(",");
		for (String resourceIdStr : resourceIdStrs) {
			if (StringUtils.isEmpty(resourceIdStr)) {
				continue;
			}
			getResourceIds().add(resourceIdStr);
		}
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

}
