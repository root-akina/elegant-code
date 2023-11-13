package ${base.basePackage}.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import ${base.basePackage}.common.constant.*;
import ${base.basePackage}.common.enums.RoleEnum;
import ${base.basePackage}.common.exception.ProjectException;
import ${base.basePackage}.rbac.mapper.DeptMapper;
import ${base.basePackage}.rbac.mapper.ResourceMapper;
import ${base.basePackage}.rbac.mapper.RoleMapper;
import ${base.basePackage}.rbac.pojo.Role;
import ${base.basePackage}.rbac.pojo.RoleDept;
import ${base.basePackage}.rbac.pojo.RoleResource;
import ${base.basePackage}.rbac.service.IRoleDeptService;
import ${base.basePackage}.rbac.service.IRoleResourceService;
import ${base.basePackage}.rbac.service.IRoleService;
import ${base.basePackage}.common.utils.BeanConv;
import ${base.basePackage}.common.utils.EmptyUtil;
import ${base.basePackage}.common.utils.ExceptionsUtil;
import ${base.basePackage}.common.vo.DeptVo;
import ${base.basePackage}.common.vo.ResourceVo;
import ${base.basePackage}.common.vo.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description：角色表服务实现类
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    IRoleResourceService roleResourceService;

    @Autowired
    IRoleDeptService roleDeptService;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    DeptMapper deptMapper;

    /***
     * @description 多条件查询
     * @param queryWrapper
     * @param roleVo
     * @return
     */
    private QueryWrapper<Role> queryWrapper(QueryWrapper<Role> queryWrapper,RoleVo roleVo){
        //角色名称查询
        if (!EmptyUtil.isNullOrEmpty(roleVo.getRoleName())) {
            queryWrapper.lambda().likeRight(Role::getRoleName,roleVo.getRoleName());
        }
        //权限标识查询
        if (!EmptyUtil.isNullOrEmpty(roleVo.getLabel())) {
            queryWrapper.lambda().likeRight(Role::getLabel,roleVo.getLabel());
        }
        //排序查询
        if (!EmptyUtil.isNullOrEmpty(roleVo.getSortNo())) {
            queryWrapper.lambda().eq(Role::getSortNo,roleVo.getSortNo());
        }
        //创建者查询
        if (!EmptyUtil.isNullOrEmpty(roleVo.getCreateBy())) {
            queryWrapper.lambda().eq(Role::getCreateBy,roleVo.getCreateBy());
        }
        //更新者查询
        if (!EmptyUtil.isNullOrEmpty(roleVo.getUpdateBy())) {
            queryWrapper.lambda().eq(Role::getUpdateBy,roleVo.getUpdateBy());
        }
        //备注查询
        if (!EmptyUtil.isNullOrEmpty(roleVo.getRemark())) {
            queryWrapper.lambda().eq(Role::getRemark,roleVo.getRemark());
        }
        //状态查询
        if (!EmptyUtil.isNullOrEmpty(roleVo.getDataState())) {
            queryWrapper.lambda().eq(Role::getDataState,roleVo.getDataState());
        }
        //按创建时间降序
        queryWrapper.lambda().orderByAsc(Role::getSortNo);
        return queryWrapper;
    }

    @Override
    public Page<RoleVo> findRolePage(RoleVo roleVo, int pageNum, int pageSize) {
        try {
            //构建分页对象
            Page<Role> page = new Page<>(pageNum,pageSize);
            //构建查询条件
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            //构建多条件查询，代码生成后自己可自行调整
            this.queryWrapper(queryWrapper,roleVo);
            //执行分页查询
            Page<RoleVo> pageVo = BeanConv.toPage(page(page, queryWrapper),RoleVo.class);

            /*
            if (!EmptyUtil.isNullOrEmpty(pageVo.getRecords())){
                List<Long> roleIds = pageVo.getRecords().stream().map(RoleVo::getId).collect(Collectors.toList());
                //查询对应资源
                List<ResourceVo> resourceList = resourceMapper.findResourceVoListInRoleId(roleIds);
                //查询对应数据权限
                //List<DeptVo> deptVoList = deptMapper.findDeptVoListInRoleId(roleIds);
                pageVo.getRecords().forEach(n->{
                    //装配资源
                    /*
                    Set<String> resourceNoSet = Sets.newHashSet();
                    resourceList.forEach(r->{
                        if (String.valueOf(n.getId()).equals(String.valueOf(r.getRoleId()))){
                            resourceNoSet.add(r.getResourceNo());
                        }
                    });
                    if (!EmptyUtil.isNullOrEmpty(resourceNoSet))
                        n.setCheckedResourceNos(resourceNoSet.toArray(new String[resourceNoSet.size()]));
                    //装配数据权限
                    /*
                    Set<String> deptNoSet = Sets.newHashSet();
                    if (!EmptyUtil.isNullOrEmpty(deptVoList)){
                        deptVoList.forEach(d->{
                            if (String.valueOf(n.getId()).equals(String.valueOf(d.getRoleId()))){
                                deptNoSet.add(d.getDeptNo());
                            }
                        });
                        if (!EmptyUtil.isNullOrEmpty(deptNoSet)){
                            n.setCheckedDeptNos(deptNoSet.toArray(new String[deptNoSet.size()]));
                        }
                    }
                });
            }*/
            return pageVo;
        }catch (Exception e){
            log.error("角色表列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(RoleEnum.PAGE_FAIL);
        }

    }

    @Override
    @Transactional
    public RoleVo createRole(RoleVo roleVo) {
        try {
            //转换RoleVo为Role
            Role role = BeanConv.toBean(roleVo, Role.class);
            boolean flag = save(role);
            if (!flag){
                throw  new RuntimeException("保存角色失败");
            }
            //保存角色资源中间信息
            List<RoleResource> roleResourceList = Arrays.asList(roleVo.getCheckedResourceNos()).stream()
                    .map(n -> RoleResource.builder()
                            .roleId(role.getId())
                            .resourceNo(n)
                            .build()).collect(Collectors.toList());
            flag = roleResourceService.saveBatch(roleResourceList);

            if (!flag){
                throw new RuntimeException("保存角色资源信息出错");
            }
            if (flag){
                return BeanConv.toBean(role,RoleVo.class);
            }
            return null;
        } catch (Exception e) {
            log.error("保存角色表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(RoleEnum.SAVE_FAIL);
        }
    }


    @Override
    @Transactional
    public Boolean updateRole(RoleVo roleVo) {
        try {
            //转换RoleVo为Role
            Role role = BeanConv.toBean(roleVo, Role.class);
            Boolean flag = updateById(role);
            if (!flag){
                throw  new RuntimeException("修改角色失败");
            }
            //删除原有角色资源中间信息
            flag = roleResourceService.deleteRoleResourceByRoleId(role.getId());
            if (!flag){
                throw  new RuntimeException("删除原有角色资源中间信息失败");
            }
            //保存角色资源中间信息
            List<RoleResource> roleResourceList = Arrays.asList(roleVo.getCheckedResourceNos()).stream()
                    .map(n -> RoleResource.builder()
                            .roleId(role.getId())
                            .resourceNo(n)
                            .build()).collect(Collectors.toList());
            flag = roleResourceService.saveBatch(roleResourceList);
            if (!flag){
                throw  new RuntimeException("保存角色资源中间信息失败");
            }
            return flag;
        } catch (Exception e) {
            log.error("修改角色表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(RoleEnum.UPDATE_FAIL);
        }
    }

    @Override
    public List<RoleVo> findRoleList(RoleVo roleVo) {
        try {
            //构建查询条件
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            //多条件查询
            this.queryWrapper(queryWrapper,roleVo);
            List<RoleVo> records = BeanConv.toBeanList(list(queryWrapper),RoleVo.class);
            return records;
        } catch (Exception e) {
            log.error("查询角色表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(RoleEnum.LIST_FAIL);
        }

    }

    @Override
    public List<RoleVo> findRoleVoListInUserId(List<Long> userIds) {
        return roleMapper.findRoleVoListInUserId(userIds);
    }

    @Override
    public List<RoleVo> findRoleVoListByUserId(Long userId) {

        try {
            return roleMapper.findRoleVoListByUserId(userId);
        } catch (Exception e) {
            log.error("查询角色表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(RoleEnum.LIST_FAIL);
        }
    }

}
