package ${base.basePackage}.common;


/**
 * @ClassName BasicEnum.java
 * @Description 基础枚举
 */
public enum BasicEnum  {

    SUCCEED(200,"操作成功"),
    SECURITY_ACCESSDENIED_FAIL(401,"权限不足!"),
    SYSYTEM_FAIL(1503,"系统运行异常"),
    REMOTE_FAIL(1504,"微服务远程调用异常"),
    VALID_EXCEPTION(1505,"参数校验异常");

    public Integer code;
    public String msg;

    BasicEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
