package ${base.basePackage}.web;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${base.basePackage}.service.IRoleService;
import ${base.basePackage}.vo.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：角色前端控制器
 */
@Slf4j
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    IRoleService roleService;

    /***
     * @description 多条件查询角色分页列表
     * @param roleVo 角色Vo查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return: Page<RoleVo>
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    public Page<RoleVo> findRoleVoPage(
                                    @RequestBody RoleVo roleVo,
                                    @PathVariable("pageNum") int pageNum,
                                    @PathVariable("pageSize") int pageSize) {
        Page<RoleVo> roleVoPage = roleService.findRolePage(roleVo, pageNum, pageSize);
        return roleVoPage;
    }

    /**
     * @Description 保存角色
     * @param roleVo 角色Vo对象
     * @return RoleVo
     */
    @PutMapping
    public RoleVo createRole(@RequestBody RoleVo roleVo) {
        RoleVo roleVoResult = roleService.createRole(roleVo);
        return roleVoResult;
    }

    /**
     * @Description 修改角色
     * @param roleVo 角色Vo对象
     * @return Boolean 是否修改成功
     */
    @PatchMapping
    public Boolean updateRole(@RequestBody RoleVo roleVo) {
        Boolean flag = roleService.updateRole(roleVo);
        return flag;
    }

    /***
     * @description 多条件查询角色列表
     * @param roleVo 角色Vo对象
     * @return List<RoleVo>
     */
    @PostMapping("list")
    public List<RoleVo> roleList(@RequestBody RoleVo roleVo) {
        List<RoleVo> roleVoList = roleService.findRoleList(roleVo);
        return roleVoList;
    }

}
