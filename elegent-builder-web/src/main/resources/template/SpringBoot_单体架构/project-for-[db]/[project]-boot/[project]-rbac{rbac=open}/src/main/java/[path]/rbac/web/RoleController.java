package ${base.basePackage}.rbac.web;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${base.basePackage}.common.basic.AjaxResult;
import ${base.basePackage}.common.enums.RoleEnum;
import ${base.basePackage}.rbac.service.IRoleService;
import ${base.basePackage}.common.utils.AjaxResultBuild;
import ${base.basePackage}.common.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：角色前端控制器
 */
@Slf4j
@Api(tags = "角色管理")
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    IRoleService roleService;

    /***
     * @description 多条件查询角色分页列表
     * @param roleVo 角色Vo查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return: Page<RoleVo>
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "角色分页",notes = "角色分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "roleVo",value = "角色Vo对象",required = true,dataType = "RoleVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    public AjaxResult<Page<RoleVo>> findRoleVoPage(
                                    @RequestBody RoleVo roleVo,
                                    @PathVariable("pageNum") int pageNum,
                                    @PathVariable("pageSize") int pageSize) {
        Page<RoleVo> roleVoPage = roleService.findRolePage(roleVo, pageNum, pageSize);
        return AjaxResultBuild.build(RoleEnum.SUCCEED,roleVoPage);
    }

    /**
     * @Description 保存角色
     * @param roleVo 角色Vo对象
     * @return RoleVo
     */
    @PutMapping
    @ApiOperation(value = "角色添加",notes = "角色添加")
    @ApiImplicitParam(name = "roleVo",value = "角色Vo对象",required = true,dataType = "RoleVo")
    public AjaxResult<RoleVo> createRole(@RequestBody RoleVo roleVo) {
        RoleVo roleVoResult = roleService.createRole(roleVo);
        return AjaxResultBuild.build(RoleEnum.SUCCEED,roleVoResult);
    }

    /**
     * @Description 修改角色
     * @param roleVo 角色Vo对象
     * @return Boolean 是否修改成功
     */
    @PatchMapping
    @ApiOperation(value = "角色修改",notes = "角色修改")
    @ApiImplicitParam(name = "roleVo",value = "角色Vo对象",required = true,dataType = "RoleVo")
    public AjaxResult<Boolean> updateRole(@RequestBody RoleVo roleVo) {
        Boolean flag = roleService.updateRole(roleVo);
        return AjaxResultBuild.build(RoleEnum.SUCCEED,flag);
    }

    /***
     * @description 多条件查询角色列表
     * @param roleVo 角色Vo对象
     * @return List<RoleVo>
     */
    @PostMapping("list")
    @ApiOperation(value = "角色列表",notes = "角色列表")
    @ApiImplicitParam(name = "roleVo",value = "角色Vo对象",required = true,dataType = "RoleVo")
    public AjaxResult<List<RoleVo>> roleList(@RequestBody RoleVo roleVo) {
        List<RoleVo> roleVoList = roleService.findRoleList(roleVo);
        return AjaxResultBuild.build(RoleEnum.SUCCEED,roleVoList);
    }

}
