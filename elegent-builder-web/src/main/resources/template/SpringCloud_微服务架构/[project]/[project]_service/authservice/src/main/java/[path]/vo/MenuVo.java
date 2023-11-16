package ${base.basePackage}.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 动态菜单VO对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuVo implements Serializable {

	// 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题

	private  String name;


	private String path;


	private String redirect;


	private String component;

	// 当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1

	private Boolean hidden;


	private List<MenuVo> children;


	private MenuMetaVo meta;
}
