package ${base.basePackage}.feign;
import ${base.basePackage}.feign.fallback.${db.className}ServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 微服务feign接口
 */
@FeignClient(value = "${db.serviceName}-service",fallbackFactory = ${db.className}ServiceFallbackFactory.class)
public interface ${db.className}Service{


}
