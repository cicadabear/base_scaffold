package cc.cicadabear.business.common;

/**
 * rest接口返回数据对象
 */
public class ResultVo {
    /**
     * 结果0-失败，1-成功
     */
    private int ret;
    /**
     * 结果代码
     */
    private int code;
    /**
     * 结果消息
     */
    private String msg;
    /**
     * 结果数据
     */
    private Object data;

    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;

    public ResultVo(int ret, int code, String msg, Object data) {
        super();
        this.code = code;
        this.msg = msg;
        this.ret = ret;
        this.data = data;
    }

    public ResultVo() {
        super();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean fail() {
        return ret == FAILURE;
    }

    public void fail(boolean isFailed) {
        ret = isFailed ? FAILURE : SUCCESS;
    }

    public void fail(String msg) {
        ret = FAILURE;
        this.msg = msg;
    }
}
