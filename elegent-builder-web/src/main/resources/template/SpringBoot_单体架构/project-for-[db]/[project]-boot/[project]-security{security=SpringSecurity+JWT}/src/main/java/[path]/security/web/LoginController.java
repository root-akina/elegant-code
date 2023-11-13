package ${base.basePackage}.security.web;

import ${base.basePackage}.common.basic.AjaxResult;
import ${base.basePackage}.common.context.SubjectContext;
import ${base.basePackage}.common.enums.UserEnum;
import ${base.basePackage}.common.utils.AjaxResultBuild;
import ${base.basePackage}.common.vo.UserVo;
import ${base.basePackage}.security.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @Autowired
    private ILoginService loginService;

    /**
     * 登录
     * @param userVo
     * @return
     */
    @PostMapping("/login")
    public AjaxResult<UserVo> login(@RequestBody UserVo userVo){
        return AjaxResultBuild.build(UserEnum.SUCCEED ,loginService.login(userVo));
    }

    /**
     * 获取当前登录用户
     * @return
     */
    @GetMapping("/user/current-user")
    public AjaxResult<UserVo> findCurrentUser(){
        return AjaxResultBuild.build( UserEnum.SUCCEED, SubjectContext.getSubject());
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/security/logout")
    public AjaxResult<Boolean> logout(){
        return AjaxResultBuild.build( UserEnum.SUCCEED, loginService.logout());
    }


}
