package ${base.basePackage}.vo;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Pager<T> implements Serializable{

    /**
     * 当前页码
     */
    private long current;
    /**
     * 每页数量
     */
    private long size;
    /**
     * 总页数
     */
    //private long totalPage;
    /**
     * 总数据数量
     */
    private long total;
    /**
     * 当前页数据
     */
    private List<T> records;


    public long getTotalPage(){
        //if(pageSize == 0) return 0;
        //totalPage = totalCount%pageSize ==0? (totalCount/pageSize) : (totalCount/pageSize) +1;

        return 0;
    }

    

	<#if option.persistence="mybatis">
    public static <T> Pager<T> build(com.github.pagehelper.PageInfo<T> pageInfo){
        Pager<T> pageResult = new Pager<>();
        pageResult.setRecords(pageInfo.getList());
        pageResult.setCurrent(pageInfo.getPageNum());
        pageResult.setSize(pageInfo.getPageSize());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }
	</#if>


    <#if option.persistence="mybatisPlus">
    public static <T> Pager<T> build(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> page){
        Pager<T> pageResult = new Pager<>();
        pageResult.setRecords(page.getRecords());
        pageResult.setCurrent(page.getCurrent());
        pageResult.setSize(page.getSize());
        pageResult.setTotal(page.getTotal());

        return pageResult;
    }
    </#if>




    /**
     * 构建空数据
     * @param <T>
     * @return
     */
    public static <T> Pager<T> buildEmpty(){
        Pager<T> pageResult = new Pager<>();
        pageResult.setRecords(Lists.newArrayList());
        pageResult.setCurrent(0);
        pageResult.setSize(0);
        pageResult.setTotal(0);

        return pageResult;
    }


    /**
     * 重新构建
     * @param <T>
     * @return
     */
    public static <T> Pager<T> build( Pager pager ,List<T> list ){
        Pager<T> pageResult = new Pager<>();
        pageResult.setRecords(list);
        pageResult.setCurrent(pager.getCurrent());
        pageResult.setSize(pager.getSize());
        pageResult.setTotal(pager.getTotal());
        return pageResult;
    }


}
