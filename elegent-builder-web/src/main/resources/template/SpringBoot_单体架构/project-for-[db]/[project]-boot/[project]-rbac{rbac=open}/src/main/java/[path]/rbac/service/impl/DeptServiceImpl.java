package ${base.basePackage}.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import ${base.basePackage}.common.constant.*;
import ${base.basePackage}.common.enums.DeptEnum;
import ${base.basePackage}.common.exception.ProjectException;
import ${base.basePackage}.rbac.mapper.DeptMapper;
import ${base.basePackage}.rbac.pojo.Dept;
import ${base.basePackage}.rbac.service.IDeptService;
import ${base.basePackage}.common.utils.BeanConv;
import ${base.basePackage}.common.utils.EmptyUtil;
import ${base.basePackage}.common.utils.ExceptionsUtil;
import ${base.basePackage}.common.utils.NoProcessing;
import ${base.basePackage}.common.vo.DeptVo;
import ${base.basePackage}.common.vo.TreeItemVo;
import ${base.basePackage}.common.vo.TreeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description：部门表服务实现类
 */
@Slf4j
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Autowired
    DeptMapper deptMapper;

    /***
     * @description 多条件查询
     * @param queryWrapper 条件对象
     * @param deptVo 查询条件
     * @return
     */
    private QueryWrapper<Dept> queryWrapper(QueryWrapper<Dept> queryWrapper,DeptVo deptVo){
        //父部门编号查询
        if (!EmptyUtil.isNullOrEmpty(deptVo.getParentDeptNo())) {
            queryWrapper.lambda().likeRight(Dept::getParentDeptNo,NoProcessing.processString(deptVo.getParentDeptNo()));
        }
        //部门编号
        if (!EmptyUtil.isNullOrEmpty(deptVo.getDeptNo())) {
            queryWrapper.lambda().likeRight(Dept::getDeptNo,deptVo.getDeptNo());
        }
        //部门名称查询
        if (!EmptyUtil.isNullOrEmpty(deptVo.getDeptName())) {
            queryWrapper.lambda().likeRight(Dept::getDeptName,deptVo.getDeptName());
        }
        //排序查询
        if (!EmptyUtil.isNullOrEmpty(deptVo.getSortNo())) {
            queryWrapper.lambda().eq(Dept::getSortNo,deptVo.getSortNo());
        }
        //创建者:username查询
        if (!EmptyUtil.isNullOrEmpty(deptVo.getCreateBy())) {
            queryWrapper.lambda().eq(Dept::getCreateBy,deptVo.getCreateBy());
        }
        //更新者:username查询
        if (!EmptyUtil.isNullOrEmpty(deptVo.getUpdateBy())) {
            queryWrapper.lambda().eq(Dept::getUpdateBy,deptVo.getUpdateBy());
        }
        //状态查询
        if (!EmptyUtil.isNullOrEmpty(deptVo.getDataState())) {
            queryWrapper.lambda().eq(Dept::getDataState,deptVo.getDataState());
        }
        //按sottNo降序
        queryWrapper.lambda().orderByAsc(Dept::getSortNo);
        return queryWrapper;
    }

    @Override
    public Page<DeptVo> findDeptPage(DeptVo deptVo, int pageNum, int pageSize) {
        try {
            //构建分页对象
            Page<Dept> page = new Page<>(pageNum,pageSize);
            //构建查询条件
            QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
            //构建多条件查询
            this.queryWrapper(queryWrapper,deptVo);
            //执行分页查询
            return BeanConv.toPage(page(page, queryWrapper),DeptVo.class);
        }catch (Exception e){
            log.error("部门表PAGE异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DeptEnum.PAGE_FAIL);
        }
    }

    @Override
    @Transactional
    public DeptVo createDept(DeptVo deptVo) {
        try {
            //转换DeptVo为Dept
            Dept dept = BeanConv.toBean(deptVo, Dept.class);
            boolean flag = save(dept);
            if (flag){
                return BeanConv.toBean(dept,DeptVo.class);
            }
            return null;
        } catch (Exception e) {
            log.error("保存部门表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DeptEnum.SAVE_FAIL);
        }
    }

    @Transactional
    @Override
    public Boolean updateDept(DeptVo deptVo) {
        try {
            //转换DeptVo为Dept
            Dept dept = BeanConv.toBean(deptVo, Dept.class);
            return updateById(dept);
        } catch (Exception e) {
            log.error("修改部门表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DeptEnum.UPDATE_FAIL);
        }

    }


    @Override
    public List<DeptVo> findDeptList(DeptVo deptVo) {
        try {
            //构建查询条件
            QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
            //构建多条件查询
            this.queryWrapper(queryWrapper,deptVo);
            return BeanConv.toBeanList(list(queryWrapper),DeptVo.class);
        } catch (Exception e) {
            log.error("查询部门表列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DeptEnum.LIST_FAIL);
        }
    }


    @Override
    public TreeVo deptTreeVo(String parentDeptNo,String[] checkedDeptNos) {
        try {
            //根节点查询树形结构
            if (EmptyUtil.isNullOrEmpty(parentDeptNo)){
                parentDeptNo = SuperConstant.ROOT_PARENT_ID;
            }
            List<Dept> deptList = Lists.newLinkedList();
            QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
            //指定节点查询树形结构
            queryWrapper.lambda().eq(Dept::getDataState, SuperConstant.DATA_STATE_0)
                    .likeRight(Dept::getParentDeptNo,NoProcessing.processString(parentDeptNo))
                    .orderByAsc(Dept::getDeptNo);
            deptList.addAll(list(queryWrapper));
            if (EmptyUtil.isNullOrEmpty(deptList)){
                throw new RuntimeException("部门信息为定义！");
            }
            List<TreeItemVo> treeItemVoList  = new ArrayList<>();
            List<String> expandedIds = new ArrayList<>();
            //递归构建树形结构
            List<String> checkedDeptNoList = Lists.newArrayList();
            if (!EmptyUtil.isNullOrEmpty(checkedDeptNos)){
                checkedDeptNoList = Arrays.asList(checkedDeptNos);
            }
            Dept rootDept =  deptList.stream()
                    .filter(d -> SuperConstant.ROOT_PARENT_ID.equals(d.getParentDeptNo()))
                    .collect(Collectors.toList()).get(0);
            recursionTreeItem(treeItemVoList,rootDept,deptList,checkedDeptNoList,expandedIds);
            return TreeVo.builder()
                    .items(treeItemVoList)
                    .checkedIds(checkedDeptNoList)
                    .expandedIds(expandedIds)
                    .build();
        } catch (Exception e) {
            log.error("查询部门表TREE异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DeptEnum.TREE_FAIL);
        }

    }

    private void recursionTreeItem(List<TreeItemVo> treeItemVoList, Dept DeptRoot,List<Dept> deptList,
                                   List<String> checkedDeptNos, List<String> expandedIds) {
        TreeItemVo treeItem = TreeItemVo.builder()
            .id(Long.valueOf(DeptRoot.getDeptNo()))
            .label(DeptRoot.getDeptName())
            .build();
        //判断是否选择
        if (!EmptyUtil.isNullOrEmpty(checkedDeptNos)&&checkedDeptNos.contains(DeptRoot.getDeptNo())){
            treeItem.setIsChecked(true);
        }else {
            treeItem.setIsChecked(false);
        }
        //是否默认展开:如果当前的部门为第二层或者第三层则展开
        if(NoProcessing.processString(DeptRoot.getDeptNo()).length()/3==3){
            expandedIds.add(DeptRoot.getDeptNo());
        }
        //获得当前部门下子部门
        List<Dept> childrenDept = deptList.stream()
            .filter(n -> n.getParentDeptNo().equals(DeptRoot.getDeptNo()))
            .collect(Collectors.toList());
        if (!EmptyUtil.isNullOrEmpty(childrenDept)){
            List<TreeItemVo> listChildren  = Lists.newArrayList();
            childrenDept.forEach(n->{
                this.recursionTreeItem(listChildren,n,deptList,checkedDeptNos,expandedIds);});
                treeItem.setChildren(listChildren);
        }
        treeItemVoList.add(treeItem);
    }

    @Override
    public List<DeptVo> findDeptVoListByUserId(Long userId) {
        try {
            return deptMapper.findDeptVoListByUserId(userId);
        } catch (Exception e) {
            log.error("查询部门表列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DeptEnum.LIST_FAIL);
        }
    }


    @Override
    public List<DeptVo> findDeptInDeptNos(Set<String> deptNos) {
        try {
            QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(Dept::getDeptNo,deptNos)
                    .eq(Dept::getDataState, SuperConstant.DATA_STATE_0);
            return BeanConv.toBeanList(list(queryWrapper),DeptVo.class);
        } catch (Exception e) {
            log.error("查询部门表列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DeptEnum.LIST_FAIL);
        }
    }

    @Override
    public List<DeptVo> findDeptVoListInRoleId(List<Long> roleIds) {
        try {
            return deptMapper.findDeptVoListInRoleId(roleIds);
        } catch (Exception e) {
            log.error("查询部门表列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DeptEnum.LIST_FAIL);
        }
    }

    @Override
    public List<String> findBelowDeptNo(String parentDeptNo) {

        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().eq(Dept::getDataState, SuperConstant.DATA_STATE_0)
                .likeRight(Dept::getDeptNo,NoProcessing.processString(parentDeptNo))
                .orderByAsc(Dept::getDeptNo);
        List<Dept> deptList = list(queryWrapper);

        return deptList.stream().map( Dept::getDeptNo ).collect(Collectors.toList());
    }


    @Override
    public String createDeptNo(String parentDeptNo) {
        try {
            QueryWrapper<Dept> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(Dept::getParentDeptNo,parentDeptNo);
            List<Dept> deptList = deptMapper.selectList(queryWrapper);
            //无下属节点则创建下属节点
            if (EmptyUtil.isNullOrEmpty(deptList)){
                return NoProcessing.createNo(parentDeptNo,false);
                //有下属节点则累加下属节点
            }else {
                Long deptNo = deptList.stream()
                    .map(dept -> { return Long.valueOf(dept.getDeptNo());})
                    .max(Comparator.comparing(i -> i)).get();
                return NoProcessing.createNo(String.valueOf(deptNo),true);
            }
        } catch (Exception e) {
            log.error("创建部门编号异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(DeptEnum.CREATE_DEPT_NO_FAIL);
        }

    }

}
