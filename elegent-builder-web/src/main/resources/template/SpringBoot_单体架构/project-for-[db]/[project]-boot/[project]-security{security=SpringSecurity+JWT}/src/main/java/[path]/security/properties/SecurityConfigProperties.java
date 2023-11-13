package ${base.basePackage}.security.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CoustomerSecurityProperties.java
 * @Description 忽略配置及跨域
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "${base.project}.framework.security")
@Configuration
public class SecurityConfigProperties {

    /**
     * 忽略地址
     */
    List<String> ignoreUrl = new ArrayList<>();

    /**
     * 特权地址
     */
    List<String> privilegeUrl = new ArrayList<>();

    /**
     * 超级管理员(特权用户)
     */
    List<String> privilegeUser = new ArrayList<>();

}
