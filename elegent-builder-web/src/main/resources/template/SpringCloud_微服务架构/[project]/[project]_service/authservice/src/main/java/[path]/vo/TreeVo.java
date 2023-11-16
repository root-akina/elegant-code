package ${base.basePackage}.vo;

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


	private List<TreeItemVo> items;


	private List<String> checkedIds;


	private List<String> expandedIds;

}
