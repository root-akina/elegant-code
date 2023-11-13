package ${base.basePackage}.security.config;

import ${base.basePackage}.security.core.TokenAuthorizationManager;
import ${base.basePackage}.security.properties.SecurityConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    private TokenAuthorizationManager tokenAuthorizationManager;

    @Autowired
    private SecurityConfigProperties securityConfigProperties;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        List<String> ignoreUrl =   securityConfigProperties.getIgnoreUrl();
        ignoreUrl.add("/login"); //登录
        ignoreUrl.add("/resource/menus/**");//菜单

        http.authorizeHttpRequests()
                .antMatchers( ignoreUrl.toArray(  new String[ignoreUrl.size()] )  ).permitAll()
                .anyRequest().access(tokenAuthorizationManager );
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );//关闭session
        http.headers().cacheControl().disable();
        return http.build();

    }


}
