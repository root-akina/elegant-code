package cn.elegent.builder.controller;
import cn.elegent.builder.service.AssistantService;
import cn.elegent.builder.vo.AssistantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/assistant")
public class AssistantController {

    @Autowired
    private AssistantService assistantService;

    /**
     * 构建模板
     * @param assistantVO
     */
    @PostMapping("/build")
    public void build(@RequestBody AssistantVO assistantVO){

        assistantVO.getContentReplaceList().addAll( assistantVO.getContentReplaceList2() );

        assistantService.buildTemplate(assistantVO.getSourcePath(),
                assistantVO.getTargetPath(),
                assistantVO.getPathReplaceList(),
                assistantVO.getContentReplaceList() );

    }


}
