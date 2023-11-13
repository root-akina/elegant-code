package ${base.basePackage}.rbac.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ${base.basePackage}.rbac.pojo.Dept;
import ${base.basePackage}.common.vo.DeptVo;
import ${base.basePackage}.common.vo.TreeVo;

import java.util.List;
import java.util.Set;

/**
 * @Description：部门表服务类
 */
public interface IDeptService extends IService<Dept> {

    /**
     * @Description 多条件查询部门表分页列表
     * @param deptVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<DeptVo>
     */
    Page<DeptVo> findDeptPage(DeptVo deptVo, int pageNum, int pageSize);

    /**
     * @Description 创建部门表
     * @param deptVo 对象信息
     * @return DeptVo
     */
    DeptVo createDept(DeptVo deptVo);

    /**
     * @Description 修改部门表
     * @param deptVo 对象信息
     * @return Boolean
     */
    Boolean updateDept(DeptVo deptVo);


    /**
     * @description 多条件查询部门表列表
     * @param deptVo 查询条件
     * @return: List<DeptVo>
     */
    List<DeptVo> findDeptList(DeptVo deptVo);

    /**
     * @description 组织部门树形
     * @param parentDeptNo 根节点
     * @param checkedDeptNos 选择节点
     * @return: TreeVo
     */
    TreeVo deptTreeVo(String parentDeptNo,String[] checkedDeptNos);

    /**
     * @description 批量查詢部門
     * @param deptNos 查询条件
     * @return: TreeVo
     */
    List<DeptVo> findDeptInDeptNos(Set<String> deptNos);

    /**
     * @description 员工对应部门
     * @param userId 员工
     * @return: List<Dept>
     */
    List<DeptVo> findDeptVoListByUserId(Long userId);

    /**
     * @Description 创建编号
     * @param parentDeptNo 父部门编号
     * @return
     */
    String createDeptNo(String parentDeptNo);

    /***
     * @description 角色对应部门
     * @param roleIds
     * @return
     */
    List<DeptVo> findDeptVoListInRoleId(List<Long> roleIds);


    /**
     * 根据部门编号，查询本部门及以下部门的部门编号
     * @param parentDeptNo
     * @return
     */
    List<String> findBelowDeptNo( String parentDeptNo );

}
