package ${base.basePackage}.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Description：权限表
 */
@Data
@NoArgsConstructor
public class ResourceVo extends BaseVo {

    @Builder
    public ResourceVo(Long id, String dataState, String resourceNo, String parentResourceNo, String resourceName,
                      String resourceType, String requestPath, String label, Integer sortNo, String icon, String remark,
                      String[] checkedIds, String[] checkedResourceNos, Long roleId) {
        super(id, dataState);
        this.resourceNo = resourceNo;
        this.parentResourceNo = parentResourceNo;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.requestPath = requestPath;
        this.label = label;
        this.sortNo = sortNo;
        this.icon = icon;
        this.remark = remark;
        this.checkedIds = checkedIds;
        this.checkedResourceNos = checkedResourceNos;
        this.roleId = roleId;
    }

    private String resourceNo;

    private String parentResourceNo;

    private String resourceName;

    private String resourceType;

    private String requestPath;

    private String label;

    private Integer sortNo;


    private String icon;

    private String remark;

    private String[] checkedIds;

    private String[] checkedResourceNos;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), getResourceNo(), getParentResourceNo(), getResourceName(), getResourceType(), getRequestPath(), getLabel(), getSortNo(), getIcon(), getCreateBy(), getCreateTime(), getUpdateBy(), getUpdateTime(), getRemark(), getRoleId());
        result = 31 * result + Arrays.hashCode(getCheckedIds());
        result = 31 * result + Arrays.hashCode(getCheckedResourceNos());
        return result;
    }
}
