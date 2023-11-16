package ${base.basePackage}.common;
import java.time.LocalDateTime;

/**
 * @Description 构造AjaxResult工具
 */
public class AjaxResultBuild {

    public static <T> AjaxResult<T> build(BasicEnum basicEnumIntface, T t){
        //从当前线程获得用户对象
        //UserVo subject = SubjectContent.getSubject();
        //构建对象
        return AjaxResult.<T>builder()
            .code(basicEnumIntface.getCode())
            .msg(basicEnumIntface.getMsg())
            .data(t)
            //.operatorId(subject.getId())
            //.operatorName(subject.getUsername())
            .operationTime(LocalDateTime.now())
            .build();
    }

    /**
     * 公共成功响应方法
     * @param t
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> successBuild(T t){
        return AjaxResultBuild.build(BasicEnum.SUCCEED,t);
    }

    /**
     * 公共失败响应方法
     * @param t
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> failedBuild(T t){
        return AjaxResultBuild.build(BasicEnum.SYSYTEM_FAIL,t);
    }

}
