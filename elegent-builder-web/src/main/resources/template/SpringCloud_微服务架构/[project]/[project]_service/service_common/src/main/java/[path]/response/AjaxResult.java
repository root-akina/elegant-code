package ${base.basePackage}.response;


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


    private Integer code;


    private String msg;


    private T data;


    private Long operatorId;


    private String operatorName;


    private LocalDateTime operationTime;

    public Boolean isSuccessful() {
        return BasicEnum.SUCCEED.getCode().equals(this.code);
    }

}
