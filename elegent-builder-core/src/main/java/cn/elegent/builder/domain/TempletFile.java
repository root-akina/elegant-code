package cn.elegent.builder.domain;

import lombok.Data;

/**
 * 模板文件实体
 * @author Administrator
 *
 */
@Data
public class TempletFile {
	
	private String path;//原绝对路径

	private String fileName;//模板文件名

	private String directory ;//相对路径

	private String targetFileName;//目标文件名

	private String type;// db   type  all

	private Schema schema;

	private Table table;

	private Base base;

}
