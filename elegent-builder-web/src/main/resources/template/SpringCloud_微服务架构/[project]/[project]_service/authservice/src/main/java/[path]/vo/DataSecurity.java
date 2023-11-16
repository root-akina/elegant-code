package ${base.basePackage}.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 数据权限封装
 *
 */
@Data
@NoArgsConstructor
public class DataSecurity {

    private String dataScope; //数据权限的范围  0 仅本人  1 按部门  4 全部

    private List<String> deptNos;  //部门编号列表

    @Builder
    public DataSecurity(String dataScope , List<String> deptNos){
        this.dataScope=dataScope;
        this.deptNos=deptNos;
    }

}
