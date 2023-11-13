package ${base.basePackage}.rbac.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ${base.basePackage}.rbac.pojo.Role;
import ${base.basePackage}.common.vo.RoleVo;

import java.util.List;

/**
 * @Description：角色表服务类
 */
public interface IRoleService extends IService<Role> {

    /**
     * @Description 多条件查询角色表分页列表
     * @param roleVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<ResourceVo>
     */
    Page<RoleVo> findRolePage(RoleVo roleVo, int pageNum, int pageSize);

    /**
     * @Description 创建角色表
     * @param roleVo 对象信息
     * @return ResourceVo
     */
    RoleVo createRole(RoleVo roleVo);

    /**
     * @Description 修改角色表
     * @param roleVo 对象信息
     * @return Boolean
     */
    Boolean updateRole(RoleVo roleVo);

    /**
     * @description 多条件查询角色表列表
     * @param roleVo 查询条件
     * @return: List<ResourceVo>
     */
    List<RoleVo> findRoleList(RoleVo roleVo);

    /***
     * @description 员工们对应角色
     * @param userIds
     * @return
     */
    List<RoleVo> findRoleVoListInUserId(List<Long> userIds);

    /**
     * @description 员工对应角色
     * @param userId 查询条件
     * @return: List<Role>
     */
    List<RoleVo> findRoleVoListByUserId(Long userId);

}
