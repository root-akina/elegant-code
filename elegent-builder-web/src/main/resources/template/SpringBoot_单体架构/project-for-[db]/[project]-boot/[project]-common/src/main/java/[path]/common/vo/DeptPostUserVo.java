package ${base.basePackage}.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import ${base.basePackage}.common.basic.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Description：部门岗位用户关联表
 */
@Data
@NoArgsConstructor
public class DeptPostUserVo extends BaseVo {

    private static final long serialVersionUID = 1L;

    @Builder
    public DeptPostUserVo(Long id, String dataState, Long userId, String deptNo, String postNo) {
        super(id, dataState);
        this.userId = userId;
        this.deptNo = deptNo;
        this.postNo = postNo;
    }

    @ApiModelProperty(value = "用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    @ApiModelProperty(value = "部门编号")
    private String deptNo;

    @ApiModelProperty(value = "岗位编码")
    private String postNo;

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserId(), getDeptNo(), getPostNo());
    }
}
