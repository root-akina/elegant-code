package cn.elegent.builder.vo;

import cn.elegent.builder.domain.DBConnection;
import cn.elegent.builder.domain.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ShowRequestVo {

    private DBConnection dbConnection;

    private List<Schema> schemaList;

}
