package ${base.basePackage}.common.context;
import ${base.basePackage}.common.vo.UserVo;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName subjectContent.java
 * @Description 用户主体对象
 */
@Slf4j
public class SubjectContext {

    /***
     * @description 创建线程局部userVO变量
     */
    public static ThreadLocal<UserVo> subjectThreadLocal = new ThreadLocal<UserVo>() {
        @Override
        protected UserVo initialValue() {
            return new UserVo();
        }
    };


    // 提供线程局部变量set方法
    public static void setSubject(UserVo userVo) {

        subjectThreadLocal.set(userVo);
    }
    // 提供线程局部变量get方法
    public static UserVo getSubject() {

        return subjectThreadLocal.get();
    }
    //清空当前线程，防止内存溢出
    public static void removeSubject() {

        subjectThreadLocal.remove();
    }

}
