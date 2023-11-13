package ${base.basePackage}.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 资源树显示类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeVo implements Serializable {

	@ApiModelProperty(value = "tree数据")
	private List<TreeItemVo> items;

	@ApiModelProperty(value = "选择节点")
	private List<String> checkedIds;

	@ApiModelProperty(value = "展开项")
	private List<String> expandedIds;

}
