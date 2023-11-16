package ${base.basePackage}.web;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${base.basePackage}.service.IPostService;
import ${base.basePackage}.vo.PostVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：岗位前端控制器
 */
@Slf4j
@RestController
@RequestMapping("post")
public class PostController {

    @Autowired
    IPostService postService;

    /***
     * @description 多条件查询岗位分页列表
     * @param postVo 岗位Vo查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return: Page<PostVo>
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    public Page<PostVo> findPostVoPage(
                                    @RequestBody PostVo postVo,
                                    @PathVariable("pageNum") int pageNum,
                                    @PathVariable("pageSize") int pageSize) {
        Page<PostVo> postVoPage = postService.findPostPage(postVo, pageNum, pageSize);
        return postVoPage;
    }

    /**
     * @Description 保存岗位
     * @param postVo 岗位Vo对象
     * @return PostVo
     */
    @PutMapping
    public PostVo createPost(@RequestBody PostVo postVo) {
        PostVo postVoResult = postService.createPost(postVo);
        return postVoResult;
    }

    /**
     * @Description 修改岗位
     * @param postVo 岗位Vo对象
     * @return Boolean 是否修改成功
     */
    @PatchMapping
    public Boolean updatePost(@RequestBody PostVo postVo) {
        Boolean flag = postService.updatePost(postVo);
        return flag;
    }

    /***
     * @description 多条件查询岗位列表
     * @param postVo 岗位Vo对象
     * @return List<PostVo>
     */
    @PostMapping("list")
    public List<PostVo> postList(@RequestBody PostVo postVo) {
        List<PostVo> postVoList = postService.findPostList(postVo);
        return postVoList;
    }

    /**
     * @Description 创建编号
     * @param deptNo 父部门编号
     * @return
     */
    @PostMapping("create-post-no/{deptNo}")
    public String createPostNo(@PathVariable("deptNo") String deptNo) {
        String postNo  = postService.createPostNo(deptNo);
        return postNo;
    }

}
