package ${base.basePackage}.rbac.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_dept")
public class Dept extends BaseEntity {

    private String parentDeptNo;

    private String deptNo;

    private String deptName;

    private Integer sortNo;

    private Long leaderId;

    private static final long serialVersionUID = 1L;

    @Builder
    public Dept(Long id, String parentDeptNo, String deptNo, String deptName, Integer sortNo, String dataState, Long leaderId) {
        super(id, dataState);
        this.parentDeptNo = parentDeptNo;
        this.deptNo = deptNo;
        this.deptName = deptName;
        this.sortNo = sortNo;
        this.leaderId = leaderId;
    }
}
