package cn.elegent.builder.controller;
import cn.elegent.builder.domain.DBConnection;
import cn.elegent.builder.domain.DbType;
import cn.elegent.builder.domain.Schema;
import cn.elegent.builder.properties.BuilderProperties;
import cn.elegent.builder.service.DbService;
import cn.elegent.builder.service.DbTypeService;
import cn.elegent.builder.vo.ShowRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/db")
public class DbController {


    @Autowired
    private DbService dbService;

    @Autowired
    private DbTypeService dbTypeService;

    @Autowired
    private BuilderProperties builderProperties;

    @PostMapping("/schemaNames")
    public List<Schema> schemaNames(@RequestBody DBConnection dbConnection){
        return dbService.getSchemaNames(dbConnection);
    }


    @PostMapping("/showTables")
    public List<Schema> showTables(@RequestBody ShowRequestVo showRequestVo){
        return dbService.getSchemasList(showRequestVo.getDbConnection(),showRequestVo.getSchemaList());
    }


    @GetMapping("/dbTypeList")
    public List<DbType> dbTypeList(){
        return builderProperties.getDbTypeList();
    }

}
