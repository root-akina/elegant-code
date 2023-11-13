package cn.elegent.builder.service.impl;

import cn.elegent.builder.service.AssistantService;
import cn.elegent.builder.util.AssistantUtil;
import cn.elegent.builder.vo.ReplaceVO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AssistantServiceImpl implements AssistantService {





    /**
     * 根据目录查找所有模板文件
     * @param basePath
     * @return
     */
    private List<String> getFileList(String basePath)
    {
        basePath = basePath.replace("/", "\\");
        List<String> list=null;
        File root = new File(basePath);
        try {
            list=showAllFiles(basePath,root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    final List<String> showAllFiles( String basePath, File dir) throws Exception{
        List<String> list=new ArrayList();
        File[] fs = dir.listFiles();
        for(int i=0; i<fs.length; i++){

            File file=new File(fs[i].getAbsolutePath());
            if(fs[i].isDirectory()){
                try{
                    list.addAll(  showAllFiles(basePath,fs[i])  );
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }else{
                list.add(file.getParent().replace( basePath, "") +File.separator + file.getName());
            }
        }
        return list;
    }


    /**
     * 构建模板
     * @param sourcePath
     * @param targetPath
     * @param pathReplaceList
     * @param contentReplaceList
     */
    @Override
    public void buildTemplate(String sourcePath, String targetPath, List<ReplaceVO> pathReplaceList, List<ReplaceVO> contentReplaceList) {

        //获取要替换的文件列表
        List<String> fileList = getFileList(sourcePath);

        for(String fileName : fileList){

            try {
                //如果不是 Asc码文件，则直接拷贝
                if(! AssistantUtil.isAsciiFile( new File(sourcePath+ File.separator+ fileName) ) ){
                    //拷贝
                    AssistantUtil.copyFile( sourcePath+ File.separator+ fileName  , targetPath+ File.separator+ fileName  );
                }else{
                    String content = AssistantUtil.getContent(sourcePath + File.separator + fileName);

                    //替换内容
                    for(ReplaceVO replaceVO :contentReplaceList ){
                        content = content.replace(replaceVO.getSourceStr(), replaceVO.getTargetStr());
                    }
                    //替换文件名
                    for(ReplaceVO replaceVO :pathReplaceList ){
                        fileName = fileName.replace(replaceVO.getSourceStr(), replaceVO.getTargetStr());
                    }
                    AssistantUtil.setContent( targetPath+File.separator+ fileName , content  );
                }
            } catch (Exception e) {

                e.printStackTrace();
            }

        }


    }


}
