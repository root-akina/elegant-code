package ${base.basePackage}.common.basic;

import ${base.basePackage}.common.enums.BasicEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description 返回结果
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AjaxResult<T> implements Serializable {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "状态信息")
    private String msg;

    @ApiModelProperty(value = "返回结果")
    private T data;

    @ApiModelProperty(value = "操作人Id")
    private Long operatorId;

    @ApiModelProperty(value = "操作人名称")
    private String operatorName;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operationTime;

    public Boolean isSuccessful() {
        return BasicEnum.SUCCEED.getCode().equals(this.code);
    }

}
