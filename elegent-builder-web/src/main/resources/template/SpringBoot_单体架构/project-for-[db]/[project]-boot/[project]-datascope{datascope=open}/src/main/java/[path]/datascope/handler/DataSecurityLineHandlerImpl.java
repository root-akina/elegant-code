package ${base.basePackage}.datascope.handler;

import ${base.basePackage}.common.constant.DataScopeConstant;
import ${base.basePackage}.common.constant.DataSecurityConstant;
import ${base.basePackage}.common.context.SubjectContext;
import ${base.basePackage}.common.vo.DataSecurity;
import ${base.basePackage}.common.vo.UserVo;
import ${base.basePackage}.datascope.core.DataSecurityLineHandler;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DataSecurityLineHandlerImpl implements DataSecurityLineHandler {

    /**
     * 拦截的表列表
     */
    private List<String> tableList;

    /**
     * 条件值
     * @return
     */
    @Override
    public ExpressionList getDataSecurityList() {

        UserVo userVo = SubjectContext.getSubject();
        DataSecurity dataSecurity = userVo.getDataSecurity();

        //仅个人
        if(DataSecurityConstant.DATA_SCOPE_ONLY_ME.equals( dataSecurity.getDataScope() ) ){
            return new ExpressionList(  new StringValue( userVo.getId()+"" ));
        }

        //按部门
        if(DataSecurityConstant.DATA_SCOPE_CUSTOM.equals( dataSecurity.getDataScope() ) ){
            List<Expression> expressionList =
                    dataSecurity.getDeptNos().stream().map(StringValue::new).collect(Collectors.toList());
            return new ExpressionList( expressionList   );
        }

        return null;
    }

    /**
     * 返回条件列
     * @return
     */
    @Override
    public String getDataSecurityColumn() {
        UserVo userVo = SubjectContext.getSubject();
        DataSecurity dataSecurity = userVo.getDataSecurity();

        //仅个人
        if(DataSecurityConstant.DATA_SCOPE_ONLY_ME.equals( dataSecurity.getDataScope() ) ){
            return DataScopeConstant.USER_FIELD;
        }
        //按部门
        if(DataSecurityConstant.DATA_SCOPE_CUSTOM.equals( dataSecurity.getDataScope() ) ){
            return DataScopeConstant.DEPT_FIELD;
        }
        return DataScopeConstant.USER_FIELD;
    }

    /**
     * 判断表是否拦截
     * @param tableName 表名
     * @return
     */
    @Override
    public boolean isInterceptTable(String tableName) {
        if(tableList==null ) return false;

        if( tableList.contains( tableName )){
            UserVo userVo = SubjectContext.getSubject();
            DataSecurity dataSecurity = userVo.getDataSecurity();
            if(DataSecurityConstant.DATA_SCOPE_ALL.equals( dataSecurity.getDataScope() ) ){
                return false;
            }
            return true;
        }
        return false;
    }
}
