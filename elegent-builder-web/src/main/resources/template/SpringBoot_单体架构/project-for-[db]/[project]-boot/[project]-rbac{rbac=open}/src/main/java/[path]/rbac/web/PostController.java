package ${base.basePackage}.rbac.web;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${base.basePackage}.common.basic.AjaxResult;
import ${base.basePackage}.common.enums.DeptEnum;
import ${base.basePackage}.common.enums.PostEnum;
import ${base.basePackage}.rbac.service.IPostService;
import ${base.basePackage}.common.utils.AjaxResultBuild;
import ${base.basePackage}.common.vo.PostVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：岗位前端控制器
 */
@Slf4j
@Api(tags = "岗位管理")
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
    @ApiOperation(value = "岗位分页",notes = "岗位分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "postVo",value = "岗位Vo对象",required = true,dataType = "PostVo"),
        @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
        @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    public AjaxResult<Page<PostVo>> findPostVoPage(
                                    @RequestBody PostVo postVo,
                                    @PathVariable("pageNum") int pageNum,
                                    @PathVariable("pageSize") int pageSize) {
        Page<PostVo> postVoPage = postService.findPostPage(postVo, pageNum, pageSize);
        return AjaxResultBuild.build(PostEnum.SUCCEED,postVoPage);
    }

    /**
     * @Description 保存岗位
     * @param postVo 岗位Vo对象
     * @return PostVo
     */
    @PutMapping
    @ApiOperation(value = "岗位添加",notes = "岗位添加")
    @ApiImplicitParam(name = "postVo",value = "岗位Vo对象",required = true,dataType = "PostVo")
    public AjaxResult<PostVo> createPost(@RequestBody PostVo postVo) {
        PostVo postVoResult = postService.createPost(postVo);
        return AjaxResultBuild.build(PostEnum.SUCCEED,postVoResult);
    }

    /**
     * @Description 修改岗位
     * @param postVo 岗位Vo对象
     * @return Boolean 是否修改成功
     */
    @PatchMapping
    @ApiOperation(value = "岗位修改",notes = "岗位修改")
    @ApiImplicitParam(name = "postVo",value = "岗位Vo对象",required = true,dataType = "PostVo")
    public AjaxResult<Boolean> updatePost(@RequestBody PostVo postVo) {
        Boolean flag = postService.updatePost(postVo);
        return AjaxResultBuild.build(PostEnum.SUCCEED,flag);
    }

    /***
     * @description 多条件查询岗位列表
     * @param postVo 岗位Vo对象
     * @return List<PostVo>
     */
    @PostMapping("list")
    @ApiOperation(value = "岗位列表",notes = "岗位列表")
    @ApiImplicitParam(name = "postVo",value = "岗位Vo对象",required = true,dataType = "PostVo")
    public AjaxResult<List<PostVo>> postList(@RequestBody PostVo postVo) {
        List<PostVo> postVoList = postService.findPostList(postVo);
        return AjaxResultBuild.build(PostEnum.SUCCEED,postVoList);
    }

    /**
     * @Description 创建编号
     * @param deptNo 父部门编号
     * @return
     */
    @PostMapping("create-post-no/{deptNo}")
    @ApiOperation(value = "创建编号",notes = "创建编号")
    @ApiImplicitParam(paramType = "path",name = "deptNo",value = "部门编号",required = true,dataType = "String")
    public AjaxResult<String> createPostNo(@PathVariable("deptNo") String deptNo) {
        String postNo  = postService.createPostNo(deptNo);
        return AjaxResultBuild.build(DeptEnum.SUCCEED,postNo);
    }

}
