package cc.cicadabear.business.domain.entity.user;

import cc.cicadabear.business.domain.entity.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户表
 *
 * @author cc
 */
public class ValiCode extends DataEntity<ValiCode> {

    private static final long serialVersionUID = 1L;

    private String code;// 验证码
    private Integer type; // 验证码类型 比如 注册 找回密码
    private String target; // 目标手机号

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date expireTime; // 过期时间

    private Integer errorTimes = 0; // 试错次数

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(Integer errorTimes) {
        this.errorTimes = errorTimes;
    }


//    @Override
//    public void preInsert() {
//        super.preInsert();
//    }
//
//    @Override
//    public void preUpdate() {
//        super.preUpdate();
//    }
}
