package cn.elegent.builder.controller;

import cn.elegent.builder.domain.Base;
import cn.elegent.builder.domain.Info;
import cn.elegent.builder.domain.Option;
import cn.elegent.builder.service.TemplateService;
import cn.elegent.builder.util.MarkdownUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @PostMapping("/build")
    public void build(@RequestBody Base base){
        String path = getClass().getResource("/template").getPath();
        if(path.startsWith("/")){
            path=path.substring(1);
        }
        base.setTemplatePath( path );


        templateService.buildCode(base);
    }


    @GetMapping("/list")
    public List<String> getTemplateList() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/template/");
        String path = classPathResource.getFile().getAbsolutePath();
        return templateService.getTemplateNameList( path );
    }

    @GetMapping("/params/{name}")
    public List<Info> getParamList(@PathVariable("name") String name) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/config/");
        String path = classPathResource.getFile().getAbsolutePath() + File.separator+ name;
        return templateService.getParamList(path) ;
    }

    /**
     * 读取模板的选项列表
     * @param name
     * @return
     * @throws IOException
     */
    @GetMapping("/options/{name}")
    public List<Option> getOptionList(@PathVariable("name") String name) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/config/");
        String path = classPathResource.getFile().getAbsolutePath() + File.separator+ name;
        List<Option> optionList = templateService.getOptionList(path);
        for(Option option:optionList){
            if(option.getOptions()!=null && option.getOptions().size()>0){
                option.setValue(option.getOptions().get(0));
            }
        }
        return optionList;
    }


    @GetMapping("/readme/{name}")
    public String getReadme(@PathVariable("name") String name) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/config/");
        String path = classPathResource.getFile().getAbsolutePath() + File.separator+ name+File.separator+"README.md";
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        // 将字节数组转换为字符串
        String content = MarkdownUtil.convertToHtml( new String(bytes) );
        return content;
    }


}
