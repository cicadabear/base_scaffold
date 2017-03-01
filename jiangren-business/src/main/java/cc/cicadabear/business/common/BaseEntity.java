package cc.cicadabear.business.common;

import cn.iutils.common.config.JConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体
 *
 * @param <T>
 * @author cc
 */
public abstract class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实体编号（唯一标识）
     */
    protected String id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createDate; // 创建日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateDate; // 更新日期
    protected String status; // 状态

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
