package cn.elegent.builder.domain;

import lombok.Data;

import java.util.List;

@Data
public class Schema {

    private String name;//库名称

    private String serviceName; // 下划线变-

    private String className;//对应类名

    private String url;//连接字符串

    private String username;//用户名

    private String password;//密码

    private String dbType;//数据库类型

    private String driverName;//驱动名

    private List<Table> tables;//表集合

    private String servicePort;//应用程序启动端口

}
