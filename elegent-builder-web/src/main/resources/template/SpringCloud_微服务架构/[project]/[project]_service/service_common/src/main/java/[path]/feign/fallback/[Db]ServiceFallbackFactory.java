package ${base.basePackage}.feign.fallback;
import ${base.basePackage}.feign.${db.className}Service;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class ${db.className}ServiceFallbackFactory implements FallbackFactory<${db.className}Service> {
    @Override
    public ${db.className}Service create(Throwable throwable) {
        return new ${db.className}Service() {


        };
    }
}
