package ${base.basePackage}.rbac.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName CoustomerSecurityProperties.java
 * @Description 忽略配置及跨域
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "${base.project}.framework.rbac")
@Configuration
@MapperScan("${base.basePackage}.rbac.mapper")
public class RBACConfigProperties {

    /**
     * 默认账号
     */
    String defaultPassword ;


}
