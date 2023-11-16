package ${base.basePackage}.service.impl;
import cn.elegent.security.common.base.UserDetails;
import cn.elegent.security.token.core.UserDetailsServices;
import ${base.basePackage}.service.IUserService;
import ${base.basePackage}.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class UserDetailServiceImpl implements UserDetailsServices {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username, String type) {
        UserDetails userDetails=new UserDetails();
        UserVo userVo = userService.findUserVoForLogin(username, null, null);
        userService.loadUserData(userVo);
        userDetails.setUserId(userVo.getId()+"");
        userDetails.setSuperUser(true);
        userDetails.setUsername(userVo.getUsername());
        userDetails.setPassword(userVo.getPassword());
        userDetails.setEnabled(true);
        userDetails.setRoles( userVo.getRoleLabels().stream().collect(Collectors.toList()) );
        userDetails.setResources( userVo.getResourceRequestPaths().stream().collect(Collectors.toList()) );
        return userDetails;
    }


}
