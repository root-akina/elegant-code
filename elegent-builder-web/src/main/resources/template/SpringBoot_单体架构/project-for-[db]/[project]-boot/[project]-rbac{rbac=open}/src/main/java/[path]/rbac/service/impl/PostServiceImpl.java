package ${base.basePackage}.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${base.basePackage}.common.constant.PostCacheConstant;
import ${base.basePackage}.common.constant.SuperConstant;
import ${base.basePackage}.common.enums.PostEnum;
import ${base.basePackage}.common.exception.ProjectException;
import ${base.basePackage}.rbac.mapper.PostMapper;
import ${base.basePackage}.rbac.pojo.Dept;
import ${base.basePackage}.rbac.pojo.Post;
import ${base.basePackage}.rbac.service.IDeptService;
import ${base.basePackage}.rbac.service.IPostService;
import ${base.basePackage}.common.utils.BeanConv;
import ${base.basePackage}.common.utils.EmptyUtil;
import ${base.basePackage}.common.utils.ExceptionsUtil;
import ${base.basePackage}.common.utils.NoProcessing;
import ${base.basePackage}.common.vo.DeptVo;
import ${base.basePackage}.common.vo.PostVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description：岗位表服务实现类
 */
@Slf4j
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

    @Autowired
    PostMapper postMapper;

    @Autowired
    IDeptService deptService;

    /***
     * @description 多条件查询
     * @param queryWrapper
     * @param postVo
     * @return
     */
    private QueryWrapper<Post> queryWrapper(QueryWrapper<Post> queryWrapper,PostVo postVo){
        //部门编号
        if (!EmptyUtil.isNullOrEmpty(postVo.getDeptNo())) {
            queryWrapper.lambda().likeRight(Post::getDeptNo, NoProcessing.processString(postVo.getDeptNo()));
        }
        //岗位编码：父部门编号+01【2位】查询
        if (!EmptyUtil.isNullOrEmpty(postVo.getPostNo())) {
            queryWrapper.lambda().eq(Post::getPostNo,postVo.getPostNo());
        }
        //岗位名称查询
        if (!EmptyUtil.isNullOrEmpty(postVo.getPostName())) {
            queryWrapper.lambda().likeRight(Post::getPostName,postVo.getPostName());
        }
        //显示顺序查询
        if (!EmptyUtil.isNullOrEmpty(postVo.getSortNo())) {
            queryWrapper.lambda().eq(Post::getSortNo,postVo.getSortNo());
        }
        //创建者:username查询
        if (!EmptyUtil.isNullOrEmpty(postVo.getCreateBy())) {
            queryWrapper.lambda().eq(Post::getCreateBy,postVo.getCreateBy());
        }
        //更新者:username查询
        if (!EmptyUtil.isNullOrEmpty(postVo.getUpdateBy())) {
            queryWrapper.lambda().eq(Post::getUpdateBy,postVo.getUpdateBy());
        }
        //备注查询
        if (!EmptyUtil.isNullOrEmpty(postVo.getRemark())) {
            queryWrapper.lambda().eq(Post::getRemark,postVo.getRemark());
        }
        //状态查询
        if (!EmptyUtil.isNullOrEmpty(postVo.getDataState())) {
            queryWrapper.lambda().eq(Post::getDataState,postVo.getDataState());
        }
        //按sortNo降序
        queryWrapper.lambda().orderByAsc(Post::getSortNo);
        return queryWrapper;
    }

    @Override
    public Page<PostVo> findPostPage(PostVo postVo, int pageNum, int pageSize) {
        try {
            //查询职位
            //构建分页对象
            Page<Post> page = new Page<>(pageNum,pageSize);
            //构建查询条件
            QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
            //构建多条件查询
            this.queryWrapper(queryWrapper,postVo);
            //执行分页查询
            Page<PostVo> pageVo = BeanConv.toPage(page(page, queryWrapper), PostVo.class);
            if (!EmptyUtil.isNullOrEmpty(pageVo.getRecords())){
                //对应部门
                Set<String> deptNos = pageVo.getRecords().stream().map(PostVo::getDeptNo).collect(Collectors.toSet());
                List<DeptVo> deptVoList = deptService.findDeptInDeptNos(deptNos);
                pageVo.getRecords().forEach(n->{
                    //装配部门
                    deptVoList.forEach(d->{
                        if (n.getDeptNo().equals(d.getDeptNo())){
                            n.setDeptVo(BeanConv.toBean(d,DeptVo.class));
                        }
                    });

                });
            }
            return pageVo;
        }catch (Exception e){
            log.error("岗位表PAGE异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(PostEnum.PAGE_FAIL);
        }

    }

    @Override
    @Transactional
    public PostVo createPost(PostVo postVo) {
        try {
            //转换PostVo为Post
            Post post = BeanConv.toBean(postVo, Post.class);
            boolean flag = save(post);
            //装配部门
            if (flag){
                PostVo postVoResult = BeanConv.toBean(post, PostVo.class);
                QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(Dept::getDataState,SuperConstant.DATA_STATE_0)
                        .eq(Dept::getParentDeptNo,postVo.getDeptNo());
                Dept dept = deptService.getOne(queryWrapper);
                if (!EmptyUtil.isNullOrEmpty(dept)){
                    postVoResult.setDeptVo(BeanConv.toBean(dept,DeptVo.class));
                }
                return postVoResult;
            }
            return null;
        } catch (Exception e) {
            log.error("保存岗位表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(PostEnum.SAVE_FAIL);
        }

    }

    @Override
    public Boolean updatePost(PostVo postVo) {
        try {
            //转换PostVo为Post
            Post post = BeanConv.toBean(postVo, Post.class);
            return updateById(post);
        } catch (Exception e) {
            log.error("修改岗位表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(PostEnum.UPDATE_FAIL);
        }

    }


    @Override
    public List<PostVo> findPostList(PostVo postVo) {
        try {
            //构建查询条件
            QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
            this.queryWrapper(queryWrapper,postVo);
            List<PostVo> records = BeanConv.toBeanList(list(queryWrapper),PostVo.class);
            if (!EmptyUtil.isNullOrEmpty(records)){
                //对应部门
                Set<String> deptNos = records.stream().map(PostVo::getDeptNo).collect(Collectors.toSet());
                List<DeptVo> deptVoList = deptService.findDeptInDeptNos(deptNos);
                records.forEach(n->{
                    //装配部门
                    deptVoList.forEach(d->{
                        if (n.getDeptNo().equals(d.getDeptNo())){
                            n.setDeptVo(BeanConv.toBean(d,DeptVo.class));
                        }
                    });
                });
            }
            return records;
        } catch (Exception e) {
            log.error("查询岗位表列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(PostEnum.LIST_FAIL);
        }

    }

    @Override
    public List<PostVo> findPostVoListByUserId(Long userId) {
        try {
            return postMapper.findPostVoListByUserId(userId);
        } catch (Exception e) {
            log.error("查询用户岗位异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(PostEnum.LIST_FAIL);
        }
    }

    @Override
    public String createPostNo(String deptNo) {
        try {
            QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Post::getDeptNo,deptNo);
            List<Post> postList = list(queryWrapper);
            //无下属节点则创建下属节点
            if (EmptyUtil.isNullOrEmpty(postList)){
                return NoProcessing.createNo(deptNo,false);
                //有下属节点则累加下属节点
            }else {
                Long postNo = postList.stream()
                        .map(post -> { return Long.valueOf(post.getPostNo());})
                        .max(Comparator.comparing(i -> i)).get();
                return NoProcessing.createNo(String.valueOf(postNo),true);
            }
        } catch (Exception e) {
            log.error("创建岗位编号异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(PostEnum.CREATE_POST_NO_FAIL);
        }

    }
}
