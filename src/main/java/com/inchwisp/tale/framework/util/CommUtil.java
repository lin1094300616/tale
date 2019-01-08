package com.inchwisp.tale.framework.util;

/**
 * @ClassName: CommUtil
 * @Description: 公共工具类，提供判断参数是否为空
 * @Author: MSI
 * @Date: 2018/12/23 16:48
 * @Vresion: 1.0.0
 **/
public class CommUtil {

    /**
     * @Author MSI
     * @Description 判断变量是否为空值,如果有空值则返回true，没有就返回false
     * @Date 2018/12/23 17:58
     * @Param [values]
     * @return boolean
     **/
    public static boolean isNullValue(Object...values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                return true;
            }
        }
        return false;
    }

    //
    /**
     * @Author MSI
     * @Description 判断String 类型是否有空字符串，如果有就返回true，没有就返回false
     * @Date 2018/12/23 18:09
     * @Param [values]
     * @return boolean 
     **/        
    public static boolean isNullString(String...values) {
        for (int i = 0; i < values.length; i++) {
            if ("".equals(values[i].trim()) || values == null) {
                return true;
            }
        }
        return false;
    }




}
