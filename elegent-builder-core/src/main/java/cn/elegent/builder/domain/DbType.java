package cn.elegent.builder.domain;

import lombok.Data;

@Data
public class DbType {

    private String name;//数据库名称

    private String port;//端口

    private String username;//用户名

    private String password;//密码

    private String url;//连接字符串模板

    private String driver; //驱动


}
