package ${base.basePackage}.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ${base.basePackage}.pojo.Resource;
import ${base.basePackage}.vo.MenuVo;
import ${base.basePackage}.vo.ResourceVo;
import ${base.basePackage}.vo.TreeVo;


import java.util.List;

/**
 * @Description：权限表服务类
 */
public interface IResourceService extends IService<Resource> {

    /**
     * @Description 多条件查询权限表分页列表
     * @param resourceVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<Resource>
     */
    Page<ResourceVo> findResourcePage(ResourceVo resourceVo, int pageNum, int pageSize);

    /**
     * @Description 创建权限表
     * @param resourceVo 对象信息
     * @return Resource
     */
    ResourceVo createResource(ResourceVo resourceVo);

    /**
     * @Description 修改权限表
     * @param resourceVo 对象信息
     * @return Boolean
     */
    Boolean updateResource(ResourceVo resourceVo);

    /**
     * @description 多条件查询权限表列表
     * @param resourceVo 查询条件
     * @return: List<Resource>
     */
    List<ResourceVo> findResourceList(ResourceVo resourceVo);

    /**
     * @description 资源树形
     * @param parentResourceNo 根节点
     * @param checkedResourceNos 选择节点
     * @return: TreeVo
     */
    TreeVo resourceTreeVo(String parentResourceNo, String[] checkedResourceNos);

    /**
     * @description 角色对应资源
     * @param roleIds 角色s
     * @return: List<Resource>
     */
    List<ResourceVo> findResourceVoListInRoleId(List<Long> roleIds);

    /***
     * @description 查询左侧菜单
     * @param systemCode 系统编号
     * @return 菜单对象
     */
    List<MenuVo> menus(String systemCode);

    /**
     * @description 员工对应资源
     * @param userId 查询条件
     * @return: List<Resource>
     */
    List<ResourceVo> findResourceVoListByUserId(Long userId);

    /**
     * @Description 创建编号
     * @param parentResourceNo 父部门编号
     * @return
     */
    String createResourceNo(String parentResourceNo);

}
