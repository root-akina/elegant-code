package ${base.basePackage}.common.exception;


import ${base.basePackage}.common.basic.IBasicEnum;

/**
 * @Description：自定义异常
 */
public class ProjectException extends RuntimeException {

    //错误编码
    private Integer code;

    //提示信息
    private String message;

    //异常接口
    private IBasicEnum basicEnumIntface;

    public ProjectException() {

    }
    public ProjectException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ProjectException(IBasicEnum errorCode) {
        setBasicMsg(errorCode);
    }


    public ProjectException(IBasicEnum errorCode, String throwMsg) {
        super(throwMsg);
        setBasicMsg(errorCode);
    }

    public ProjectException(IBasicEnum errorCode, Throwable throwable) {
        super(throwable);
        setBasicMsg(errorCode);
    }

    private void setBasicMsg(IBasicEnum basicEnumIntface) {
        this.code = basicEnumIntface.getCode();
        this.message = basicEnumIntface.getMsg();
        this.basicEnumIntface = basicEnumIntface;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IBasicEnum getBasicEnumIntface() {
        return basicEnumIntface;
    }

    public void setBasicEnumIntface(IBasicEnum basicEnumIntface) {
        this.basicEnumIntface = basicEnumIntface;
    }


}
