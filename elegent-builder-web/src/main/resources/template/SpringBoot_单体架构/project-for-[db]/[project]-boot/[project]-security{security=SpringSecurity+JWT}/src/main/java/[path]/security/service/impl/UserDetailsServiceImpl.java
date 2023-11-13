package ${base.basePackage}.security.service.impl;

import ${base.basePackage}.common.vo.UserVo;
<#if option.rbac=="open">
import ${base.basePackage}.rbac.service.IUserService;
</#if>
import ${base.basePackage}.security.basic.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	<#if option.rbac=="open">
	
    @Autowired
    IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户
        UserVo userVo = userService.findUserVoForLogin(username, null, null);
		userService.loadUserData(userVo);
        return new UserAuth(userVo);
    }

	<#else>
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户
        UserVo userVo = new UserVo(); //此处代码，改为从数据中查询用户对象
        return new UserAuth(userVo);
    }

	</#if>

    


}
