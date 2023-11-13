package cn.elegent.builder.service;

import cn.elegent.builder.domain.DbType;

import java.util.List;

public interface DbTypeService {


    /**
     * 数据库类型列表
     * @return
     */
    List<DbType> dbTypeList();


}
