package ${base.basePackage}.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import ${base.basePackage}.common.constant.*;
import ${base.basePackage}.common.vo.*;
import ${base.basePackage}.common.enums.UserEnum;
import ${base.basePackage}.common.exception.ProjectException;
import ${base.basePackage}.rbac.mapper.RoleMapper;
import ${base.basePackage}.rbac.mapper.UserMapper;
import ${base.basePackage}.rbac.pojo.DeptPostUser;
import ${base.basePackage}.rbac.pojo.RoleDept;
import ${base.basePackage}.rbac.pojo.User;
import ${base.basePackage}.rbac.pojo.UserRole;
import ${base.basePackage}.rbac.properties.RBACConfigProperties;
import ${base.basePackage}.rbac.service.*;
import ${base.basePackage}.common.utils.BeanConv;
import ${base.basePackage}.common.utils.EmptyUtil;
import ${base.basePackage}.common.utils.ExceptionsUtil;
import ${base.basePackage}.common.utils.NoProcessing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description：用户表服务实现类
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    IDeptPostUserService deptPostUserService;


    @Autowired
    IRoleService roleService;

    @Autowired
    IResourceService resourceService;

    @Autowired
    IRoleDeptService roleDeptService;

    @Autowired
    RBACConfigProperties rbacConfigProperties;

    @Autowired
    IDeptService deptService;

    /***
     * @description 构建多条件查询
     *
     * @param queryWrapper 查询条件
     * @param userVo 查询对象
     * @return
     */
    private QueryWrapper<User> queryWrapper(QueryWrapper<User> queryWrapper, UserVo userVo){
        //角色Id查询
        if (!EmptyUtil.isNullOrEmpty(userVo.getRoleId())){
            QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
            userRoleQueryWrapper.lambda().eq(UserRole::getRoleId,userVo.getRoleId());
            List<UserRole> roles = userRoleService.list(userRoleQueryWrapper);
            if (!EmptyUtil.isNullOrEmpty(roles)){
                List<Long> userIds = roles.stream().map(UserRole::getUserId).collect(Collectors.toList());
                queryWrapper.lambda().in(User::getId,userIds);
            }else {
                queryWrapper.lambda().in(User::getId,-1L);
            }
        }
        //部门No查询
        if (!EmptyUtil.isNullOrEmpty(userVo.getDeptNo())){
            QueryWrapper<DeptPostUser> deptPostUserQueryWrapper = new QueryWrapper<>();
            deptPostUserQueryWrapper.lambda().likeRight(DeptPostUser::getDeptNo,NoProcessing.processString(userVo.getDeptNo()));
            List<DeptPostUser> deptPostUsers = deptPostUserService.list(deptPostUserQueryWrapper);
            if (!EmptyUtil.isNullOrEmpty(deptPostUsers)){
                List<Long> userIds= deptPostUsers.stream().map(DeptPostUser::getUserId).collect(Collectors.toList());
                queryWrapper.lambda().in(User::getId,userIds);
            }else {
                queryWrapper.lambda().in(User::getId,-1L);
            }
        }
        //用户账号查询
        if (!EmptyUtil.isNullOrEmpty(userVo.getUsername())) {
            queryWrapper.lambda().eq(User::getUsername,userVo.getUsername());
        }
        //open_id标识查询
        if (!EmptyUtil.isNullOrEmpty(userVo.getOpenId())) {
            queryWrapper.lambda().eq(User::getOpenId,userVo.getOpenId());
        }
        //用户类型（0:系统用户,1:客户）
        if (!EmptyUtil.isNullOrEmpty(userVo.getUserType())) {
            queryWrapper.lambda().eq(User::getUserType,userVo.getUserType());
        }
        //用户昵称查询
        if (!EmptyUtil.isNullOrEmpty(userVo.getNickName())) {
            queryWrapper.lambda().likeRight(User::getNickName,userVo.getNickName());
        }
        //用户邮箱查询
        if (!EmptyUtil.isNullOrEmpty(userVo.getEmail())) {
            queryWrapper.lambda().likeRight(User::getEmail,userVo.getEmail());
        }
        //真实姓名查询
        if (!EmptyUtil.isNullOrEmpty(userVo.getRealName())) {
            queryWrapper.lambda().likeRight(User::getRealName,userVo.getRealName());
        }
        //手机号码查询
        if (!EmptyUtil.isNullOrEmpty(userVo.getMobile())) {
            queryWrapper.lambda().likeRight(User::getMobile,userVo.getMobile());
        }
        //用户性别（0男 1女 2未知）查询
        if (!EmptyUtil.isNullOrEmpty(userVo.getSex())) {
            queryWrapper.lambda().eq(User::getSex,userVo.getSex());
        }
        //创建者查询
        if (!EmptyUtil.isNullOrEmpty(userVo.getCreateBy())) {
            queryWrapper.lambda().eq(User::getCreateBy,userVo.getCreateBy());
        }
        //更新者查询
        if (!EmptyUtil.isNullOrEmpty(userVo.getUpdateBy())) {
            queryWrapper.lambda().eq(User::getUpdateBy,userVo.getUpdateBy());
        }
        //备注查询
        if (!EmptyUtil.isNullOrEmpty(userVo.getRemark())) {
            queryWrapper.lambda().eq(User::getRemark,userVo.getRemark());
        }
        //状态查询
        if (!EmptyUtil.isNullOrEmpty(userVo.getDataState())) {
            queryWrapper.lambda().eq(User::getDataState,userVo.getDataState());
        }
        //按创建时间降序
        queryWrapper.lambda().orderByDesc(User::getCreateTime);
        return queryWrapper;
    }

    @Override
    public Page<UserVo> findUserPage(UserVo userVo, int pageNum, int pageSize) {
        try {
            //构建分页对象
            Page<User> page = new Page<>(pageNum,pageSize);
            //构建查询条件
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            //多条件查询
            this.queryWrapper(queryWrapper,userVo);
            //执行分页查询
            Page<UserVo> pageVo = BeanConv.toPage(page(page, queryWrapper),UserVo.class);
            if (!EmptyUtil.isNullOrEmpty(pageVo.getRecords())){
                List<Long> userIds = pageVo.getRecords().stream().map(UserVo::getId).collect(Collectors.toList());
                //查询对应角色
                List<RoleVo> roleVoList = roleMapper.findRoleVoListInUserId(userIds);
                //查询对应部门、职位
                List<DeptPostUserVo> deptPostUserVoList = deptPostUserService.findDeptPostUserVoListInUserId(userIds);
                //查询对应部门、职位
                pageVo.getRecords().forEach(n->{
                    //装配角色
                    Set<String> roleVoIds = roleVoList.stream()
                            .filter(r -> n.getId().equals(r.getUserId()))
                            .map(r -> String.valueOf(r.getId())).collect(Collectors.toSet());
                    n.setRoleVoIds(roleVoIds);
                    //装配对应部门、职位、数据权限
                    Set<DeptPostUserVo> deptPostUserVoSet = deptPostUserVoList.stream()
                            .filter(r -> n.getId().equals(r.getUserId()))
                            .collect(Collectors.toSet());
                    n.setDeptPostUserVoSet(deptPostUserVoSet);
                });
            }
            return pageVo;
        }catch (Exception e){
            log.error("用户表列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.PAGE_FAIL);
        }

    }


    @Override
    @Transactional
    public UserVo createUser(UserVo userVo) {
        try {
            String password = rbacConfigProperties.getDefaultPassword();
            userVo.setPassword(password);
            //转换UserVo为User
            User user = BeanConv.toBean(userVo, User.class);
            boolean flag = save(user);
            if (!flag){
                throw new RuntimeException("保存用户信息出错");
            }
            //保存用户角色中间表
            List<UserRole> userRoles = userVo.getRoleVoIds().stream()
                    .map(r -> UserRole.builder().userId(user.getId()).roleId(Long.valueOf(r)).build())
                    .collect(Collectors.toList());

            flag = userRoleService.saveBatch(userRoles);
            if (!flag){
                throw new RuntimeException("保存用户角色中间表出错");
            }
            //保存部门职位中间表
            List<DeptPostUser> deptPostUsers = userVo.getDeptPostUserVoSet().stream()
                    .map(dpu -> {
                        dpu.setUserId(user.getId());
                        return BeanConv.toBean(dpu, DeptPostUser.class);
                    }).collect(Collectors.toList());
            flag = deptPostUserService.saveBatch(deptPostUsers);

            if (!flag){
                throw new RuntimeException("保存部门职位中间表出错");
            }
            return BeanConv.toBean(user, UserVo.class);
        } catch (Exception e) {
            log.error("保存用户表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.SAVE_FAIL);
        }

    }

    @Override
    @Transactional
    public Boolean updateUser(UserVo userVo) {
        try {
            //转换UserVo为User
            User user = BeanConv.toBean(userVo, User.class);
            boolean flag = updateById(user);
            if (!flag){
                throw new RuntimeException("修改用户信息出错");
            }
            //删除角色中间表
            flag = userRoleService.deleteUserRoleByUserId(user.getId());
            if (!flag){
                throw new RuntimeException("删除角色中间表出错");
            }
            //重新保存角色中间表
            List<UserRole> userRoles = userVo.getRoleVoIds().stream()
                    .map(r -> UserRole.builder().userId(user.getId()).roleId(Long.valueOf(r)).build())
                    .collect(Collectors.toList());
            userRoleService.saveBatch(userRoles);
            //删除部门职位中间表
            flag = deptPostUserService.deleteDeptPostUserByUserId(user.getId());
            if (!flag){
                throw new RuntimeException("删除角色中间表出错");
            }
            //重新保存部门职位中间表
            List<DeptPostUser> deptPostUsers = userVo.getDeptPostUserVoSet().stream()
                    .map(dpu -> {
                        dpu.setUserId(user.getId());
                        return BeanConv.toBean(dpu, DeptPostUser.class);
                    }).collect(Collectors.toList());
            flag = deptPostUserService.saveBatch(deptPostUsers);
            if (!flag){
                throw new RuntimeException("保存部门职位中间表出错");
            }
            return flag;
        } catch (Exception e) {
            log.error("修改用户表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.UPDATE_FAIL);
        }

    }

    @Override
    public List<UserVo> findUserList(UserVo userVo) {
        try {
            //构建查询条件
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            //构建多条件查询
            this.queryWrapper(queryWrapper,userVo);
            List<UserVo> records = BeanConv.toBeanList(list(queryWrapper),UserVo.class);
            if (!EmptyUtil.isNullOrEmpty(records)){
                List<Long> userIds = records.stream().map(UserVo::getId).collect(Collectors.toList());
                //查询对应角色
                List<RoleVo> roleVoList = roleMapper.findRoleVoListInUserId(userIds);
                //查询对应部门、职位
                List<DeptPostUserVo> deptPostUserVoList = deptPostUserService.findDeptPostUserVoListInUserId(userIds);
                //查询对应部门、职位
                records.forEach(n->{
                    //装配角色
                    Set<String> roleVoIds = roleVoList.stream()
                            .filter(r -> n.getId().equals(r.getUserId()))
                            .map(r -> String.valueOf(r.getId())).collect(Collectors.toSet());
                    n.setRoleVoIds(roleVoIds);
                    //装配对应部门、职位、数据权限
                    Set<DeptPostUserVo> deptPostUserVoSet = deptPostUserVoList.stream()
                            .filter(r -> n.getId().equals(r.getUserId()))
                            .collect(Collectors.toSet());
                    n.setDeptPostUserVoSet(deptPostUserVoSet);
                });
            }
            return records;
        } catch (Exception e) {
            log.error("查询用户表列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.LIST_FAIL);
        }
    }

    @Override
    public  List<UserVo> findUserVoListByDeptNo(String deptNo) {
        try {
            return userMapper.findUserVoListByDeptNo(deptNo);
        } catch (Exception e) {
            log.error("查询用户表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.LIST_FAIL);
        }
    }

    @Override
    public List<UserVo> findUserVoListByRoleId(Long roleId) {
        try {
            return userMapper.findUserVoListByRoleId(roleId);
        } catch (Exception e) {
            log.error("查询用户表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.LIST_FAIL);
        }

    }

    @Override
    public UserVo findUserVoForLogin(String username, String openId, String mobile) {
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(User::getDataState, SuperConstant.DATA_STATE_0);
            if (!EmptyUtil.isNullOrEmpty(username)){
                queryWrapper.lambda().eq(User::getUsername,username);
            }else if (!EmptyUtil.isNullOrEmpty(openId)){
                queryWrapper.lambda().eq(User::getOpenId,openId);
            }else if (!EmptyUtil.isNullOrEmpty(mobile)){
                queryWrapper.lambda().eq(User::getMobile,mobile);
            }else {
                throw new RuntimeException("未提供对应用户");
            }
            return BeanConv.toBean(getOne(queryWrapper),UserVo.class);
        } catch (Exception e) {
            log.error("查询用户表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(UserEnum.LIST_FAIL);
        }
    }

    @Override
    public Boolean resetPasswords(String userId) {
        String password = rbacConfigProperties.getDefaultPassword();
        User user = User.builder().id(Long.valueOf(userId)).password(password).build();
        return updateById(user);
    }



    @Override
    public void loadUserData(UserVo userVoResult) {

        //用户当前角色
        List<RoleVo> roleVoList = roleService.findRoleVoListByUserId(userVoResult.getId());
        Set<String> roleLabels = roleVoList.stream().map(RoleVo::getLabel).collect(Collectors.toSet());
        userVoResult.setRoleLabels(roleLabels);

        //用户当前资源
        List<ResourceVo> resourceVoList = resourceService.findResourceVoListByUserId(userVoResult.getId());
        Set<String> requestPaths = resourceVoList.stream().map(ResourceVo::getRequestPath).collect(Collectors.toSet());
        userVoResult.setResourceRequestPaths(requestPaths);

        //查询用户部门、职位
        DeptPostUserVo defaultDeptPostUserVo = deptPostUserService.findDeptPostUserVoByUserId(userVoResult.getId());
        if (!EmptyUtil.isNullOrEmpty(defaultDeptPostUserVo)){
            userVoResult.setDeptNo(defaultDeptPostUserVo.getDeptNo());
            userVoResult.setPostNo(defaultDeptPostUserVo.getPostNo());
        }


    }






}
