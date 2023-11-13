package ${base.basePackage}.security.properties;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @Description：jw配置文件
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "${base.project}.framework.jwt")
@Configuration
public class TokenProperties implements Serializable {

    /**
     * @Description 有效时间
     */
    private Integer ttl;

    /**
     * 对称加密密钥
     */
    private String secretKey;

    /**
     * 获取请求头的名字
     */
    private String headerName;

}
