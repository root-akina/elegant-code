package cn.elegent.builder.domain;

import lombok.Data;

import java.util.List;

/**
 * 选项
 */
@Data
public class Option {

    private String name;

    private String title;//标题（中文）

    private String value;//选择的值

    private List<String> options;

}
