package ${base.basePackage}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${base.basePackage}.constant.SuperConstant;
import ${base.basePackage}.pojo.User;
import ${base.basePackage}.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Description：用户表Mapper接口
 */
public interface UserMapper extends BaseMapper<User> {

    @Select({"SELECT",
            "u.id ,",
            "u.user_name,",
            "u.open_id,",
            "u.password,",
            "u.user_type,",
            "u.avatar,",
            "u.nick_name,",
            "u.email,",
            "u.real_name,",
            "u.mobile,",
            "u.sex,",
            "u.data_state,",
            "u.create_by,",
            "u.create_time,",
            "u.update_by,",
            "u.update_time,",
            "u.remark,",
            "dpu.post_no",
            "FROM",
            "tab_dept_post_user dpu",
            "LEFT JOIN tab_user u ON dpu.user_id = u.id",
            "WHERE dpu.post_no = <#noparse>#{postNo}</#noparse> AND u.data_state='"+ SuperConstant.DATA_STATE_0+",",
            })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="open_id", property="openId", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_type", property="userType", jdbcType=JdbcType.VARCHAR),
            @Result(column="avatar", property="avatar", jdbcType=JdbcType.VARCHAR),
            @Result(column="nick_name", property="nickName", jdbcType=JdbcType.VARCHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
            @Result(column="real_name", property="realName", jdbcType=JdbcType.VARCHAR),
            @Result(column="mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
            @Result(column="data_state", property="dataState", jdbcType=JdbcType.CHAR),
            @Result(column="create_by", property="createBy", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_by", property="updateBy", jdbcType=JdbcType.BIGINT),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
            @Result(column="post_no", property="postNo", jdbcType=JdbcType.VARCHAR)
    })
    List<UserVo> findUserVoListByDeptNo(@Param("postNo") String postNo);

    @Select({"SELECT",
            "u.id ,",
            "u.user_name,",
            "u.open_id,",
            "u.password,",
            "u.user_type,",
            "u.avatar,",
            "u.nick_name,",
            "u.email,",
            "u.real_name,",
            "u.mobile,",
            "u.sex,",
            "u.data_state,",
            "u.create_by,",
            "u.create_time,",
            "u.update_by,",
            "u.update_time,",
            "u.remark,",
            "ur.role_id ",
            "FROM ",
            "tab_user_role ur ",
            "LEFT JOIN tab_user u ON ur.user_id = u.id ",
            "WHERE ur.role_id = <#noparse>#{roleId}</#noparse>  AND u.data_state='"+ SuperConstant.DATA_STATE_0+",",
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="open_id", property="openId", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_type", property="userType", jdbcType=JdbcType.VARCHAR),
            @Result(column="avatar", property="avatar", jdbcType=JdbcType.VARCHAR),
            @Result(column="nick_name", property="nickName", jdbcType=JdbcType.VARCHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
            @Result(column="real_name", property="realName", jdbcType=JdbcType.VARCHAR),
            @Result(column="mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
            @Result(column="data_state", property="dataState", jdbcType=JdbcType.CHAR),
            @Result(column="create_by", property="createBy", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_by", property="updateBy", jdbcType=JdbcType.BIGINT),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
            @Result(column="role_id", property="roleId", jdbcType=JdbcType.VARCHAR)
    })
    List<UserVo> findUserVoListByRoleId(@Param("roleId")Long roleId);
}
