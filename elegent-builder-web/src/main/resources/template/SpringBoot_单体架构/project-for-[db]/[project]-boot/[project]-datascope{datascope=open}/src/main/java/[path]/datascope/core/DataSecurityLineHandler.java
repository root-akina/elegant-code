package ${base.basePackage}.datascope.core;

import net.sf.jsqlparser.expression.operators.relational.ExpressionList;

/**
 * @ClassName DataSecurityLineHandler.java
 * @Description 数据权限处理器（ DeptNo 行级 ）
 */
public interface DataSecurityLineHandler {

    /**
     * 获取数据权限值表达式
     * @return 数据权限值表达式
     */
    ExpressionList getDataSecurityList();

    /**
     * 获取数据权限字段名
     * @return 数据权限字段名
     */
    String getDataSecurityColumn();

    /**
     * 判断该表是否是需要拦截的表
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接数据权限条件
     */
    boolean isInterceptTable(String tableName);


}
