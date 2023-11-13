package ${base.basePackage}.common.vo;

import ${base.basePackage}.common.basic.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Description：岗位表
 */
@Data
@NoArgsConstructor
public class PostVo extends BaseVo {

    @Builder
    public PostVo(Long id, String dataState, String deptNo, String postNo, String postName, Integer sortNo, String remark, String[] checkedIds, DeptVo deptVo) {
        super(id, dataState);
        this.deptNo = deptNo;
        this.postNo = postNo;
        this.postName = postName;
        this.sortNo = sortNo;
        this.remark = remark;
        this.checkedIds = checkedIds;
        this.deptVo = deptVo;
    }

    @ApiModelProperty(value = "部门编号")
    private String deptNo;

    @ApiModelProperty(value = "岗位编码：父部门编号+001【3位】")
    private String postNo;

    @ApiModelProperty(value = "岗位名称")
    private String postName;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortNo;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "列表选择：选中职位Ids")
    private String[] checkedIds;

    @ApiModelProperty(value = "职位对应部门")
    private DeptVo deptVo;

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), getDeptNo(), getPostNo(), getPostName(), getSortNo(), getCreateBy(), getCreateTime(), getUpdateBy(), getUpdateTime(), getRemark(), getDeptVo());
        result = 31 * result + Arrays.hashCode(getCheckedIds());
        return result;
    }
}
