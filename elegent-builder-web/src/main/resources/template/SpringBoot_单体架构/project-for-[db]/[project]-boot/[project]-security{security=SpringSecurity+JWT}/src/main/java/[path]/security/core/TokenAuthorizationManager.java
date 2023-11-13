package ${base.basePackage}.security.core;

import com.alibaba.fastjson.JSON;
import ${base.basePackage}.common.constant.UserCacheConstant;
import ${base.basePackage}.common.context.SubjectContext;
import ${base.basePackage}.common.utils.BeanConv;
import ${base.basePackage}.common.utils.EmptyUtil;
import ${base.basePackage}.common.vo.UserVo;
import ${base.basePackage}.security.properties.SecurityConfigProperties;
import ${base.basePackage}.security.properties.TokenProperties;
import ${base.basePackage}.security.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * 自定义授权管理器
 */
@Component
public class TokenAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private TokenProperties tokenProperties;


    private AntPathMatcher antPathMatcher=new AntPathMatcher();

    @Autowired
    private SecurityConfigProperties securityConfigProperties;


    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {

        //1.获取token
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        String token = request.getHeader(tokenProperties.getHeaderName());
        if(EmptyUtil.isNullOrEmpty(token)) {
            return new AuthorizationDecision(false);
        }

        //2.验证token
        Object object = JWTUtils.verifyJWT(token, tokenProperties.getSecretKey());
        if( EmptyUtil.isNullOrEmpty( object) ){
            return new AuthorizationDecision(false);
        }
        //UserVo userVo = BeanConv.toBean(object, UserVo.class); //小的user
        UserVo userVo = JSON.parseObject(  JSON.toJSONString( object ),UserVo.class );

        //4.将认证对象放入上下文
        SubjectContext.setSubject(userVo);


        //5.地址拦截
        //特权用户判断
        if(securityConfigProperties.getPrivilegeUser().contains( userVo.getUsername() )){
            return new AuthorizationDecision(true);
        }

        Set<String> resourceRequestPaths = userVo.getResourceRequestPaths(); //用户拥有的地址资源
        List<String> privilegeUrl = securityConfigProperties.getPrivilegeUrl();
        privilegeUrl.add("GET/user/current-user");
        privilegeUrl.add("POST/security/logout");
        resourceRequestPaths.addAll( privilegeUrl);//追加特权地址


        String requestURI = request.getRequestURI();  //  //list
        String method = request.getMethod();  //  GET  POST  PUT  DELETE
        String targetUrl=  method+ requestURI;  //  POST//list

        for( String path:  resourceRequestPaths ) {  //list/**  ant表达式
            boolean match = antPathMatcher.match(path, targetUrl);
            if(match){
                return new AuthorizationDecision(true);
            }
        }

        return new AuthorizationDecision(false);
    }
}
