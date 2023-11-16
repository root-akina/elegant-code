package cn.elegent.builder.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Base {

    private String templatePath;//模板目录

    private String basePackage;  //包名称

    private String basePath;//基础目录

    private String project;//项目名称（英文）

    private String projectName;//项目中文名称

    private List<Schema> schemas;//数据库列表

    private String templateName; //模板名称

    private String outputPath;//输出目录

    private DBConnection conn;//连接对象

    private Map<String,String> extend;//扩展

    private List<Info> extendList;//扩展列表

    private Map<String,String> option;//选项

    private List<Info> optionList;//选项列表

    public void convertListToMap(){
        extend= extendList.stream().collect(Collectors.toMap(info -> info.getName(), info -> info.getValue()));
        option= optionList.stream().collect(Collectors.toMap(info -> info.getName(), info -> info.getValue()));
    }

}
