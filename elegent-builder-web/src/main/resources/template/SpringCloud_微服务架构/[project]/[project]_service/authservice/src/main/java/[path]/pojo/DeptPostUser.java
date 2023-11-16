package ${base.basePackage}.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_dept_post_user")
public class DeptPostUser extends BaseEntity {

    private Long userId;

    private String deptNo;

    private String postNo;

    private static final long serialVersionUID = 1L;

    @Builder
    public DeptPostUser(Long id, String dataState, Long userId, String deptNo, String postNo) {
        super(id, dataState);
        this.userId = userId;
        this.deptNo = deptNo;
        this.postNo = postNo;
    }
}
