package ${base.basePackage}.security.service.impl;
import com.alibaba.fastjson.JSON;
import ${base.basePackage}.common.vo.UserVo;
import ${base.basePackage}.security.basic.UserAuth;
import ${base.basePackage}.security.properties.TokenProperties;
import ${base.basePackage}.security.service.ILoginService;
import ${base.basePackage}.security.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    TokenProperties tokenProperties;


    @Override
    public UserVo login(UserVo user) {

        //1.验证用户
        //1.1 调用认证管理器认证用户
        UsernamePasswordAuthenticationToken authentication
                =new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);

        if(!authenticate.isAuthenticated()) return null;  //未通过验证

        //1.2 提取用户
        UserAuth userAuth = (UserAuth)authenticate.getPrincipal();
        //UserVo userVo = BeanConv.toBean(userAuth, UserVo.class);
        UserVo userVo = JSON.parseObject(  JSON.toJSONString( userAuth ),UserVo.class );
        //2.签发令牌
        String token  = JWTUtils.createJWT(userVo.getId() + "", JSON.parseObject(JSON.toJSONString(userVo), Map.class), tokenProperties.getSecretKey(), tokenProperties.getTtl(), TimeUnit.HOURS);
        userVo.setUserToken( token );
        userVo.setPassword("");

        return userVo;
    }

    @Override
    public Boolean logout() {

        return true;
    }
}
