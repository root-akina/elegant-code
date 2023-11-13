package cn.elegent.builder.domain;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 表实体
 * @author Administrator
 *
 */
@Data
public class Table {
	
	private String name;//表名称

	private String className;//对应的类名称（驼峰，首字母大写）

	private String varName;//对应的变量名称（驼峰，首字母小写）

	private String comment;//介绍

	private String key;// 主键列

	private String key2;// 主键列（驼峰）

	private String key2Upper;// 主键列（驼峰）

	private String keyType;//主键类型

	private List<Column> columns;//列集合

	private String fields;  //所有字段列表

	public void buildAllColumn(){
		List<String> list = columns.stream().map(column -> column.getName()).collect(Collectors.toList());
		fields = String.join( "," ,list );
	}

}
