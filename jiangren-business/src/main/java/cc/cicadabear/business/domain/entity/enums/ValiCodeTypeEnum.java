package cc.cicadabear.business.domain.entity.enums;

/**
 * 数据范围
 *
 * @author cc
 */
public enum ValiCodeTypeEnum {

    register("注册", 1, false), forgetPassword("找回密码", 2, false), changePassword("修改密码", 3, true);

    private final String desc;

    private final Integer type;

    //是否需要登录之后才能发送
    private final boolean isSecurity;

    private ValiCodeTypeEnum(String desc, Integer type, boolean isSecurity) {
        this.desc = desc;
        this.type = type;
        this.isSecurity = isSecurity;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getType() {
        return type;
    }

    public boolean isSecurity() {
        return isSecurity;
    }
}
