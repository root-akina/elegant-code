package cn.elegent.builder.config;
import cn.elegent.builder.service.AssistantService;
import cn.elegent.builder.service.DbService;
import cn.elegent.builder.service.DbTypeService;
import cn.elegent.builder.service.TemplateService;
import cn.elegent.builder.service.impl.AssistantServiceImpl;
import cn.elegent.builder.service.impl.DbServiceImpl;
import cn.elegent.builder.service.impl.DbTypeServiceImpl;
import cn.elegent.builder.service.impl.TemplateServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuilderConfig {

    @Bean
    public DbService dbService(){
        return new DbServiceImpl();
    }

    @Bean
    public TemplateService templateService(){
        return new TemplateServiceImpl();
    }

    @Bean
    public DbTypeService dbTypeService(){
        return new DbTypeServiceImpl();
    }

    @Bean
    public AssistantService assistantService(){ return new AssistantServiceImpl();  };

}
