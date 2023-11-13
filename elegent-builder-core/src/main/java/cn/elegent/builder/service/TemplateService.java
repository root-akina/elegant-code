package cn.elegent.builder.service;


import cn.elegent.builder.domain.Base;
import cn.elegent.builder.domain.Info;
import cn.elegent.builder.domain.Option;
import cn.elegent.builder.domain.Schema;

import java.util.List;

public interface TemplateService {



    void buildCode(Base base);



    List<String> getTemplateNameList(String templatePath);



    List<Info> getParamList(String templatePath);



    List<Option> getOptionList(String templatePath);


}
