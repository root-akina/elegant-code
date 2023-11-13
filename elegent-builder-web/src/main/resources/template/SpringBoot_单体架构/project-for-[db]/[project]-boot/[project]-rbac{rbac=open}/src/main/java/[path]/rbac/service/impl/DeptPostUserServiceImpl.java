package ${base.basePackage}.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${base.basePackage}.common.constant.DeptPostUserCacheConstant;
import ${base.basePackage}.common.constant.SuperConstant;
import ${base.basePackage}.common.enums.DeptPostUserEnum;
import ${base.basePackage}.common.exception.ProjectException;
import ${base.basePackage}.rbac.mapper.DeptPostUserMapper;
import ${base.basePackage}.rbac.pojo.DeptPostUser;
import ${base.basePackage}.rbac.service.IDeptPostUserService;
import ${base.basePackage}.rbac.service.IDeptService;
import ${base.basePackage}.common.utils.BeanConv;
import ${base.basePackage}.common.utils.ExceptionsUtil;
import ${base.basePackage}.common.vo.DeptPostUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description：部门岗位用户关联表服务实现类
 */
@Slf4j
@Service
public class DeptPostUserServiceImpl extends ServiceImpl<DeptPostUserMapper, DeptPostUser> implements IDeptPostUserService {

    @Autowired
    IDeptService deptService;

    @Override
    public List<DeptPostUserVo> findDeptPostUserVoListInUserId(List<Long> userIds) {
        try {
            QueryWrapper<DeptPostUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(DeptPostUser::getUserId,userIds)
                    .eq(DeptPostUser::getDataState, SuperConstant.DATA_STATE_0);
            return BeanConv.toBeanList(list(queryWrapper),DeptPostUserVo.class);
        } catch (Exception e) {
            log.error("查询部门职位人员异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DeptPostUserEnum.LIST_FAIL);
        }

    }

    @Override
    @Transactional
    public Boolean deleteDeptPostUserByUserId(Long userId) {
        try {
            UpdateWrapper<DeptPostUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().eq(DeptPostUser::getUserId,userId);
            return remove(updateWrapper);
        } catch (Exception e) {
            log.error("查询部门职位人员异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DeptPostUserEnum.DEL_FAIL);
        }

    }

    @Override
    @Transactional
    public Boolean deleteDeptPostUserInUserId(List<String> userIds) {
        try {
            UpdateWrapper<DeptPostUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().in(DeptPostUser::getUserId,userIds);
            return remove(updateWrapper);
        } catch (Exception e) {
            log.error("查询部门职位人员异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DeptPostUserEnum.DEL_FAIL);
        }
    }

    @Override
    public DeptPostUserVo findDeptPostUserVoByUserId(Long userId) {
        try {
            QueryWrapper<DeptPostUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(DeptPostUser::getUserId,userId)
                    .eq(DeptPostUser::getDataState, SuperConstant.DATA_STATE_0);
            return BeanConv.toBean(getOne(queryWrapper),DeptPostUserVo.class);
        } catch (Exception e) {
            log.error("查询部门职位人员异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DeptPostUserEnum.LIST_FAIL);
        }

    }
}
