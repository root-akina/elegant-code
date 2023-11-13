package cn.elegent.builder.util;

import java.io.File;

public class StringUtils {

    /**
     * 字符串转换为驼峰
     * @param input
     * @return
     */
    public static String convertToCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNextChar = false;
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (currentChar == '_') {
                capitalizeNextChar = true;
            } else {
                if (capitalizeNextChar) {
                    result.append(Character.toUpperCase(currentChar));
                    capitalizeNextChar = false;
                } else {
                    result.append(Character.toLowerCase(currentChar));
                }
            }
        }
        return result.toString();
    }


    /**
     * 首字母小写
     * @return
     */
    public static String initialToLowerCase(String str){
        return str.substring(0, 1).toLowerCase()+str.substring(1);//首字母改小写
    }


    /**
     * 首字母大写
     * @return
     */
    public static String initialToUpperCase(String str){
        return str.substring(0, 1).toUpperCase()+str.substring(1);//首字母改大写
    }


    /**
     * 表名改类名
     * @param tableName
     * @param prefixs
     * @return
     */
    public static String convertTableNameToClassName(String tableName,String[] prefixs){
        //去掉前缀
        for(String prefix:prefixs  ){
            if(tableName.startsWith(prefix)){
                tableName=tableName.substring(prefix.length());
            }
        }
        String className = convertToCamelCase(tableName);
        return initialToUpperCase(  className  );//首字母改大写
    }


    public static String convertPackageToPath(String pack){
        return pack.replace(".", File.separator);
    }


    public static String convertToServiceName( String name ){
        return name.replace("_","-");
    }



}