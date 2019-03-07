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
    SYSTEM_ERROR_9004("9004","文件存储异常"),

    RET_INSERT_FAIL("A001","新增数据库记录失败"),RET_UPDATE_FAIL("A002","修改数据库记录失败"),
    RET_DELETE_FAIL("A003","删除数据库记录失败"),

    USER_ERROR_1001("1001","用户名或密码不正确"),USER_ERROR_1002("1002","用户状态异常"),
    USER_ERROR_1003("1003","两次密码输入不一致"),USER_ERROR_1004("1004","用户名或电话号码已存在"),
    USER_ERROR_1005("1005","用户不存在"),

    MOVIE_ERROR_2001("2001","电影已存在"),MOVIE_ERROR_2002("2002","电影不存在"),
    MOVIE_ERROR_2003("2003","上传电影图片异常"),MOVIE_ERROR_2004("",""),
    MOVIE_ERROR_2005("",""),MOVIE_ERROR_2006("",""),

    DIRECTOR_ERROR_3001("3001","导演已存在"),DIRECTOR_ERROR_3002("3002","导演不存在"),
    DIRECTOR_ERROR_3003("3003","上传导演图片异常"),DIRECTOR_ERROR_3004("",""),
    DIRECTOR_ERROR_3005("",""),DIRECTOR_ERROR_3006("",""),

    PERFORMER_ERROR_4001("4001","演员已存在"),PERFORMER_ERROR_4002("4002","演员不存在"),
    PERFORMER_ERROR_4003("4003","关联演员不正确"),PERFORMER_ERROR_4004("",""),
    ;

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
