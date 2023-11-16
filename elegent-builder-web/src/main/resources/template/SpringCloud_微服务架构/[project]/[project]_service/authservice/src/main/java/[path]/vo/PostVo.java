package ${base.basePackage}.vo;
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

    private String deptNo;

    private String postNo;

    private String postName;

    private Integer sortNo;

    private String remark;

    private String[] checkedIds;

    private DeptVo deptVo;

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), getDeptNo(), getPostNo(), getPostName(), getSortNo(), getCreateBy(), getCreateTime(), getUpdateBy(), getUpdateTime(), getRemark(), getDeptVo());
        result = 31 * result + Arrays.hashCode(getCheckedIds());
        return result;
    }
}
