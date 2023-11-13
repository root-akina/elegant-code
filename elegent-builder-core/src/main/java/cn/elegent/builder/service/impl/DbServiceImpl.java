package cn.elegent.builder.service.impl;

import cn.elegent.builder.domain.Column;
import cn.elegent.builder.domain.DBConnection;
import cn.elegent.builder.domain.Schema;
import cn.elegent.builder.domain.Table;
import cn.elegent.builder.service.DbService;
import cn.elegent.builder.util.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DbServiceImpl implements DbService {


    @Override
    public List<Schema> getSchemaNames(DBConnection dbConnection) {

        String url= dbConnection.getUrl();
        url = url.replace("${ip}", dbConnection.getIp());
        url = url.replace("${port}", dbConnection.getPort());

        Connection connection =null;
        try {
            Class.forName(dbConnection.getDriverName());
            connection = java.sql.DriverManager.getConnection(url, dbConnection.getUsername(), dbConnection.getPassword());
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getCatalogs();
            List<Schema> list=new ArrayList<Schema>();

            while(rs.next()){
                Schema schema=new Schema();
                schema.setName( rs.getString(1) );
                list.add( schema);
            }
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据库无法连接");
        }finally {
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public List<Schema> getSchemasList(DBConnection dbConnection, List<Schema> schemaNames) {
        List<Schema> schemas=new ArrayList<>();
        int port=9100;
        for(Schema schema: schemaNames){
            Schema s = getSchema(dbConnection, schema.getName());
            //类名处理
            s.setClassName(  StringUtils.initialToUpperCase(StringUtils.convertToCamelCase(s.getName()))  );
            s.setServiceName(  StringUtils.convertToServiceName( schema.getName() )  );
            port++;
            s.setServicePort(port+"");
            schemas.add(s);
        }
        return schemas;
    }

    @Override
    public Schema getSchema(DBConnection dbConnection, String schemaName) {

        dbConnection.setDriverName("com.mysql.cj.jdbc.Driver");
        //加载转换器
        Map<String, String> convertMap = getTypeMap("MYSQL");

        String url= "jdbc:mysql://${ip}:${port}/${db}?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=true";
        try {
            Class.forName(dbConnection.getDriverName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Properties props =new Properties();
        props.put("remarksReporting","true");
        props.put("user", dbConnection.getUsername());
        props.put("password", dbConnection.getPassword());

        if(schemaName!=null  ){
            url=url.replace("${db}", schemaName);
        }

        String ip = dbConnection.getIp();
        if(ip!=null && !ip.equals("")){
            url=url.replace("${ip}", ip);
        }else{
            url=url.replace("${ip}", "127.0.0.1");
        }
        url = url.replace("${port}", dbConnection.getPort());


        Set<String> ignoreTableList = ignoreTableList();  //忽略表

        Connection connection = null;
        try {
            connection = java.sql.DriverManager.getConnection(url, props);
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tablers = metaData.getTables(schemaName, null, null, new String[]{"TABLE"});
            List<Table> tableList=new ArrayList<Table>();
            while(tablers.next()){
                Table table=new Table();
                String tableName=tablers.getString("TABLE_NAME");
                //如果为垃圾表
                if(tableName.contains("=") || tableName.contains("$")){
                    continue;
                }
                //如果为忽略的表，也不列出来
                if(ignoreTableList.contains(tableName )){
                    continue;
                }
                //判断 表名为全大写 ，则转换为小写
                if(tableName.toUpperCase().equals(tableName)){
                    table.setName(tableName.toLowerCase());
                }else{
                    table.setName(tableName);
                }
                String[] prefixs ={ "tb_","t_","tab_"};
                table.setClassName( StringUtils.convertTableNameToClassName( table.getName(),  prefixs) );
                table.setVarName(  StringUtils.initialToLowerCase( table.getClassName() )   );  //首字母小写的变量名
                table.setComment(tablers.getString("REMARKS").replaceAll("\\s", ""));
                //获得主键
                ResultSet primaryKeys = metaData.getPrimaryKeys(schemaName, null, tableName);
                List<String> keys=new ArrayList<String>();
                while(primaryKeys.next()){
                    String keyname=primaryKeys.getString("COLUMN_NAME");
                    //判断 表名为全大写 ，则转换为小写
                    if(keyname.toUpperCase().equals(keyname)){
                        keyname=keyname.toLowerCase();//转换为小写
                    }
                    keys.add(keyname);
                }

                //获得所有列
                ResultSet columnrs = metaData.getColumns(schemaName, null, tableName, null);

                List<Column> columnList=new ArrayList<Column>();
                while(columnrs.next()){
                    Column column=new Column();
                    String columnName=  columnrs.getString("COLUMN_NAME");
                    //判断 表名为全大写 ，则转换为小写
                    if(columnName.toUpperCase().equals(columnName)){
                        columnName=columnName.toLowerCase();//转换为小写
                    }
                    column.setName(columnName);
                    //驼峰处理
                    column.setProperty(  StringUtils.convertToCamelCase(columnName)  );
                    column.setProperty2( StringUtils.initialToUpperCase( column.getProperty() ) );
                    String columnDbType = columnrs.getString("TYPE_NAME");
                    if(columnDbType.contains(" ")){
                        columnDbType=columnDbType.substring(0, columnDbType.indexOf(" ") );
                    }
                    column.setType(columnDbType);//数据库原始类型
                    //todo:类型转换
                    String typeName = convertMap.get(columnDbType);//获取转换后的类型!!!!!!!
                    if(typeName==null){
                        typeName="String";
                    }
                    column.setPropertyType(typeName);

                    String remarks = columnrs.getString("REMARKS").replaceAll("\\s", "");//备注
                    if(remarks==null){
                        remarks=columnName;
                    }
                    column.setComment(remarks);

                    if(keys.contains(columnName)){//如果该列是主键
                        column.setKey(true);
                        table.setKey(column.getName());
                        table.setKeyType(typeName);
                        table.setKey2( StringUtils.convertToCamelCase( table.getKey() ) );//驼峰
                        table.setKey2Upper(   StringUtils.initialToUpperCase( table.getKey2() ) );
                    }else{
                        column.setKey(false);
                    }
                    column.setSize(columnrs.getInt("COLUMN_SIZE"));//字段长度
                    int decimals =columnrs.getInt("DECIMAL_DIGITS");//小数位数
                    if(decimals>0){
                        column.setType("Double");//如果是小数则设置为Double
                    }
                    column.setDecimals(decimals);//
                    //前端控件
                    if(column.isKey()){
                        column.setListControl("");
                    }else{
                        column.setListControl("text");
                    }

                    column.setEditControl("input");

                    if(column.getPropertyType().equals("string")){
                        column.setQueryControl("input");
                    }else{
                        column.setQueryControl("");
                    }
                    columnList.add(column);
                }
                columnrs.close();
                table.setColumns(columnList);

                table.buildAllColumn(); //生成字段列表

                tableList.add(table );
            }
            tablers.close();
            connection.close();

            Schema schema=new Schema();
            schema.setName( schemaName );
            schema.setTables(tableList);

            schema.setUrl( url );
            schema.setUsername(dbConnection.getUsername());
            schema.setPassword(dbConnection.getPassword());
            schema.setDriverName(dbConnection.getDriverName());
            schema.setDbType(dbConnection.getDbType());

            return schema;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Map<String, String> getTypeMap(String dbType) {
        Map<String, String> map=new HashMap<>();
        //如果是Mysql
        if("MYSQL".equals(dbType)){
            map.put("VARCHAR","String");
            map.put("BIGINT","Long");
            map.put("INT","Integer");
            map.put("DATE","java.time.LocalDate");
            map.put("DATETIME","java.time.LocalDateTime");
            map.put("DOUBLE","Double");
            map.put("TEXT","String");
            map.put("BIT","Boolean");
            map.put("TINYINT","Byte");
            map.put("CHAR","String");
            map.put("TIME","java.time.LocalTime");
            map.put("TIMESTAMP","java.time.LocalDateTime");
            map.put("BLOB","byte[]");
            map.put("VARBINARY","byte[]");
            map.put("CLOB","String");
            map.put("SMALLINT","Short");
            map.put("FLOAT","Float");
            map.put("NUMERIC","Double");
            map.put("BINARY","byte[]");
            map.put("GEOMETRY","String");
        }
        return map;
    }

    @Override
    public Set<String> ignoreTableList() {
        Set<String> list=new HashSet<>();
        list.add("tab_dept");
        list.add("tab_dept_post_user");
        list.add("tab_post");
        list.add("tab_resource");
        list.add("tab_role");
        list.add("tab_role_dept");
        list.add("tab_role_resource");
        list.add("tab_user");
        list.add("tab_user_role");
        return list;
    }
}
