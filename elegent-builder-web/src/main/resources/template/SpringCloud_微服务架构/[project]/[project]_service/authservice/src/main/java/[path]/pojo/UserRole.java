package ${base.basePackage}.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_user_role")
public class UserRole extends BaseEntity {

    private Long userId;

    private Long roleId;

    private static final long serialVersionUID = 1L;

    @Builder
    public UserRole(Long id, String dataState, Long userId, Long roleId) {
        super(id, dataState);
        this.userId = userId;
        this.roleId = roleId;
    }
}
