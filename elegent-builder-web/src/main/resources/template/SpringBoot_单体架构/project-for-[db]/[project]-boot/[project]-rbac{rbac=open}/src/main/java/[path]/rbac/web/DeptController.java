package ${base.basePackage}.rbac.web;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import ${base.basePackage}.common.basic.AjaxResult;
import ${base.basePackage}.common.enums.DeptEnum;
import ${base.basePackage}.rbac.service.IDeptService;
import ${base.basePackage}.common.utils.AjaxResultBuild;
import ${base.basePackage}.common.vo.DeptVo;
import ${base.basePackage}.common.vo.TreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：部门前端控制器
 */
@Slf4j
@Api(tags = "部门管理")
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    IDeptService deptService;

    /***
     * @description 多条件查询部门分页列表
     * @param deptVo 部门Vo查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return: Page<DeptVo>
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "部门分页",notes = "部门分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "deptVo",value = "部门Vo对象",required = true,dataType = "DeptVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    public AjaxResult<Page<DeptVo>> findDeptVoPage(
                                    @RequestBody DeptVo deptVo,
                                    @PathVariable("pageNum") int pageNum,
                                    @PathVariable("pageSize") int pageSize) {
        Page<DeptVo> deptVoPage = deptService.findDeptPage(deptVo, pageNum, pageSize);
        return AjaxResultBuild.build(DeptEnum.SUCCEED,deptVoPage);
    }

    /**
     * @Description 保存部门
     * @param deptVo 部门Vo对象
     * @return DeptVo
     */
    @PutMapping
    @ApiOperation(value = "部门添加",notes = "部门添加")
    @ApiImplicitParam(name = "deptVo",value = "部门Vo对象",required = true,dataType = "DeptVo")
    @ApiOperationSupport(includeParameters ={"deptVo.parentDeptNo",
            "deptVo.deptNo","deptVo.deptName","deptVo.sortNo","deptVo.updateBy","deptVo.createBy"} )
    public AjaxResult<DeptVo> createDept(@RequestBody DeptVo deptVo) {
        DeptVo deptVoResult = deptService.createDept(deptVo);
        return AjaxResultBuild.build(DeptEnum.SUCCEED,deptVoResult);
    }

    /**
     * @Description 修改部门
     * @param deptVo 部门Vo对象
     * @return Boolean 是否修改成功
     */
    @PatchMapping
    @ApiOperation(value = "部门修改",notes = "部门修改")
    @ApiImplicitParam(name = "deptVo",value = "部门Vo对象",required = true,dataType = "DeptVo")
    public AjaxResult<Boolean> updateDept(@RequestBody DeptVo deptVo) {
        Boolean flag = deptService.updateDept(deptVo);
        return AjaxResultBuild.build(DeptEnum.SUCCEED,flag);
    }


    /***
     * @description 多条件查询部门列表
     * @param deptVo 部门Vo对象
     * @return List<DeptVo>
     */
    @PostMapping("list")
    @ApiOperation(value = "部门列表",notes = "部门列表")
    @ApiImplicitParam(name = "deptVo",value = "部门Vo对象",required = true,dataType = "DeptVo")
    public AjaxResult<List<DeptVo>> deptList(@RequestBody DeptVo deptVo) {
        List<DeptVo> deptVoList = deptService.findDeptList(deptVo);
        return AjaxResultBuild.build(DeptEnum.SUCCEED,deptVoList);
    }

    /**
     * @Description 组织部门树形
     * @param deptVo 组织部门对象
     * @return
     */
    @PostMapping("tree")
    @ApiOperation(value = "部门树形",notes = "部门树形")
    @ApiImplicitParam(name = "deptVo",value = "组织部门对象",required = false,dataType = "DeptVo")
    public AjaxResult<TreeVo> deptTreeVo(@RequestBody DeptVo deptVo) {
        TreeVo treeVo = deptService.deptTreeVo(deptVo.getParentDeptNo(),deptVo.getCheckedDeptNos());
        return AjaxResultBuild.build(DeptEnum.SUCCEED,treeVo);
    }

    /**
     * @Description 创建编号
     * @param parentDeptNo 父部门编号
     * @return
     */
    @PostMapping("create-dept-no/{parentDeptNo}")
    @ApiOperation(value = "创建编号",notes = "创建编号")
    @ApiImplicitParam(paramType = "path",name = "preantDeptNo",value = "父编号",required = true,dataType = "String")
    public AjaxResult<String> createDeptNo(@PathVariable("parentDeptNo") String parentDeptNo) {
        String deptNo  = deptService.createDeptNo(parentDeptNo);
        return AjaxResultBuild.build(DeptEnum.SUCCEED,deptNo);
    }

}
