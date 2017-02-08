package cn.iutils.sys.entity;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户表
 * 
 * @author cc
 */
public class User extends DataEntity<User> {

	private static final long serialVersionUID = 1L;

	private String no;// 编号
	private String organizationId; // 组织机构编号
	private Organization organization;//组织机构
	private String username; // 用户名
	private String password; // 密码
	private String salt; // 加密密码的盐
	private List<String> roleIds; // 拥有的角色列表
	private String roleIdsStr;
	private Boolean locked = Boolean.FALSE;//是否锁定
	private Boolean isDept = Boolean.FALSE;//是否部门管理员
	private String name;// 姓名或昵称
	private String email;// 电子邮件
	private String phone;// 电话
	private String mobile;// 手机
	private String photo;// 头像

	public User() {
		super();
	}

	public User(String id) {
		super(id);
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCredentialsSalt() {
		return username + salt;
	}

	public List<String> getRoleIds() {
		if (roleIds == null) {
			roleIds = new ArrayList<String>();
		}
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleIdsStr() {
		if (CollectionUtils.isEmpty(roleIds)) {
			return "";
		}
		StringBuilder s = new StringBuilder();
		for (String roleId : roleIds) {
			s.append(roleId);
			s.append(",");
		}
		return s.toString();
	}

	public void setRoleIdsStr(String roleIdsStr) {
		if (StringUtils.isEmpty(roleIdsStr)) {
			return;
		}
		String[] roleIdStrs = roleIdsStr.split(",");
		for (String roleIdStr : roleIdStrs) {
			if (StringUtils.isEmpty(roleIdStr)) {
				continue;
			}
			getRoleIds().add(roleIdStr);
		}
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getIsDept() {
		return isDept;
	}

	public void setIsDept(Boolean isDept) {
		this.isDept = isDept;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isAdmin(){
		return isAdmin(this.id);
	}

	public static boolean isAdmin(String id){
		return id != null && "1".equals(id);
	}

}
