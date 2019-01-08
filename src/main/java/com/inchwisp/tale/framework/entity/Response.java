package com.inchwisp.tale.framework.entity;

/**
 * @ClassName:  Response
 * @Description:  响应对象封装类,所有Contorller 返回数据使用此类格式
 * @Author:  MSI
 * @Date:  2018/12/23  15:38
 * @Vresion: 1.0.0
 **/
/**

 * by lcm 2018/12/23
 */
public class Response {

    private Object code;    //返回结果代码
    private Object data;    //返回结果内容数据

    public Response() {
        this.code = StatusEnum.RESPONSE_OK.getCode();
    }

    public Response(Object code, Object data) {
        setCode(code);
        setData(data);
    }

    public static Response factoryResponse(String code,Object data){
        return new Response(code,data);
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
