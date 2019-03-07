package com.inchwisp.tale.framework.entity;

/**
 * @ClassName: ConstantsEnum
 * @Description: 系统常量枚举类，存放常量，格式为： 常量名（常量值，"常量注释"）
 * @Author: MSI
 * @Date: 2019/1/2 15:18
 * @Vresion: 1.0.0
 **/
public enum ConstantsEnum {

    TOKEN_TIMEOUT_MINUTES(5,"登陆有效时间"),

    ACCOUNT_STATUS_VALID(1,"可用"),ACCOUNT_STATUS_INVALID(0,"禁用"),
    ACCOUNT_IS_ONLINE(1,"在线"),ACCOUNT_IS_OFFLINE(0,"离线");

    public static final Integer PAGE_DEFT = 1; //默认分页查询页面
    public static final Integer SIZE_DEFT = 10; //默认分页查询每页条数

    public static final String UPLOAD = "D:\\IDEAwork\\tale\\upload\\";

    private int value;
    private String data;

    ConstantsEnum(int value, String data) {
        this.value = value;
        this.data = data;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
