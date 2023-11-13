package ${base.basePackage}.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${base.basePackage}.rbac.mapper.RoleResourceMapper;
import ${base.basePackage}.rbac.pojo.RoleResource;
import ${base.basePackage}.rbac.service.IRoleResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description：角色资源关联表服务实现类
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {

    @Override
    public Boolean deleteRoleResourceByRoleId(Long roleId) {
        UpdateWrapper<RoleResource> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(RoleResource::getRoleId,roleId);
        return remove(updateWrapper);
    }

    @Override
    public Boolean deleteRoleResourceInRoleId(List<Long> roleIds) {
        UpdateWrapper<RoleResource> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().in(RoleResource::getRoleId,roleIds);
        return remove(updateWrapper);
    }
}
