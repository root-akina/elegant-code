package cn.elegent.builder.vo;

import lombok.Data;

import java.util.List;

@Data
public class AssistantVO {

    private String sourcePath;
    private String targetPath;
    private List<ReplaceVO> pathReplaceList;
    private List<ReplaceVO> contentReplaceList;
    private List<ReplaceVO> contentReplaceList2;

}
