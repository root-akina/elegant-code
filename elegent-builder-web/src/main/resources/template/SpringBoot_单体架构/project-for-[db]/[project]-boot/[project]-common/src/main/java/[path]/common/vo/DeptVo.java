package ${base.basePackage}.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import ${base.basePackage}.common.basic.BaseVo;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "父部门编号")
    private String parentDeptNo;

    @ApiModelProperty(value = "部门编号")
    private String deptNo;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "负责人Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long leaderId;

    @ApiModelProperty(value = "列表选择：选中部门Ids")
    private String[] checkedIds;

    @ApiModelProperty(value = "TREE结构：选中部门No")
    private String[] checkedDeptNos;

    @ApiModelProperty(value = "角色查询部门：部门对应角色id")
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
