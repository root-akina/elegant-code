package ${base.basePackage}.rbac.web;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${base.basePackage}.common.basic.AjaxResult;
import ${base.basePackage}.common.enums.DeptEnum;
import ${base.basePackage}.common.enums.ResourceEnum;
import ${base.basePackage}.rbac.service.IResourceService;
import ${base.basePackage}.common.utils.AjaxResultBuild;
import ${base.basePackage}.common.vo.MenuVo;
import ${base.basePackage}.common.vo.ResourceVo;
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
 * @Description：资源前端控制器
 */
@Slf4j
@Api(tags = "资源管理")
@RestController
@RequestMapping("resource")
public class ResourceController {

    @Autowired
    IResourceService resourceService;

    /***
     * @description 多条件资源分页查询
     * @param resourceVo 资源Vo查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return: Page<ResourceVo>
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "资源分页",notes = "资源分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "resourceVo",value = "资源Vo对象",required = true,dataType = "ResourceVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    public AjaxResult<Page<ResourceVo>> findResourceVoPage(
                                    @RequestBody ResourceVo resourceVo,
                                    @PathVariable("pageNum") int pageNum,
                                    @PathVariable("pageSize") int pageSize) {
        Page<ResourceVo> resourceVoPage = resourceService.findResourcePage(resourceVo, pageNum, pageSize);
        return AjaxResultBuild.build(ResourceEnum.SUCCEED,resourceVoPage);
    }

    /**
     * @Description 保存资源
     * @param resourceVo 资源Vo对象
     * @return ResourceVo
     */
    @PutMapping
    @ApiOperation(value = "资源添加",notes = "资源添加")
    @ApiImplicitParam(name = "resourceVo",value = "资源Vo对象",required = true,dataType = "ResourceVo")
    public AjaxResult<ResourceVo> createResource(@RequestBody ResourceVo resourceVo) {
        ResourceVo resourceVoResult = resourceService.createResource(resourceVo);
        return AjaxResultBuild.build(ResourceEnum.SUCCEED,resourceVoResult);
    }

    /**
     * @Description 修改资源
     * @param resourceVo 资源Vo对象
     * @return Boolean 是否修改成功
     */
    @PatchMapping
    @ApiOperation(value = "资源修改",notes = "资源修改")
    @ApiImplicitParam(name = "resourceVo",value = "资源Vo对象",required = true,dataType = "ResourceVo")
    public AjaxResult<Boolean> updateResource(@RequestBody ResourceVo resourceVo) {
        Boolean flag = resourceService.updateResource(resourceVo);
        return AjaxResultBuild.build(ResourceEnum.SUCCEED,flag);
    }

    /***
     * @description 多条件查询资源列表
     * @param resourceVo 资源Vo对象
     * @return List<ResourceVo>
     */
    @PostMapping("list")
    @ApiOperation(value = "资源列表",notes = "资源列表")
    @ApiImplicitParam(name = "resourceVo",value = "资源Vo对象",required = true,dataType = "ResourceVo")
    public AjaxResult<List<ResourceVo>> resourceList(@RequestBody ResourceVo resourceVo) {
        List<ResourceVo> resourceVoList = resourceService.findResourceList(resourceVo);
        return AjaxResultBuild.build(ResourceEnum.SUCCEED,resourceVoList);
    }

    /**
     * @Description 资源树形
     * @param resourceVo 资源对象
     * @return
     */
    @PostMapping("tree")
    @ApiOperation(value = "资源树形",notes = "资源树形")
    @ApiImplicitParam(name = "resourceVo",value = "资源对象",required = false,dataType = "ResourceVo")
    public AjaxResult<TreeVo> resourceTreeVo(@RequestBody ResourceVo resourceVo) {
        TreeVo treeVo = resourceService.resourceTreeVo(resourceVo.getParentResourceNo(), resourceVo.getCheckedResourceNos());
        return AjaxResultBuild.build(ResourceEnum.SUCCEED,treeVo);
    }

    /**
     * @Description 左侧菜单
     * @return
     */
    @GetMapping("menus/{systemCode}")
    @ApiOperation(value = "左侧菜单",notes = "左侧菜单")
    @ApiImplicitParam(name = "systemCode",value = "系统code",required = false,dataType = "systemCode")
    public AjaxResult<List<MenuVo>> menus(@PathVariable("systemCode") String systemCode) {
        List<MenuVo> menus = resourceService.menus(systemCode);
        return AjaxResultBuild.build(ResourceEnum.SUCCEED,menus);
    }

    /**
     * @Description 创建编号
     * @param parentResourceNo 父资源编号
     * @return
     */
    @PostMapping("create-resource-no/{parentResourceNo}")
    @ApiOperation(value = "创建编号",notes = "创建编号")
    @ApiImplicitParam(paramType = "path",name = "parentResourceNo",value = "部门编号",required = true,dataType = "String")
    public AjaxResult<String> createResourceNo(@PathVariable("parentResourceNo") String parentResourceNo) {
        String resourceNo  = resourceService.createResourceNo(parentResourceNo);
        return AjaxResultBuild.build(DeptEnum.SUCCEED,resourceNo);
    }

}
