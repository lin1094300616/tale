package com.inchwisp.tale.framework.entity;

/**
 * @ClassName: StateEnum
 * @Description: 状态码枚举类
 * @Author: MSI
 * @Date: 2019/1/2 15:25
 * @Vresion: 1.0.0
 **/
public enum StatusEnum {
    //HEAD_PARAM_ACCOUNT("GrapeAccount","用户名的属性名"),HEAD_PARAM_TOKEN("GrapeToken","用户令牌的属性名"),

    RESPONSE_OK("0","请求成功"),

    SYSTEM_ERROR_9999("9999","系统抛出异常"),SYSTEM_ERROR_9001("9001","登录超时，请重新登录"),
    SYSTEM_ERROR_9002("9002","请求数据不完整"),SYSTEM_ERROR_9003("9003","权限不足"),

    RET_INSERT_FAIL("A001","新增数据库记录失败"),RET_UPDATE_FAIL("A002","修改数据库记录失败"),
    RET_DELETE_FAIL("A003","删除数据库记录失败"),

    USER_Error_1001("1001","用户名或密码不正确"),USER_Error_1002("1002","用户状态异常"),
    USER_Error_1003("1003","两次密码输入不一致"),USER_Error_1004("1004","用户名已存在"),
    USER_Error_1005("1005","用户不存在");

    private String code;
    private String data;

    StatusEnum(String code,String data) {
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
