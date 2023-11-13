package cn.elegent.builder.service;

import cn.elegent.builder.vo.ReplaceVO;

import java.util.List;

public interface AssistantService {


    void buildTemplate(String sourcePath, String targetPath,List<ReplaceVO> pathReplaceList, List<ReplaceVO> contentReplaceList );

}
