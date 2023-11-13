package ${base.basePackage}.rbac.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_role")
public class Role extends BaseEntity {

    private String roleName;

    private String label;

    private Integer sortNo;

    private String dataState;

    private String remark;

    private String dataScope;

    private static final long serialVersionUID = 1L;

    @Builder
    public Role(Long id, String dataState, String roleName, String label, Integer sortNo, String dataState1, String remark, String dataScope) {
        super(id, dataState);
        this.roleName = roleName;
        this.label = label;
        this.sortNo = sortNo;
        this.dataState = dataState1;
        this.remark = remark;
        this.dataScope = dataScope;
    }

}
