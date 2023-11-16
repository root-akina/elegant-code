package ${base.basePackage}.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：资源树结构体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeItemVo implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Long id;


    public String label;


    public Boolean isChecked;


    public List<TreeItemVo> children;
}
