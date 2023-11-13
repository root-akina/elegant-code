package ${base.basePackage}.rbac.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_role_dept")
public class RoleDept extends BaseEntity {

    private Long roleId;

    private String deptNo;

    private static final long serialVersionUID = 1L;

    @Builder
    public RoleDept(Long id, String dataState, Long roleId, String deptNo) {
        super(id, dataState);
        this.roleId = roleId;
        this.deptNo = deptNo;
    }
}
