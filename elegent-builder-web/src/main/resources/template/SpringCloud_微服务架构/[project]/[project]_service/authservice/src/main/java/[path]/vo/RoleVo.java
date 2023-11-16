package ${base.basePackage}.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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


    private String roleName;


    private String label;


    private Integer sortNo;


    private String remark;


    private String[] checkedIds;


    private String[] checkedResourceNos;


    private String[] checkedDeptNos;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

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
