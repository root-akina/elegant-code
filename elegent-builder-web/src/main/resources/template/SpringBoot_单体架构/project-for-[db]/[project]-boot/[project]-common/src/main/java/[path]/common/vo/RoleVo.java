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
 * @Description：角色表
 */
@Data
@NoArgsConstructor
public class RoleVo extends BaseVo {

    @Builder
    public RoleVo(Long id, String dataState, String roleName, String label, Integer sortNo, String remark, String[] checkedIds,
                  String[] checkedResourceNos, String[] checkedDeptNos, Long userId, String dataScope) {
        super(id, dataState);
        this.roleName = roleName;
        this.label = label;
        this.sortNo = sortNo;
        this.remark = remark;
        this.checkedIds = checkedIds;
        this.checkedResourceNos = checkedResourceNos;
        this.checkedDeptNos = checkedDeptNos;
        this.userId = userId;
        this.dataScope = dataScope;
    }

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色标识")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "列表选择：选中角色Ids")
    private String[] checkedIds;

    @ApiModelProperty(value = "TREE结构：选中资源No")
    private String[] checkedResourceNos;

    @ApiModelProperty(value = "TREE结构：选中部门No")
    private String[] checkedDeptNos;

    @ApiModelProperty(value = "人员查询部门：当前人员Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    @ApiModelProperty(value = "数据范围（0本人  1自定义）")
    private String dataScope;

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), getRoleName(), getLabel(), getSortNo(), getRemark(), getUserId(), getDataScope());
        result = 31 * result + Arrays.hashCode(getCheckedIds());
        result = 31 * result + Arrays.hashCode(getCheckedResourceNos());
        result = 31 * result + Arrays.hashCode(getCheckedDeptNos());
        return result;
    }
}
