package cn.elegent.builder.domain;

import lombok.Data;

/**
 * 数据库连接对象
 */
@Data
public class DBConnection {

    private String dbType;//数据库类型

    private String driverName;//驱动名

    private String username;//用户名

    private String password;//密码

    private String url;//连接url

    private String ip;

    private String port;//端口

}
