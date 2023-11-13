package ${base.basePackage}.rbac.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_post")
public class Post extends BaseEntity {

    private String deptNo;

    private String postNo;

    private String postName;

    private Integer sortNo;

    private String remark;

    private static final long serialVersionUID = 1L;

    @Builder
    public Post(Long id, String dataState, String deptNo, String postNo, String postName, Integer sortNo, String remark) {
        super(id, dataState);
        this.deptNo = deptNo;
        this.postNo = postNo;
        this.postName = postName;
        this.sortNo = sortNo;
        this.remark = remark;
    }
}
