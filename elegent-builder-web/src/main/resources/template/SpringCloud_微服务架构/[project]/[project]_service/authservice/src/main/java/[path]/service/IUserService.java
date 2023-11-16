package ${base.basePackage}.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ${base.basePackage}.pojo.User;
import ${base.basePackage}.vo.UserVo;

import java.util.List;

/**
 * @Description：用户表服务类
 */
public interface IUserService extends IService<User> {

    /**
     * @Description 多条件查询用户表分页列表
     * @param userVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<User>
     */
    Page<UserVo> findUserPage(UserVo userVo, int pageNum, int pageSize);

    /**
     * @Description 创建用户表
     * @param userVo 对象信息
     * @return User
     */
    UserVo createUser(UserVo userVo);

    /**
     * @Description 修改用户表
     * @param userVo 对象信息
     * @return Boolean
     */
    Boolean updateUser(UserVo userVo);

    /**
     * @description 多条件查询用户表列表
     * @param userVo 查询条件
     * @return: List<User>
     */
    List<UserVo> findUserList(UserVo userVo);

    /**
     * @description 部门下员工
     * @param deptNo 部门
     * @return: List<User>
     */
    List<UserVo> findUserVoListByDeptNo(String deptNo);

    /**
     * @description 角色下员工
     * @param roleId 角色
     * @return: List<User>
     */
    List<UserVo> findUserVoListByRoleId(Long roleId);

    /***
     * @description 查询用户构建对象
     *
     * @param username
     * @param openId
     * @param mobile
     * @return
     * @return: com.itheima.${base.project}.easy.vo.UserVo
     */
    UserVo findUserVoForLogin(String username, String openId, String mobile);

    /***
     * @description 重置密码
     * @param userId
     * @return
     */
    Boolean resetPasswords(String userId);

    /**
     * 加载用户数据
     * @param userVo
     * @return
     */
    void loadUserData(UserVo userVo);


}
