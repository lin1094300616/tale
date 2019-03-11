package com.inchwisp.tale.framework.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.inchwisp.tale.framework.configurer.Permission;
import com.inchwisp.tale.framework.entity.Response;
import com.inchwisp.tale.framework.entity.StatusEnum;
import com.inchwisp.tale.system.model.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName: loginInterceptor
 * @Description: TODO
 * @Author: MSI
 * @Date: 2019/1/4 8:46
 * @Vresion: 1.0.0
 **/
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * @Author MSI
     * @Description 调用Controller方法之前执行，判断用户是否登陆以及验证是否有权限
     * @Date 2019/1/4 11:32
     * @Param [request, response, handler]
     * @return boolean 
     **/       
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        //1、判断用户存在session中
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            //1.1、用户不存在，写入错误信息并返回
            PrintWriter printWriter = response.getWriter();
            Response res = Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9001.getCode(),StatusEnum.SYSTEM_ERROR_9001.getData());
            printWriter.append(JSONObject.toJSONString(res));
            return false;
        }
        //2、判断用户是否拥有权限，如果没有就返回信息
        if (!(handler instanceof HandlerMethod)) {
            PrintWriter printWriter = response.getWriter();
            Response res = Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9005.getCode(),StatusEnum.SYSTEM_ERROR_9005.getData());
            printWriter.append(JSONObject.toJSONString(res));
            return false;
        }

        if(!isPermission(handler,user)) {
            PrintWriter printWriter = response.getWriter();
            Response res = Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9003.getCode(),StatusEnum.SYSTEM_ERROR_9003.getData());
            printWriter.append(JSONObject.toJSONString(res));
            return false;
        }
         return true;
    }

    /**
     * @Author MSI
     * @Description 判断用户是否拥有权限
     * @Date 2019/1/4 11:28
     * @Param [handler, user]
     * @return boolean 
     **/       
    public boolean isPermission(Object handler,User user){
        //1、获得HandlerMethod
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //2、获得访问方法的Permission 注解获得权限。
            Permission permission = handlerMethod.getMethod().getAnnotation(Permission.class);
            //4、判断访问路径是否需要权限以及用户是否有此权限
            if (permission == null || "管理员".equals(user.getRoleName())) {
                return true;
            }
            System.out.println("permission.value() = " + permission.value());
            if (user.getRoleName().equals(permission.value())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TODO
    }
}
