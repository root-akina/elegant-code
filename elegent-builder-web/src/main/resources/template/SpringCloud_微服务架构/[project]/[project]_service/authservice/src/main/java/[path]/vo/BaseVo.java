package ${base.basePackage}.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName BasicVo.java
 * @Description 基础请求
 */
@Data
@NoArgsConstructor
public class BaseVo implements Serializable {


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//set
    protected LocalDateTime createTime;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//set
    protected LocalDateTime updateTime;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createBy;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long updateBy;


    protected String dataState;


    private String remark;


    private String creator;

    public BaseVo(Long id, String dataState) {
        this.id = id;
        this.dataState = dataState;
    }
}
