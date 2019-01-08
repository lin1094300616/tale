package com.inchwisp.tale.framework.util;

import java.security.MessageDigest;

/**
 * @ClassName: MD5Util
 * @Description: MD5加密算法工具类
 * @Author: MSI
 * @Date: 2019/1/3 14:22
 * @Vresion: 1.0.0
 **/
public class MD5Util {
    /**
     * @Author MSI
     * @Description MD5加密
     * @Date 2018/12/23 18:51
     * @Param [key] 需要加密的字符串
     * @return java.lang.String 加密后的字符串，定长32字节
     **/
    public static String MD5(String key) {
        // 设定密文字符范围
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            // 将字符串转换成字节数组
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            //每次取密文字节数组的一个值，根据其二进制值找到密文字符，写入成加密后的报文
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];     // 假设此时md[i]的值是100，转换成二进制是 0110 0100
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];    //取0110，十进制为6，对应字符 '5'
                str[k++] = hexDigits[byte0 & 0xf];          //取0110，十进制为4，对应字符 '3'，所以写入的是'5'和'3'
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }
}
