package ${base.basePackage}.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_role_resource")
public class RoleResource extends BaseEntity {

    private Long roleId;

    private String resourceNo;

    private static final long serialVersionUID = 1L;

    @Builder
    public RoleResource(Long id, String dataState, Long roleId, String resourceNo) {
        super(id, dataState);
        this.roleId = roleId;
        this.resourceNo = resourceNo;
    }
}
