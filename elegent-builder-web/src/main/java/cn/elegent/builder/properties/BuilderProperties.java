package cn.elegent.builder.properties;

import cn.elegent.builder.domain.DbType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Component
@ConfigurationProperties(prefix = "elegent.builder")
public class BuilderProperties {


    private List<DbType> dbTypeList;

}
