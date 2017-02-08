package cn.iutils.common;

import cn.iutils.common.config.JConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体
 * 
 * @author cc
 * @param <T>
 */
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 实体编号（唯一标识）
	 */
	protected String id;

	protected String remarks; // 备注
	protected String createBy; // 创建者
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date createDate; // 创建日期
	protected String updateBy; // 更新者
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date updateDate; // 更新日期
	protected String status; // 状态
	protected String dbType;// 数据类型

	/**
	 * 是否是新记录（默认：false），调用setIsNew()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
	 */
	protected boolean isNewId = false;

	public BaseEntity() {
		this.status = STATUS_NORMAL;
	}

	public BaseEntity(String id) {
		this();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean getIsNewId() {
		return isNewId || StringUtils.isBlank(getId());
	}

	public void setNewId(boolean isNewId) {
		this.isNewId = isNewId;
	}

	public String getDbType() {
		return JConfig.getConfig("jdbc.type");
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}


	/**
	 * 插入之前执行方法，子类实现
	 */
	public abstract void preInsert();

	/**
	 * 更新之前执行方法，子类实现
	 */
	public abstract void preUpdate();

	/**
	 * 状态（0：正常；1：删除）
	 */
	public static final String STATUS_NORMAL = "0";
	public static final String STATUS_DELETE = "1";

}
