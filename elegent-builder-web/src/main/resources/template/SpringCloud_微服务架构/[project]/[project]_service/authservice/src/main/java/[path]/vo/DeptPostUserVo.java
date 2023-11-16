package ${base.basePackage}.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    private String deptNo;

    private String postNo;

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserId(), getDeptNo(), getPostNo());
    }
}
