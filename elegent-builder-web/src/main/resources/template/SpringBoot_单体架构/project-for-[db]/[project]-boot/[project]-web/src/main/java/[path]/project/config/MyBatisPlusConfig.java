package ${base.basePackage}.project.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
<#if option.datascope=="open"  >
import ${base.basePackage}.datascope.core.DataSecurityLineInnerInterceptor;
import ${base.basePackage}.datascope.handler.DataSecurityLineHandlerImpl;
</#if>
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description：配置文件
 */
@Slf4j
//申明此类为配置类
@Configuration
//扫描mapper接口类
@MapperScans({
        @MapperScan("${base.basePackage}.rbac.mapper"),
        @MapperScan("${base.basePackage}.project.mapper")
})
//读取配置
@EnableConfigurationProperties({MybatisPlusProperties.class})
public class MyBatisPlusConfig {

    /**
     * @Description mybatis提供的主键生成策略【制定雪花】
     */
    @Bean
    public IdentifierGenerator identifierGenerator() {
        return new DefaultIdentifierGenerator();
    }


    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,
     * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		<#if option.datascope=="open"  >
        //数据权限插件
        interceptor.addInnerInterceptor(dataSecurityLineInnerInterceptor() );
		</#if>
        //分页的插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());

        return interceptor;
    }

    //分页的插件配置
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor(DbType.MYSQL);
    }

	<#if option.datascope=="open"  >
    @Bean
    public DataSecurityLineInnerInterceptor dataSecurityLineInnerInterceptor(){
        List<String> tableList=new ArrayList<>();
		//todo: 要拦截的表
        //tableList.add("表名称");
        return new DataSecurityLineInnerInterceptor( new DataSecurityLineHandlerImpl(tableList ));
    }
	</#if>

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }
	

}
