package ${base.basePackage}.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${base.basePackage}.mapper.RoleDeptMapper;
import ${base.basePackage}.pojo.RoleDept;
import ${base.basePackage}.service.IRoleDeptService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Description：权限表服务实现类
 */
@Service
public class RoleDeptServiceImpl extends ServiceImpl<RoleDeptMapper, RoleDept> implements IRoleDeptService {

    @Override
    public Boolean deleteRoleDeptByRoleId(Long roleId) {
        UpdateWrapper<RoleDept> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda().eq(RoleDept::getRoleId,roleId);
        return remove(updateWrapper);
    }

    @Override
    public Boolean deleteRoleDeptInRoleId(List<Long> roleIds) {
        UpdateWrapper<RoleDept> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda().in(RoleDept::getRoleId,roleIds);
        return remove(updateWrapper);
    }

    @Override
    public List<RoleDept> findRoleDeptByRoleId(List<Long> roleIds) {
        //根据角色集合 查询部门角色中间表
        QueryWrapper<RoleDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(RoleDept::getRoleId,roleIds);
        return list(queryWrapper);
    }


}
