package ${base.basePackage}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${base.basePackage}.constant.SuperConstant;
import ${base.basePackage}.pojo.Post;
import ${base.basePackage}.vo.PostVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Description：岗位表Mapper接口
 */
public interface PostMapper extends BaseMapper<Post> {

    @Select({"SELECT ",
            "p.id,",
            "p.dept_no,",
            "p.post_no,",
            "p.post_name,",
            "p.sort_no,",
            "p.data_state,",
            "p.create_by,",
            "p.create_time,",
            "p.update_by,",
            "p.update_time,",
            "p.remark,",
            "FROM ",
            "tab_dept_post_user dpu ",
            "LEFT JOIN tab_post p ON dpu.post_no = p.post_no ",
            "WHERE p.data_state = '"+ SuperConstant.DATA_STATE_0+"' ",
            "AND dpu.user_id = <#noparse>#{userId}</#noparse>"})
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="dept_no", property="deptNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="post_no", property="postNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="post_name", property="postName", jdbcType=JdbcType.VARCHAR),
            @Result(column="sort_no", property="sortNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="data_state", property="dataState", jdbcType=JdbcType.CHAR),
            @Result(column="create_by", property="createBy", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_by", property="updateBy", jdbcType=JdbcType.BIGINT),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    List<PostVo> findPostVoListByUserId(@Param("userId") Long userId);
}
