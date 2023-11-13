package ${base.basePackage}.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import ${base.basePackage}.common.constant.ResourceCacheConstant;
import ${base.basePackage}.common.constant.RoleCacheConstant;
import ${base.basePackage}.common.constant.SuperConstant;
import ${base.basePackage}.common.enums.ResourceEnum;
import ${base.basePackage}.common.exception.ProjectException;
import ${base.basePackage}.common.vo.*;
import ${base.basePackage}.rbac.mapper.ResourceMapper;
import ${base.basePackage}.rbac.mapper.RoleMapper;
import ${base.basePackage}.rbac.pojo.Resource;
import ${base.basePackage}.rbac.service.IResourceService;
import ${base.basePackage}.common.utils.BeanConv;
import ${base.basePackage}.common.utils.EmptyUtil;
import ${base.basePackage}.common.utils.ExceptionsUtil;
import ${base.basePackage}.common.utils.NoProcessing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description：权限表服务实现类
 */
@Slf4j
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    RoleMapper roleMapper;

    /***
     * @description 多条件查询
     * @param queryWrapper
     * @param resourceVo
     * @return
     */
    private QueryWrapper<Resource> queryWrapper(QueryWrapper<Resource> queryWrapper, ResourceVo resourceVo){
        //资源编号
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getResourceNo())) {
            queryWrapper.lambda().eq(Resource::getResourceNo,resourceVo.getResourceNo());
        }
        //父资源编号查询
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getParentResourceNo())) {
            queryWrapper.lambda().likeRight(Resource::getParentResourceNo,
                    NoProcessing.processString(resourceVo.getParentResourceNo()));
        }
        //资源名称查询
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getResourceName())) {
            queryWrapper.lambda().likeRight(Resource::getResourceName,resourceVo.getResourceName());
        }
        //资源类型（m目录 c菜单 f按钮 r微服务）查询
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getResourceType())) {
            queryWrapper.lambda().eq(Resource::getResourceType,resourceVo.getResourceType());
        }
        //请求地址查询
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getRequestPath())) {
            queryWrapper.lambda().likeRight(Resource::getRequestPath,resourceVo.getRequestPath());
        }
        //权限标识查询
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getLabel())) {
            queryWrapper.lambda().likeRight(Resource::getLabel,resourceVo.getLabel());
        }
        //排序查询
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getSortNo())) {
            queryWrapper.lambda().eq(Resource::getSortNo,resourceVo.getSortNo());
        }
        //图标查询
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getIcon())) {
            queryWrapper.lambda().eq(Resource::getIcon,resourceVo.getIcon());
        }
        //创建者查询
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getCreateBy())) {
            queryWrapper.lambda().eq(Resource::getCreateBy,resourceVo.getCreateBy());
        }
        //更新者查询
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getUpdateBy())) {
            queryWrapper.lambda().eq(Resource::getUpdateBy,resourceVo.getUpdateBy());
        }
        //备注查询
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getRemark())) {
            queryWrapper.lambda().eq(Resource::getRemark,resourceVo.getRemark());
        }
        //状态查询
        if (!EmptyUtil.isNullOrEmpty(resourceVo.getDataState())) {
            queryWrapper.lambda().eq(Resource::getDataState,resourceVo.getDataState());
        }
        //按创sortNo排序
        queryWrapper.lambda().orderByAsc(Resource::getSortNo);
        return queryWrapper;
    }

    @Override
    public Page<ResourceVo> findResourcePage(ResourceVo resourceVo, int pageNum, int pageSize) {
        try {
            //构建分页对象
            Page<Resource> page = new Page<>(pageNum,pageSize);
            //构建查询条件
            QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
            //多条件查询
            this.queryWrapper(queryWrapper,resourceVo);
            //执行分页查询
            return BeanConv.toPage(page(page, queryWrapper),ResourceVo.class);
        }catch (Exception e){
            log.error("权限表列表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(ResourceEnum.PAGE_FAIL);
        }

    }

    @Override
    @Transactional
    public ResourceVo createResource(ResourceVo resourceVo) {
        try {
            //转换ResourceVo为Resource
            Resource resource = BeanConv.toBean(resourceVo, Resource.class);
            boolean flag = save(resource);
            if (flag){
                return BeanConv.toBean(resource,ResourceVo.class);
            }
            return null;
        } catch (Exception e) {
            log.error("保存权限表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(ResourceEnum.SAVE_FAIL);
        }

    }

    @Override
    @Transactional
    public Boolean updateResource(ResourceVo resourceVo) {
        try {
            //转换ResourceVo为Resource
            Resource resource = BeanConv.toBean(resourceVo, Resource.class);
            return updateById(resource);
        } catch (Exception e) {
            log.error("修改权限表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(ResourceEnum.UPDATE_FAIL);
        }

    }

    @Override
    public List<ResourceVo> findResourceList(ResourceVo resourceVo) {
        try {
            //构建查询条件
            QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
            this.queryWrapper(queryWrapper,resourceVo);
            return BeanConv.toBeanList(list(queryWrapper),ResourceVo.class);
        } catch (Exception e) {
            log.error("删除权限表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(ResourceEnum.LIST_FAIL);
        }

    }

    @Override
    public List<ResourceVo> findResourceVoListInRoleId(List<Long> roleIds) {
        try {
            return resourceMapper.findResourceVoListInRoleId(roleIds);
        } catch (Exception e) {
            log.error("查询权限表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(ResourceEnum.LIST_FAIL);
        }
    }

    @Override
    public List<ResourceVo> findResourceVoListByUserId(Long userId) {
        try {
            return resourceMapper.findResourceVoListByUserId(userId);
        } catch (Exception e) {
            log.error("删除权限表异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(ResourceEnum.LIST_FAIL);
        }
    }

    @Override
    public TreeVo resourceTreeVo(String parentResourceNo, String[] checkedResourceNos) {
        try {
            List<Resource> resourceList = Lists.newLinkedList();
            QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
            //根节点查询树形结构
            if (EmptyUtil.isNullOrEmpty(parentResourceNo)){
                parentResourceNo = SuperConstant.ROOT_PARENT_ID;
            }
            //指定节点查询树形结构
            queryWrapper.lambda()
                    .eq(Resource::getDataState, SuperConstant.DATA_STATE_0)
                    .likeRight(Resource::getParentResourceNo, NoProcessing.processString(parentResourceNo))
                    .orderByAsc(Resource::getResourceNo);
            resourceList.addAll(list(queryWrapper));
            if (EmptyUtil.isNullOrEmpty(resourceList)){
                throw new RuntimeException("部门信息为定义！");
            }
            List<TreeItemVo> treeItemVoList  = new ArrayList<>();
            List<String> expandedIds = new ArrayList<>();
            //递归构建树形结构
            List<String> checkedResourceNoList = Lists.newArrayList();
            if (!EmptyUtil.isNullOrEmpty(checkedResourceNos)){
                checkedResourceNoList = Arrays.asList(checkedResourceNos);
            }
            recursionTreeItem(treeItemVoList,resourceList.get(0),resourceList,checkedResourceNoList,expandedIds);
            return TreeVo.builder()
                    .items(treeItemVoList)
                    .checkedIds(checkedResourceNoList)
                    .expandedIds(expandedIds)
                    .build();
        } catch (Exception e) {
            log.error("查询资源表TREE异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(ResourceEnum.TREE_FAIL);
        }
    }


    private void recursionTreeItem(List<TreeItemVo> treeItemVoList, Resource ResourceRoot, List<Resource> resourceList,
                                   List<String> checkedResourceNos, List<String> expandedIds) {
        TreeItemVo treeItem = TreeItemVo.builder()
                .id(Long.valueOf(ResourceRoot.getResourceNo()))
                .label(ResourceRoot.getResourceName())
                .build();
        //判断是否选择
        if (!EmptyUtil.isNullOrEmpty(checkedResourceNos)&&
                checkedResourceNos.contains(ResourceRoot.getResourceNo())){
            treeItem.setIsChecked(true);
        }else {
            treeItem.setIsChecked(false);
        }
        //是否默认展开:如果当前的资源为第二层或者第三层则展开
        if(NoProcessing.processString(ResourceRoot.getResourceNo()).length()/3==2||
           NoProcessing.processString(ResourceRoot.getResourceNo()).length()/3==3){
            expandedIds.add(ResourceRoot.getResourceNo());
        }
        //获得当前资源下子资源
        List<Resource> childrenResource = resourceList.stream()
            .filter(n -> n.getParentResourceNo().equals(ResourceRoot.getResourceNo()))
            .collect(Collectors.toList());
        if (!EmptyUtil.isNullOrEmpty(childrenResource)){
            List<TreeItemVo> listChildren  = Lists.newArrayList();
            childrenResource.forEach(n->{
                this.recursionTreeItem(listChildren,n,resourceList,checkedResourceNos,expandedIds);});
            treeItem.setChildren(listChildren);
        }
        treeItemVoList.add(treeItem);
    }

    @Override
    public List<MenuVo> menus(String systemCode) {
        try {
            //查询当前系统的根节点
            QueryWrapper<Resource> parentQueryWrapper =new QueryWrapper<>();
            parentQueryWrapper.lambda()
                    .eq(Resource::getParentResourceNo, SuperConstant.ROOT_PARENT_ID)
                    .eq(Resource::getDataState,SuperConstant.DATA_STATE_0)
                    .eq(Resource::getResourceType,SuperConstant.SYSTEM)
                    .orderByAsc(Resource::getSortNo);
            Resource parentResource = resourceMapper.selectOne(parentQueryWrapper);
            //构建一级菜单
            QueryWrapper<Resource> queryWrapper =new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(Resource::getParentResourceNo,parentResource.getResourceNo())
                    .eq(Resource::getDataState,SuperConstant.DATA_STATE_0)
                    .eq(Resource::getResourceType,SuperConstant.CATALOGUE)
                    .orderByAsc(Resource::getSortNo);
            List<Resource> resources = resourceMapper.selectList(queryWrapper);
            List<MenuVo> list  = new ArrayList<>();
            recursionMenuVo(list,resources,SuperConstant.COMPONENT_LAYOUT);
            return list;
        } catch (Exception e) {
            log.error("查询资源表TREE异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(ResourceEnum.MENUS_FAIL);
        }
    }

    /**
     * @Description 递归菜单
     */
    public List<MenuVo> recursionMenuVo(List<MenuVo> list, List<Resource> resources, String component){

        for (Resource resource : resources) {
            List<RoleVo> roleVoList = roleMapper.findRoleVoListByResourceNo(resource.getResourceNo());
            List<String> roleLabels = new ArrayList<>();
            roleVoList.forEach(n->{
                roleLabels.add(n.getLabel());
            });
            MenuMetaVo menuMetaVo = MenuMetaVo.builder()
                .icon(resource.getIcon())
                .roles(roleLabels)
                .title(resource.getResourceName())
                .build();
            MenuVo menuVo = MenuVo.builder()
                .name(resource.getResourceName())
                .hidden(false)
                .component(resource.getRequestPath())
                .meta(menuMetaVo)
                .build();
            if (SuperConstant.COMPONENT_LAYOUT.equals(component)){
                menuVo.setPath("/"+resource.getRequestPath());
                menuVo.setComponent(SuperConstant.COMPONENT_LAYOUT);
            }else {
                menuVo.setPath(resource.getRequestPath());
                menuVo.setComponent(component+"/"+resource.getRequestPath());
            }
            QueryWrapper<Resource> queryWrapper =new QueryWrapper<>();
            queryWrapper.lambda()
                .eq(Resource::getParentResourceNo,resource.getResourceNo())
                .eq(Resource::getResourceType,SuperConstant.MENU)
                .orderByAsc(Resource::getSortNo);
            List<Resource> resourceChildren = resourceMapper.selectList(queryWrapper);
            if (resourceChildren.size()>0){
                menuVo.setRedirect("/"+resource.getResourceName()+"/"+resourceChildren.get(0).getResourceName());
                List<MenuVo> listChildren  = new ArrayList<>();
                this.recursionMenuVo(listChildren,resourceChildren,resource.getRequestPath());
                menuVo.setChildren(listChildren);
            }
            list.add(menuVo);
        }
        return list;
    }

    @Override
    public String createResourceNo(String parentResourceNo) {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Resource::getParentResourceNo,parentResourceNo);
        List<Resource> resourceList = list(queryWrapper);
        //无下属节点则创建下属节点
        if (EmptyUtil.isNullOrEmpty(resourceList)){
            return NoProcessing.createNo(parentResourceNo,false);
        //有下属节点则累加下属节点
        }else {
            Long resourceNo = resourceList.stream()
                .map(resource -> { return Long.valueOf(resource.getResourceNo());})
                .max(Comparator.comparing(i -> i)).get();
            return NoProcessing.createNo(String.valueOf(resourceNo),true);
        }
    }
}
