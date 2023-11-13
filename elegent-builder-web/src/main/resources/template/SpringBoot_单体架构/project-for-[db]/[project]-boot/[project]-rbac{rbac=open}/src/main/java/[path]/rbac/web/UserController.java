package ${base.basePackage}.rbac.web;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${base.basePackage}.common.basic.AjaxResult;
import ${base.basePackage}.common.enums.UserEnum;
import ${base.basePackage}.rbac.service.IUserService;
import ${base.basePackage}.common.utils.AjaxResultBuild;
import ${base.basePackage}.common.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：用户前端控制器
 */
@Slf4j
@Api(tags = "用户管理")
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
    @ApiOperation(value = "用户分页",notes = "用户分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userVo",value = "用户Vo对象",required = true,dataType = "UserVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    public AjaxResult<Page<UserVo>> findUserVoPage(
                                    @RequestBody UserVo userVo,
                                    @PathVariable("pageNum") int pageNum,
                                    @PathVariable("pageSize") int pageSize) {
        Page<UserVo> userVoPage = userService.findUserPage(userVo, pageNum, pageSize);
        return AjaxResultBuild.build(UserEnum.SUCCEED,userVoPage);
    }

    /**
     * @Description 保存用户
     * @param userVo 用户Vo对象
     * @return UserVo
     */
    @PutMapping
    @ApiOperation(value = "用户添加",notes = "用户添加")
    @ApiImplicitParam(name = "userVo",value = "用户Vo对象",required = true,dataType = "UserVo")
    public AjaxResult<UserVo> createUser(@RequestBody UserVo userVo) {
        UserVo userVoResult = userService.createUser(userVo);
        return AjaxResultBuild.build(UserEnum.SUCCEED,userVoResult);
    }

    /**
     * @Description 修改用户
     * @param userVo 用户Vo对象
     * @return Boolean 是否修改成功
     */
    @PatchMapping
    @ApiOperation(value = "用户修改",notes = "用户修改")
    @ApiImplicitParam(name = "userVo",value = "用户Vo对象",required = true,dataType = "UserVo")
    public AjaxResult<Boolean> updateUser(@RequestBody UserVo userVo) {
        Boolean flag = userService.updateUser(userVo);
        return AjaxResultBuild.build(UserEnum.SUCCEED,flag);
    }

    /***
     * @description 多条件查询用户列表
     * @param userVo 用户Vo对象
     * @return List<UserVo>
     */
    @PostMapping("list")
    @ApiOperation(value = "用户列表",notes = "用户列表")
    @ApiImplicitParam(name = "userVo",value = "用户Vo对象",required = true,dataType = "UserVo")
    public AjaxResult<List<UserVo>> userList(@RequestBody UserVo userVo) {
        List<UserVo> userVoList = userService.findUserList(userVo);
        return AjaxResultBuild.build(UserEnum.SUCCEED,userVoList);
    }

    /**
     * @Description 重置密码
     * @param userId 用户Vo对象
     * @return Boolean 是否修改成功
     */
    @PostMapping("reset-passwords/{userId}")
    @ApiOperation(value = "密码重置",notes = "密码重置")
    @ApiImplicitParam(paramType = "path",name = "userId",value = "用戶Id",required = true,dataType = "String")
    public AjaxResult<Boolean> resetPasswords(@PathVariable("userId") String userId) {
        Boolean flag = userService.resetPasswords(userId);
        return AjaxResultBuild.build(UserEnum.SUCCEED,flag);
    }

}
