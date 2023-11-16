package ${base.basePackage}.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：菜单meta属性
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuMetaVo implements Serializable {


    private String title;


    private String icon;


    private List<String> roles;
}
