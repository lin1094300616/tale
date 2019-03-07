package com.inchwisp.tale.framework.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

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

    /**
     * @Author MSI
     * @Description 判断String 类型是否有空字符串，如果有就返回true，没有就返回false
     * @Content  调整算法判断顺序，修复bug
     * @Date 2019/1/9 18:49
     * @Param [values]
     * @return boolean 
     **/
    public static boolean isNullString(String...values) {
        for (int i = 0; i < values.length; i++) {// 1.字符串为空， 2.空字符串
            if (values[i] == null || "".equals(values[i].trim()) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Author MSI
     * @Description 分割ID字符串，产生ID集合
     * @Content: 1.对ID字符串进行分割，产生ID数组
     *            2.遍历数组，装进List集合，并返回
     * @Date 2019/3/7 11:40
     * @Param [ids] 多个ID组成的字符串，ID之间用 '，'隔开 ，如 1，2，3
     * @return java.util.List<java.lang.Long>
     **/
    public static List<Long> splitString(String ids) {
        List<Long> longList = new LinkedList<>();
        //1.按 "," 分割字符串，产生字符串数组
        String[] idArray = ids.split(",");
        //2.遍历字符串数组
        for (int i = 0; i < idArray.length; i++) {
            //2.1转换成Long类型，加入到Set集合中
            longList.add(Long.valueOf(idArray[i]));
        }
        //3.返回集合
        return longList;
    }

}
