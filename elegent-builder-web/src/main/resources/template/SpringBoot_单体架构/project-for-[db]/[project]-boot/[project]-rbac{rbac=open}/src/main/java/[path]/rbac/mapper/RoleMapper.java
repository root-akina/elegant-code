package ${base.basePackage}.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${base.basePackage}.common.constant.SuperConstant;
import ${base.basePackage}.rbac.pojo.Role;
import ${base.basePackage}.common.vo.RoleVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Description：角色表Mapper接口
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select({"<script>",
            "SELECT",
            "r.id,",
            "r.role_name,",
            "r.label,",
            "r.sort_no,",
            "r.data_state,",
            "r.create_by,",
            "r.create_time,",
            "r.update_by,",
            "r.update_time,",
            "ur.user_id,",
            "r.remark ",
            "FROM ",
            "tab_user_role ur ",
            "LEFT JOIN tab_role r ON ur.role_id = r.id ",
            "WHERE r.data_state = '"+ SuperConstant.DATA_STATE_0+"' ",
            "AND ur.user_id IN (" ,
            "<foreach collection='userIds' separator=',' item='userId'>",
            "<#noparse>#{userId}</#noparse>",
            "</foreach> ",
            ")</script>"})
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
            @Result(column="label", property="label", jdbcType=JdbcType.VARCHAR),
            @Result(column="sort_no", property="sortNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="data_state", property="dataState", jdbcType=JdbcType.CHAR),
            @Result(column="create_by", property="createBy", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_by", property="updateBy", jdbcType=JdbcType.BIGINT),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    List<RoleVo> findRoleVoListInUserId(@Param("userIds")List<Long> userIds);

    @Select({"SELECT",
            "r.id,",
            "r.role_name,",
            "r.label,",
            "r.sort_no,",
            "r.data_state,",
            "r.create_by,",
            "r.create_time,",
            "r.update_by,",
            "r.update_time,",
            "r.remark ",
            "FROM ",
            "tab_role_resource rr ",
            "LEFT JOIN tab_role r ON rr.role_id = r.id ",
            "WHERE r.data_state = '"+ SuperConstant.DATA_STATE_0+"' ",
            "AND rr.resource_no = <#noparse>#{resourceNo}</#noparse>"})
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
            @Result(column="label", property="label", jdbcType=JdbcType.VARCHAR),
            @Result(column="sort_no", property="sortNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="data_state", property="dataState", jdbcType=JdbcType.CHAR),
            @Result(column="create_by", property="createBy", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_by", property="updateBy", jdbcType=JdbcType.BIGINT),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    List<RoleVo> findRoleVoListByResourceNo(String resourceNo);

    @Select({"SELECT",
            "r.id,",
            "r.role_name,",
            "r.label,",
            "r.sort_no,",
            "r.data_state,",
            "r.create_by,",
            "r.create_time,",
            "r.update_by,",
            "r.update_time,",
            "r.remark,",
            "r.data_scope ",
            "FROM ",
            "tab_user_role ur ",
            "LEFT JOIN tab_role r ON ur.role_id = r.id ",
            "WHERE r.data_state = '"+ SuperConstant.DATA_STATE_0+"' ",
            "AND ur.user_id = <#noparse>#{userId}</#noparse>"})
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
            @Result(column="label", property="label", jdbcType=JdbcType.VARCHAR),
            @Result(column="sort_no", property="sortNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="data_state", property="dataState", jdbcType=JdbcType.CHAR),
            @Result(column="create_by", property="createBy", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_by", property="updateBy", jdbcType=JdbcType.BIGINT),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
            @Result(column="data_scope", property="dataScope", jdbcType=JdbcType.VARCHAR)
    })
    List<RoleVo> findRoleVoListByUserId(@Param("userId") Long userId);
}
