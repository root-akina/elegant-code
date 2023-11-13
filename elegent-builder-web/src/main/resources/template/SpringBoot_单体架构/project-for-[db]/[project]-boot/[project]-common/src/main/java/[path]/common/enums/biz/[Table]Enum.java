package ${base.basePackage}.common.enums.biz;

import ${base.basePackage}.common.basic.IBasicEnum;

/**
 * @ClassName ${table.className}Enum.java
 * @Description TODO
 */
public enum ${table.className}Enum implements IBasicEnum {

    SUCCEED(200,"操作成功"),
    FAIL(1000,"操作失败"),
    PAGE_FAIL(55001, "分页查询${table.comment}表列表失败"),
    FIND_FAIL(55002, "查询${table.comment}表失败"),
    SAVE_FAIL(55003, "保存${table.comment}表失败"),
    UPDATE_FAIL(55004, "修改${table.comment}表失败"),
    DEL_FAIL(55005, "删除${table.comment}表失败"),
    LIST_FAIL(55006, "查询${table.comment}表失败")
    ;

    private Integer code;

    private String msg;

    ${table.className}Enum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
