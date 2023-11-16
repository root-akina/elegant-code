package ${base.basePackage}.web;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${base.basePackage}.service.IResourceService;
import ${base.basePackage}.vo.MenuVo;
import ${base.basePackage}.vo.ResourceVo;
import ${base.basePackage}.vo.TreeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：资源前端控制器
 */
@Slf4j
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
    public Page<ResourceVo> findResourceVoPage(
                                    @RequestBody ResourceVo resourceVo,
                                    @PathVariable("pageNum") int pageNum,
                                    @PathVariable("pageSize") int pageSize) {
        Page<ResourceVo> resourceVoPage = resourceService.findResourcePage(resourceVo, pageNum, pageSize);
        return resourceVoPage;
    }

    /**
     * @Description 保存资源
     * @param resourceVo 资源Vo对象
     * @return ResourceVo
     */
    @PutMapping
    public ResourceVo createResource(@RequestBody ResourceVo resourceVo) {
        ResourceVo resourceVoResult = resourceService.createResource(resourceVo);
        return resourceVoResult;
    }

    /**
     * @Description 修改资源
     * @param resourceVo 资源Vo对象
     * @return Boolean 是否修改成功
     */
    @PatchMapping
    public Boolean updateResource(@RequestBody ResourceVo resourceVo) {
        Boolean flag = resourceService.updateResource(resourceVo);
        return flag;
    }

    /***
     * @description 多条件查询资源列表
     * @param resourceVo 资源Vo对象
     * @return List<ResourceVo>
     */
    @PostMapping("list")
    public List<ResourceVo> resourceList(@RequestBody ResourceVo resourceVo) {
        List<ResourceVo> resourceVoList = resourceService.findResourceList(resourceVo);
        return resourceVoList;
    }

    /**
     * @Description 资源树形
     * @param resourceVo 资源对象
     * @return
     */
    @PostMapping("tree")
    public TreeVo resourceTreeVo(@RequestBody ResourceVo resourceVo) {
        TreeVo treeVo = resourceService.resourceTreeVo(resourceVo.getParentResourceNo(), resourceVo.getCheckedResourceNos());
        return treeVo;
    }

    /**
     * @Description 左侧菜单
     * @return
     */
    @GetMapping("menus/{systemCode}")
    public List<MenuVo> menus(@PathVariable("systemCode") String systemCode) {
        List<MenuVo> menus = resourceService.menus(systemCode);
        return  menus;
    }

    /**
     * @Description 创建编号
     * @param parentResourceNo 父资源编号
     * @return
     */
    @PostMapping("create-resource-no/{parentResourceNo}")
    public String createResourceNo(@PathVariable("parentResourceNo") String parentResourceNo) {
        String resourceNo  = resourceService.createResourceNo(parentResourceNo);
        return resourceNo;
    }

}
