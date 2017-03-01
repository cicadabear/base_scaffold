package cn.iutils.sys.entity;

import java.util.Map;

import cn.iutils.common.BaseEntity;
import cn.iutils.common.utils.JStringUtils;

/**
 * 日志记录
 *
 * @author MyCode
 * @version 1.0
 */
public class SLog extends DataEntity<SLog> {

    private static final long serialVersionUID = 1L;

    private String menu;// 菜单
    private String remoteAddr;// 操作IP
    private String requestUri;// 请求地址
    private String method;// 操作方式
    private String params;// 提交的数据
    private String userAgent;// 客户端信息
    private String exception;// 异常信息
    private String timeConsuming;//耗时
    private String username;//用户名 用作查询条件

    public SLog() {
        super();
    }

    public SLog(String id) {
        super(id);
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void setParams(Map paramMap) {
        if (paramMap == null) {
            return;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap)
                .entrySet()) {
            params.append(("".equals(params.toString()) ? "" : "&")
                    + param.getKey() + "=");
            String paramValue = (param.getValue() != null
                    && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(JStringUtils.abbr(JStringUtils.endsWithIgnoreCase(
                    param.getKey(), "password") ? "" : paramValue, 100));
        }
        this.params = params.toString();
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(String timeConsuming) {
        this.timeConsuming = timeConsuming;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
