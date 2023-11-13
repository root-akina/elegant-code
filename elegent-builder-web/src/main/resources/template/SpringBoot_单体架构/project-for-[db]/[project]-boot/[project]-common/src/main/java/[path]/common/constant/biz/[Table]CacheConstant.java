package ${base.basePackage}.common.constant.biz;
import ${base.basePackage}.common.constant.CacheConstant;
/**
 * @ClassName ${table.className}CacheConstant.java
 * @Description TODO
 */
public class ${table.className}CacheConstant extends CacheConstant {

    //缓存父包
    public static final String PREFIX= "${table.varName}:";

    //缓存父包
    public static final String BASIC= PREFIX+"basic";

    //分布式锁前缀
    public static final String LOCK_PREFIX = PREFIX+"lock:";

    //page分页
    public static final String PAGE= PREFIX+"page";

    //list下拉框
    public static final String LIST= PREFIX+"list";


}

