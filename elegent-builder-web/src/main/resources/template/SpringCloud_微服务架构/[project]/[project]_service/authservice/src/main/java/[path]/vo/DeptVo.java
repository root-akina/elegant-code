package ${base.basePackage}.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Description：部门表
 */
@Data
@NoArgsConstructor
public class DeptVo extends BaseVo {

    @Builder
    public DeptVo(Long id, String dataState, String parentDeptNo, String deptNo, String deptName, Integer sortNo, Long leaderId,
                  String[] checkedIds, String[] checkedDeptNos, Long roleId) {
        super(id, dataState);
        this.parentDeptNo = parentDeptNo;
        this.deptNo = deptNo;
        this.deptName = deptName;
        this.sortNo = sortNo;
        this.leaderId = leaderId;
        this.checkedIds = checkedIds;
        this.checkedDeptNos = checkedDeptNos;
        this.roleId = roleId;
    }


    private String parentDeptNo;


    private String deptNo;


    private String deptName;

    private Integer sortNo;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long leaderId;


    private String[] checkedIds;


    private String[] checkedDeptNos;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), getParentDeptNo(), getDeptNo(), getDeptName(), getSortNo(), getLeaderId());
        result = 31 * result + Arrays.hashCode(getCheckedIds());
        result = 31 * result + Arrays.hashCode(getCheckedDeptNos());
        return result;
    }
}
