package cn.elegent.builder.service;
import cn.elegent.builder.domain.DBConnection;
import cn.elegent.builder.domain.Schema;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据库读取接口
 */
public interface DbService {


    /**
     * 获取数据库名称列表
     * @return
     */
    List<Schema> getSchemaNames(DBConnection dbConnection);


    /**
     * 获取数据库所有信息
     * @return
     */
    List<Schema> getSchemasList(DBConnection dbConnection, List<Schema> schemaNames);



    /**
     * 获取数据库所有信息
     * @return
     */
    Schema getSchema(DBConnection dbConnection, String schemaName);


    /**
     * 获取类型对照表
     * @param dbType
     * @return
     */
    Map<String,String> getTypeMap(String dbType);


    /**
     * 忽略表
     * @return
     */
    Set<String> ignoreTableList();

}
