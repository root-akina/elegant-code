package ${base.basePackage}.web;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${base.basePackage}.service.IDeptService;
import ${base.basePackage}.vo.DeptVo;
import ${base.basePackage}.vo.TreeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：部门前端控制器
 */
@Slf4j
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    IDeptService deptService;

    /***
     * @description 多条件查询部门分页列表
     * @param deptVo 部门Vo查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return: Page<DeptVo>
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    public Page<DeptVo> findDeptVoPage(
                                    @RequestBody DeptVo deptVo,
                                    @PathVariable("pageNum") int pageNum,
                                    @PathVariable("pageSize") int pageSize) {
        Page<DeptVo> deptVoPage = deptService.findDeptPage(deptVo, pageNum, pageSize);
        return deptVoPage;
    }

    /**
     * @Description 保存部门
     * @param deptVo 部门Vo对象
     * @return DeptVo
     */
    @PutMapping
    public DeptVo createDept(@RequestBody DeptVo deptVo) {
        DeptVo deptVoResult = deptService.createDept(deptVo);
        return deptVoResult;
    }

    /**
     * @Description 修改部门
     * @param deptVo 部门Vo对象
     * @return Boolean 是否修改成功
     */
    @PatchMapping
    public Boolean updateDept(@RequestBody DeptVo deptVo) {
        Boolean flag = deptService.updateDept(deptVo);
        return flag;
    }


    /***
     * @description 多条件查询部门列表
     * @param deptVo 部门Vo对象
     * @return List<DeptVo>
     */
    @PostMapping("list")
    public List<DeptVo> deptList(@RequestBody DeptVo deptVo) {
        List<DeptVo> deptVoList = deptService.findDeptList(deptVo);
        return deptVoList;
    }

    /**
     * @Description 组织部门树形
     * @param deptVo 组织部门对象
     * @return
     */
    @PostMapping("tree")
    public TreeVo deptTreeVo(@RequestBody DeptVo deptVo) {
        TreeVo treeVo = deptService.deptTreeVo(deptVo.getParentDeptNo(),deptVo.getCheckedDeptNos());
        return treeVo;
    }

    /**
     * @Description 创建编号
     * @param parentDeptNo 父部门编号
     * @return
     */
    @PostMapping("create-dept-no/{parentDeptNo}")
    public String createDeptNo(@PathVariable("parentDeptNo") String parentDeptNo) {
        String deptNo  = deptService.createDeptNo(parentDeptNo);
        return deptNo;
    }

}
