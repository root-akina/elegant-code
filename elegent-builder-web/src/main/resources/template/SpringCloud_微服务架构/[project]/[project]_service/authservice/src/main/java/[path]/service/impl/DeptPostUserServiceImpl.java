package ${base.basePackage}.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${base.basePackage}.constant.SuperConstant;
import ${base.basePackage}.mapper.DeptPostUserMapper;
import ${base.basePackage}.pojo.DeptPostUser;
import ${base.basePackage}.service.IDeptPostUserService;
import ${base.basePackage}.service.IDeptService;
import ${base.basePackage}.util.BeanConv;
import ${base.basePackage}.vo.DeptPostUserVo;
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

        QueryWrapper<DeptPostUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(DeptPostUser::getUserId,userIds)
                .eq(DeptPostUser::getDataState, SuperConstant.DATA_STATE_0);
        return BeanConv.toBeanList(list(queryWrapper),DeptPostUserVo.class);
    }

    @Override
    @Transactional
    public Boolean deleteDeptPostUserByUserId(Long userId) {

        UpdateWrapper<DeptPostUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(DeptPostUser::getUserId,userId);
        return remove(updateWrapper);
    }

    @Override
    @Transactional
    public Boolean deleteDeptPostUserInUserId(List<String> userIds) {

        UpdateWrapper<DeptPostUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().in(DeptPostUser::getUserId,userIds);
        return remove(updateWrapper);
    }

    @Override
    public DeptPostUserVo findDeptPostUserVoByUserId(Long userId) {

        QueryWrapper<DeptPostUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(DeptPostUser::getUserId,userId)
                .eq(DeptPostUser::getDataState, SuperConstant.DATA_STATE_0);
        return BeanConv.toBean(getOne(queryWrapper),DeptPostUserVo.class);
    }
}
