package ${base.basePackage}.web;

import cn.elegent.security.common.base.UserAuth;
import cn.elegent.security.common.base.UserDetails;
import cn.elegent.security.common.context.SubjectContext;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${base.basePackage}.service.IUserService;
import ${base.basePackage}.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description：用户前端控制器
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    IUserService userService;

    /***
     * @description 多条件查询用户分页列表
     * @param userVo 用户Vo查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return: Page<UserVo>
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    public Page<UserVo> findUserVoPage(
                                    @RequestBody UserVo userVo,
                                    @PathVariable("pageNum") int pageNum,
                                    @PathVariable("pageSize") int pageSize) {
        Page<UserVo> userVoPage = userService.findUserPage(userVo, pageNum, pageSize);
        return userVoPage;
    }

    /**
     * @Description 保存用户
     * @param userVo 用户Vo对象
     * @return UserVo
     */
    @PutMapping
    public UserVo createUser(@RequestBody UserVo userVo) {
        UserVo userVoResult = userService.createUser(userVo);
        return userVoResult;
    }

    /**
     * @Description 修改用户
     * @param userVo 用户Vo对象
     * @return Boolean 是否修改成功
     */
    @PatchMapping
    public Boolean updateUser(@RequestBody UserVo userVo) {
        Boolean flag = userService.updateUser(userVo);
        return flag;
    }

    /***
     * @description 多条件查询用户列表
     * @param userVo 用户Vo对象
     * @return List<UserVo>
     */
    @PostMapping("list")
    public List<UserVo> userList(@RequestBody UserVo userVo) {
        List<UserVo> userVoList = userService.findUserList(userVo);
        return userVoList;
    }

    /**
     * @Description 重置密码
     * @param userId 用户Vo对象
     * @return Boolean 是否修改成功
     */
    @PostMapping("reset-passwords/{userId}")
    public Boolean resetPasswords(@PathVariable("userId") String userId) {
        Boolean flag = userService.resetPasswords(userId);
        return flag;
    }


    /**
     * 当前登录用户
     * @return
     */
    @GetMapping("/current-user")
    public UserVo currentUser(){
        UserDetails userDetails = SubjectContext.getSubject();
        UserVo userVo =new UserVo();
        userVo.setUsername( userDetails.getUsername());
        userVo.setNickName(userDetails.getUsername());
        userVo.setRoleLabels(userDetails.getRoles().stream().collect(Collectors.toSet()));
        userVo.setId( Long.valueOf(  userDetails.getUserId() ) );
        userVo.setResourceRequestPaths( userDetails.getResources().stream().collect(Collectors.toSet()) );
        return userVo;
    }


    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    public Boolean logout(){
        return true;
    }

}
