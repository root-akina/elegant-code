package ${base.basePackage}.project.http.controller;
import ${base.basePackage}.project.service.${table.className}Service;
import ${base.basePackage}.project.vo.Pager;
import ${base.basePackage}.project.vo.${table.className}VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/${table.varName}")
public class ${table.className}Controller {

    @Autowired
    private ${table.className}Service ${table.varName}Service;

    /**
     * 获取所有商品类别
     * @return
     */
    @PostMapping("/list")
    public List<${table.className}VO> list(@RequestBody ${table.className}VO ${table.varName}Vo){
        return ${table.varName}Service.findList( ${table.varName}Vo );
    }

    /**
     * 类型搜索
     * @param pageIndex
     * @param pageSize
     * @param ${table.varName}Vo
     * @return
     */
    @PostMapping("/search")
    public Pager<${table.className}VO> search(
            @RequestParam(value = "pageIndex",required = false,defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize,
            @RequestBody ${table.className}VO ${table.varName}Vo){
        return ${table.varName}Service.findPage(pageIndex,pageSize,${table.varName}Vo);
    }

    /**
     * 根据Id查询
     * @param id
     * @return 实体
     */
    @GetMapping("/{id}")
    public ${table.className}VO findById(@PathVariable ${table.keyType} id){
        return ${table.varName}Service.findById( id );
    }


    /**
     * 新增
     * @param ${table.varName}Vo
     * @return
     */
    @PostMapping
    public boolean add(@RequestBody ${table.className}VO ${table.varName}Vo){
        return ${table.varName}Service.add(${table.varName}Vo);
    }


    /**
     * 修改
     * @param id
     * @param ${table.varName}Vo
     * @return 是否成功
     */
    @PutMapping("/{id}")
    public boolean update(@PathVariable ${table.keyType} id,@RequestBody ${table.className}VO ${table.varName}Vo){
        return ${table.varName}Service.update(id,${table.varName}Vo);
    }


    /**
     * 根据classId删除
     * @param id
     * @return 实体
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable ${table.keyType} id){
        ${table.varName}Service.removeById( id );
    }


}
