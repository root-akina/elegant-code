package cn.elegent.builder.service.impl;
import cn.elegent.builder.domain.*;
import cn.elegent.builder.service.TemplateService;
import cn.elegent.builder.util.StringUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

public class TemplateServiceImpl implements TemplateService {

    @Override
    public void buildCode(Base base) {
        base.convertListToMap();//将接收过来的扩展参数转换一下。

        base.setBasePath( StringUtils.convertPackageToPath( base.getBasePackage() ) );

        // 创建Configuration对象
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        // 设置模板目录
        try {
            //获取所有模板
            List<TempletFile> templetList = getTempletList( base.getTemplatePath()+File.separator+base.getTemplateName() );
            //创建模板工作列表
            List<TempletFile> workTempletList =new ArrayList<>();
            //将模板分成三份，一个是顶级的  ，一个是库级  ，一个是表级
            //1.不带替换符的模板
            List<TempletFile> templetTopList = templetList.stream()
                    .filter(templetFile -> getTemplateType(templetFile).equals("base"))
                    .map(templetFile -> {
                        templetFile.setType("base");
                        templetFile.setBase(base);
                        templetFile.setTargetFileName( replaceBase( templetFile.getFileName(),base));
                        templetFile.setDirectory( replaceBase(templetFile.getDirectory(),base ) );
                        return templetFile ;
                    })
                    .collect(Collectors.toList());
            workTempletList.addAll(templetTopList);

            //2.库级别模板
            List<TempletFile> templetDBList = templetList.stream()
                    .filter(templetFile -> getTemplateType(templetFile).equals("db") )
                    .collect(Collectors.toList());

            for(TempletFile templetFile :templetDBList) {
                //循环所有库
                for (Schema schema : base.getSchemas()) {
                    String directory = replaceDb( templetFile.getDirectory(), schema) ;
                    String targetFileName = replaceDb( templetFile.getFileName() ,schema  );
                    TempletFile templetFile2 = new TempletFile();
                    templetFile2.setPath(templetFile.getPath());

                    templetFile2.setFileName(templetFile.getFileName());
                    templetFile2.setTargetFileName( targetFileName);

                    templetFile2.setType("db");
                    templetFile2.setSchema(schema);
                    templetFile2.setBase(base);
                    templetFile2.setDirectory( replaceBase(directory,base ) );

                    workTempletList.add(templetFile2);
                }
            }

            //3.表级别
            List<TempletFile> templetTableList = templetList.stream()
                    .filter(templetFile -> getTemplateType(templetFile).equals("table")  )
                    .collect(Collectors.toList());

            for(TempletFile templetFile :templetTableList) {
                //循环所有库
                for (Schema schema : base.getSchemas()) {
                    for (Table table:  schema.getTables() ){
                        String directory =  replaceDb( templetFile.getDirectory(), schema);
                        directory= replaceTable( directory, table );
                        String targetFileName =  replaceDb( templetFile.getFileName(), schema);
                        targetFileName= replaceTable( targetFileName, table ) ;
                        TempletFile templetFile2 = new TempletFile();
                        templetFile2.setPath(templetFile.getPath());
                        templetFile2.setDirectory(directory);
                        templetFile2.setFileName(templetFile.getFileName());
                        templetFile2.setTargetFileName( targetFileName );
                        templetFile2.setType("table");
                        templetFile2.setSchema(schema);
                        templetFile2.setTable(table);
                        templetFile2.setBase(base);
                        templetFile2.setDirectory( replaceBase(templetFile2.getDirectory(),base ) );
                        workTempletList.add(templetFile2);
                    }
                }
            }

            //循环所有的模板文件
            for(TempletFile templetFile :workTempletList){


                if(templetFile.getFileName().contains("(i)")  || templetFile.getDirectory().contains("(i)")  ){

                    //非模板文件结尾为 (i)，原样拷贝
                    copyFile(base.getOutputPath(),templetFile);
                }else{
                    //
                    Info info = getTemplateOption(templetFile.getFileName());//获取模板选项
                    Info info2 = getTemplateOption(templetFile.getDirectory());//获取模板选项
                    if(info == null && info2 == null){  //非选项
                        buildCode(configuration,base.getOutputPath(),templetFile);
                    }else{
                        //选项模板，需要判断
                        Map<String, String> option = base.getOption();
                        String optionValue = null;

                        if(info!=null){
                            optionValue = option.get(info.getName());
                        }
                        String optionValue2 = null;
                        if(info2!=null) {
                            optionValue2 = option.get(info2.getName());
                        }
                        boolean b = false;

                        if(optionValue != null &&  optionValue.equals( info.getValue() ) ){
                            String infoStr= "{" + info.getName() + "=" + info.getValue() + "}";
                            templetFile.setTargetFileName( templetFile.getTargetFileName().replace(infoStr, "") );//剔除条件标记
                            b = true;
                        }
                        //路径中带有{}的处理
                        if(optionValue2 != null &&  optionValue2.equals( info2.getValue() ) ){
                            String infoStr= "{" + info2.getName() + "=" + info2.getValue() + "}";
                            templetFile.setDirectory( templetFile.getDirectory().replace(infoStr, "") );//剔除条件标记
                            b = true;
                        }
                        if(b){
                            buildCode(configuration,base.getOutputPath(),templetFile);
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<String> getTemplateNameList(String templatePath) {
        File dir=new File( templatePath );
        File[] fs = dir.listFiles();
        return Arrays.stream(fs)
                .filter(file -> file.isDirectory())
                .map(file -> file.getName())
                .collect(Collectors.toList());
    }

    @Override
    public List<Info> getParamList(String templatePath) {

        try (InputStream inputStream = new FileInputStream(templatePath+ File.separator + "config.yml")) {
            Yaml yaml = new Yaml();
            Map<String, String> data = yaml.load(inputStream);
            return data.entrySet()
                    .stream().collect(Collectors.toList())
                    .stream()
                    .map(m->{
                        Info info=new Info();
                        info.setName(m.getKey() );
                        info.setValue(m.getValue());
                        return info;
                    }  ).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    @Override
    public List<Option> getOptionList(String templatePath) {
        try (InputStream inputStream = new FileInputStream(templatePath+ File.separator + "option.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Map<String,Object>> data = yaml.load(inputStream);

            return data.entrySet()
                    .stream().collect(Collectors.toList())
                    .stream()
                    .map(m->{
                        Option option= new Option();
                        option.setName( m.getKey());
                        option.setTitle((String)m.getValue().get("title"));
                        option.setOptions( (List)m.getValue().get("options"));
                        return option;
                    }  ).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    private void buildCode( Configuration configuration ,String  targetPath, TempletFile templetFile){
        Writer out =null;
        try {
            configuration.setDirectoryForTemplateLoading(new File(templetFile.getPath()   ));
            // 获取模板
            Template template = configuration.getTemplate(templetFile.getFileName() );
            // 创建数据模型
            Map<String, Object> dataModel = new HashMap<>();

            if("table".equals( templetFile.getType() )){
                dataModel.put("table", templetFile.getTable());
                dataModel.put("db", templetFile.getSchema());
            }
            if("db".equals( templetFile.getType() )){
                dataModel.put("db", templetFile.getSchema());
            }
            dataModel.put("base", templetFile.getBase());
            dataModel.put("extend",templetFile.getBase().getExtend());  //扩展替换符
            dataModel.put("option",templetFile.getBase().getOption());  // 选项

            File target=new File(targetPath + File.separator + templetFile.getDirectory()  );
            if(!target.exists()){
                target.mkdirs();
            }

            // 处理模板
            out = new FileWriter(new File( targetPath+ File.separator+ templetFile.getDirectory() + File.separator + templetFile.getTargetFileName()));
            template.process(dataModel, out);

        } catch (Exception e) {
            //System.out.println("生成代码时发生错误："+templetFile.getType() + " "+ templetFile.getDirectory()+"  "+templetFile.getFileName() +"->" +templetFile.getTargetFileName()  );
            e.printStackTrace();
        }finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void  copyFile( String  targetPath, TempletFile templetFile ){

        templetFile.setDirectory( templetFile.getDirectory().replace("(i)","") );  //去掉标记
        File target=new File(targetPath+ templetFile.getDirectory()  );
        if(!target.exists()){
            target.mkdirs();
        }

        templetFile.setTargetFileName( templetFile.getFileName().replace("(i)","") );  //去掉标记

        Path sourcePath = Paths.get(templetFile.getPath() + File.separator + templetFile.getFileName()); // 源文件路径
        Path destPath = Paths.get(targetPath+ templetFile.getDirectory()  + File.separator+ templetFile.getTargetFileName()); // 目标文件路径
        try {
            Files.copy(sourcePath, destPath  , StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据目录查找所有模板文件
     * @param basePath
     * @return
     */
    public static List<TempletFile> getTempletList(String basePath)
    {
        basePath = basePath.replace("/", "\\");
        List<TempletFile> list=null;
        File root = new File(basePath);
        try {
            list=showAllFiles(basePath,root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    final static List<TempletFile> showAllFiles( String basePath, File dir) throws Exception{
        List<TempletFile> list=new ArrayList();

        File[] fs = dir.listFiles();
        for(int i=0; i<fs.length; i++){
            TempletFile templet=new TempletFile();
            File file=new File(fs[i].getAbsolutePath());
            templet.setPath(file.getParent() ); //绝对目录
            templet.setDirectory(file.getParent().replace( basePath, "") ); //相对目录
            templet.setFileName(file.getName());//文件名
            if(fs[i].isDirectory()){
                try{
                    list.addAll(  showAllFiles(basePath,fs[i])  );
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }else{
                list.add(templet);
            }

        }
        return list;
    }


    /**
     * 判断模板类型
     * @param templetFile
     * @return
     */
    private String getTemplateType(TempletFile templetFile){

        if(getPathType(templetFile.getDirectory()).equals("base") &&  getPathType(templetFile.getFileName()).equals("base")){
            return "base";
        }
        if(getPathType(templetFile.getDirectory()).equals("db") && !getPathType(templetFile.getFileName()).equals("table")){
            return "db";
        }
        if(getPathType(templetFile.getFileName()).equals("db") ){
            return "db";
        }
        if(getPathType(templetFile.getDirectory()).equals("table") || getPathType(templetFile.getFileName()).equals("table") ){
            return "table";
        }
        return "?";
    }



    private String getPathType(String path){
        if(isTablePath(path)){
            return "table";
        }
        if(isDbPath(path)){
            return "db";
        }
        return "base";
    }


    private boolean isTablePath(String path){
        if(path.contains("[table]")){
            return true;
        }
        if(path.contains("[Table]")){
            return true;
        }
        return false;
    }


    private boolean isDbPath(String path){
        if(path.contains("[table]")){
            return false;
        }
        if(path.contains("[Table]")){
            return false;
        }
        if(path.contains("[db]")){
            return true;
        }
        if(path.contains("[Db]")){
            return true;
        }
        if(path.contains("[service]")){
            return true;
        }
        return false;
    }

    private String  replaceTable( String path, Table table){
        return path.replace("[table]",table.getName())
                .replace("[Table]",table.getClassName());

    }


    private String  replaceDb( String path,Schema schema){
        return path.replace("[db]",schema.getName()) //数据库原始名称
                .replace("[Db]", schema.getClassName() )  //大写开头的数据库名
                .replace( "[service]", schema.getServiceName()   );  //服务名
    }

    private String replaceBase( String path ,Base base){
        return path.replace( "[path]",base.getBasePath() )
                .replace("[project]",base.getProject());
    }

    /**
     * 获取模板路径中的选项
     * @param path
     * @return
     */
    private Info getTemplateOption(String path){
        //判断是否有条件表达式
        if( path.contains("}") && path.contains("{")  && path.contains("=") ){
            int index1 = path.indexOf("{");
            int index2= path.indexOf("}");
            int index3= path.indexOf("=");

            if(index1<index3 && index1<index2 && index3<index2){
                String substring = path.substring(index1+1,index2 );
                String[] split = substring.split("=");
                Info info=new Info();
                info.setName( split[0] );
                info.setValue( split[1] );
                return info;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

}
