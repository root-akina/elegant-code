package ${base.basePackage}.project.vo;

import lombok.*;

import java.io.Serializable;
@Data
public class ${table.className}VO implements Serializable{


<#foreach column in table.columns>
  
   private ${column.propertyType} ${column.property};//${column.comment}
   
</#foreach>


}
