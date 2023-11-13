package ${base.basePackage}.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ${base.basePackage}.rbac.pojo.DeptPostUser;
import ${base.basePackage}.common.vo.DeptPostUserVo;

import java.util.List;

/**
 * @Description：部门岗位用户关联表服务类
 */
public interface IDeptPostUserService extends IService<DeptPostUser> {

    /**
     * @description 用户集合对应的部门岗位用户关联表列表
     * @param userIds 查询条件
     * @return: List<DeptPostUser>
     */
    List<DeptPostUserVo> findDeptPostUserVoListInUserId(List<Long> userIds);

    /***
     * @description 删除用户的部门职位
     * @param userId 用户id
     * @return
     */
    Boolean deleteDeptPostUserByUserId(Long userId);

    /***
     * @description 删除用户IDS的部门职位
     * @param userIds 用户id
     * @return
     */
    Boolean deleteDeptPostUserInUserId(List<String> userIds);


    /**
     * @description 用户的默认部门
     * @param userId 查询条件
     * @return: List<DeptPostUser>
     */
    DeptPostUserVo findDeptPostUserVoByUserId(Long userId);
}
