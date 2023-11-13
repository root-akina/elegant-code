package ${base.basePackage}.rbac.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ${base.basePackage}.rbac.pojo.Post;
import ${base.basePackage}.common.vo.PostVo;

import java.util.List;

/**
 * @Description：岗位表服务类
 */
public interface IPostService extends IService<Post> {

    /**
     * @Description 多条件查询岗位表分页列表
     * @param postVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Page<PostVo>
     */
    Page<PostVo> findPostPage(PostVo postVo, int pageNum, int pageSize);

    /**
     * @Description 创建岗位表
     * @param postVo 对象信息
     * @return PostVo
     */
    PostVo createPost(PostVo postVo);

    /**
     * @Description 修改岗位表
     * @param postVo 对象信息
     * @return Boolean
     */
    Boolean updatePost(PostVo postVo);

    /**
     * @description 多条件查询岗位表列表
     * @param postVo 查询条件
     * @return: List<PostVo>
     */
    List<PostVo> findPostList(PostVo postVo);

    /**
     * @description 人员对应职位
     * @param userId 查询条件
     * @return: List<PostVo>
     */
    List<PostVo> findPostVoListByUserId(Long userId);

    /**
     * @Description 创建编号
     * @param deptNo 部门编号
     * @return
     */
    String createPostNo(String deptNo);
}
