package cn.elegent.builder.domain;

import lombok.Data;

/**
 * 列对象
 * @author Administrator
 *
 */
@Data
public class Column {
	
	private String name;//列名称

	private String type;//列数据库类型

	private String property;//属性名称

	private String property2;//属性名称(大写开头),用于get方法

	private String propertyType;//列类型

	private String comment;//列备注D

	private boolean key;//是否是主键

	private int size;//  COLUMN_SIZE  字段长度
	
	private int decimals;// DECIMAL_DIGITS;//小数位数

	private String  tableName;  //关联的表名称

	private Table table;  //关联的表

	//前端
	private String queryControl ;  //查询空间类型   input:文本框

	private String listControl;   //列表中显示样式  text:文本

	private String editControl;//  编辑控件  input:文本框

}
