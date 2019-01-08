package com.inchwisp.tale.system.controller;

import com.inchwisp.tale.framework.configurer.Permission;
import com.inchwisp.tale.framework.entity.*;
import com.inchwisp.tale.framework.util.CommUtil;
import com.inchwisp.tale.framework.util.MD5Util;
import com.inchwisp.tale.framework.util.PageUtil;
import com.inchwisp.tale.system.model.User;
import com.inchwisp.tale.system.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName:  UserController
 * @Description:  用户控制器
 * @Author:  MSI
 * @Date:  2019/1/2 16：50
 * @Vresion:  1.0.0
 **/
@RestController
public class UserController {

    @Autowired //Spring自动装配
    UserService userService; //注入userService

    /**
     * @Author MSI
     * @Description 用户登陆
     * @Date 2019/1/3 17:19
     * @Param [data, session]
     * @return com.inchwisp.tale.framework.entity.Response
     **/
    @PostMapping("/login")
    public Response login(@RequestBody JSONObject data, HttpSession session) {
        //1、获取数据，验证完整性
        String account = data.getString("account");
        String phone = data.getString("phone");
        String password = data.getString("password");
        if (CommUtil.isNullString(password)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //2、查询数据库，获得用户信息
        User user = userService.findByAccountOrPhone(account,phone);
        //3、判断用户信息————不存在，用户被禁用，密码不正确
        if (user == null) {
            return Response.factoryResponse(StatusEnum.USER_Error_1005.getCode(),StatusEnum.USER_Error_1005.getData());
        }
        if (user.getIsEnabled() == ConstantsEnum.ACCOUNT_STATUS_INVALID.getValue()) {
            return Response.factoryResponse(StatusEnum.USER_Error_1002.getCode(),StatusEnum.USER_Error_1002.getData());
        }
        if (!user.getPassword().equals(MD5Util.MD5(password))) {
            return Response.factoryResponse(StatusEnum.USER_Error_1001.getCode(),StatusEnum.USER_Error_1001.getData());
        }
        //4、验证成功，用户信息写入session
        session.setAttribute("user",user);
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),"登陆成功");
    }

    /**
     * @Author MSI
     * @Description 退出登陆
     * @Date 2019/1/3 17:29
     * @Param [data, session]
     * @return com.inchwisp.tale.framework.entity.Response 
     **/       
    @PostMapping("/loginOut")
    public Response loginOut(HttpSession session) {
        //查看session是否存在用户数据，有就清空
        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    /**
     * @Author MSI
     * @Description 用户注册
     * @Date 2019/1/6 18:24
     * @Param [data]
     * @return com.inchwisp.tale.framework.entity.Response
     **/
    @PostMapping("/register")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response register(@RequestBody JSONObject data) {
        //1、获取数据
        String account = data.getString("account");
        String password = data.getString("password");
        String passwordRetry = data.getString("passwordRetry");
        String name = data.getString("name");
        String phone = data.getString("phone");
        String email = data.getString("email");
        //2、判断，验证数据完整性
        if (CommUtil.isNullString(account,password,passwordRetry,name,phone)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //3、验证用户是否可以注册
        User user = userService.findByAccountOrPhone(account,phone);
        //3.1用户名已存在
        if (user.getAccount().equals(account)) {
            return Response.factoryResponse(StatusEnum.USER_Error_1004.getCode(),StatusEnum.USER_Error_1004.getData());
        }
        //3.2两次输入密码不一致
        if (!password.equals(passwordRetry)) {
            return Response.factoryResponse(StatusEnum.USER_Error_1003.getCode(),StatusEnum.USER_Error_1003.getData());
        }
        //3、数据封装
        user.setAccount(account);
        user.setPassword(MD5Util.MD5(password));//密码进行MD5加密
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setRoleId(ConstantsEnum.USER_ROLE_USER.getValue());
        user.setRoleName(ConstantsEnum.USER_ROLE_USER.getData());
        //4、调用Service，新增用户
        try {
            userService.saveUser(user);
        }catch (Exception e){
            return  Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
        return  Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    /**
     * @Author MSI
     * @Description 用户新增
     * @Date 2019/1/2 16:50
     * @Param [data]
     * @return Response
     **/
    @PostMapping("/user")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response addUser(@RequestBody JSONObject data) {
        //1、获取数据
        String account = data.getString("account");
        String password = data.getString("password");
        String passwordRetry = data.getString("passwordRetry");
        String name = data.getString("name");
        String phone = data.getString("phone");
        String email = data.getString("email");
        Integer roleId = data.getInteger("roleId");
        String roleName = data.getString("roleName");
        //2、判断，验证数据完整性
        if (CommUtil.isNullValue(roleId) || CommUtil.isNullString(account,password,passwordRetry,name,phone)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        if (!password.equals(passwordRetry)) {
            return Response.factoryResponse(StatusEnum.USER_Error_1003.getCode(),StatusEnum.USER_Error_1003.getData());
        }
        //3、数据封装
        User user = new User();
        user.setAccount(account);
        user.setPassword(MD5Util.MD5(password));//密码进行MD5加密
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setRoleId(roleId);
        user.setRoleName(roleName);
        //4、调用Service，新增用户
        try {
            userService.saveUser(user);
        }catch (Exception e){
            return  Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
        return  Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    /**
     * @Author MSI
     * @Description 用户删除
     * @Date 2019/1/3 9:56
     * @Param [id]
     * @return com.inchwisp.tale.framework.entity.Response
     **/
    @DeleteMapping("/user")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response deleteUser(@RequestBody JSONObject data) {
        //1、验证数据完整性
        Long id = data.getLong("id");
        if (CommUtil.isNullValue(id) ) {
            return  Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //2、判断用户是否存在
        User user = userService.findById(id);
        if (user == null) {
            return  Response.factoryResponse(StatusEnum.USER_Error_1005.getCode(),StatusEnum.USER_Error_1005.getData());
        }
        //3、删除用户
        try {
            userService.deleteUser(user);
        }catch (Exception e){
            return  Response.factoryResponse(StatusEnum.RET_DELETE_FAIL.getCode(),StatusEnum.RET_DELETE_FAIL.getData());
        }
        return  Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    /**
     * @Author MSI
     * @Description 修改用户信息
     * @Date 2019/1/3 14:30
     * @Param [data]
     * @return com.inchwisp.tale.framework.entity.Response
     **/
    @PutMapping("/user")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response updateUser(@RequestBody JSONObject data) {
        //1、获取数据
        Long id = data.getLong("id");
        String account = data.getString("account");
        String password = data.getString("password");
        String name = data.getString("name");
        String phone = data.getString("phone");
        String email = data.getString("email");
        Integer roleId = data.getInteger("roleId");
        String roleName = data.getString("roleName");
        //2、判断，验证数据完整性
        if (CommUtil.isNullValue(id,roleId) || CommUtil.isNullString(account,password,name)) {
            return  Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //3、数据封装
        User user = new User();
        user.setId(id);
        user.setAccount(account);
        user.setPassword(password);
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setRoleId(roleId);
        user.setRoleName(roleName);
        //4、调用Service，新增用户
        try {
            userService.saveUser(user);
        }catch (Exception e){
            return  Response.factoryResponse(StatusEnum.RET_UPDATE_FAIL.getCode(),StatusEnum.RET_UPDATE_FAIL.getData());
        }
        return  Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());

    }

    /**
     * @Author MSI
     * @Description 查询用户详情
     * @Date 2019/1/3 14:30
     * @Param [id]
     * @return com.inchwisp.tale.framework.entity.Response
     **/
    @GetMapping("/user/{id}")
    public Response detailUser(@PathVariable("id") Long id) {
        if (CommUtil.isNullValue(id) ) {
            return  Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //2、判断用户是否存在
        User user = userService.findById(id);
        if (user == null) {
            return Response.factoryResponse(StatusEnum.USER_Error_1005.getCode(),StatusEnum.USER_Error_1005.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),user);
    }

    /**
     * @Author MSI
     * @Description 查询用户列表
     * @Date 2019/1/3 15:56
     * @Param []
     * @return com.inchwisp.tale.framework.entity.Response 
     **/
    @GetMapping("/user")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response listUser() {
        List<User> userList = userService.findAll();
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),userList);
    }

    /**
     * @Author MSI
     * @Description 条件分页查询，可查询条件：姓名（模糊）
     * @Date 2019/1/7 16:03
     * @Param [size, page, name]
     * @return com.inchwisp.tale.framework.entity.Response 
     **/       
    @GetMapping("/user/{size}/{page}")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response searchUser(@PathVariable int size, @PathVariable int page, @RequestParam("name") String name) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<User> userPage = userService.search(name,pageable);
        JSONObject data = PageUtil.pageInfo(userPage);
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),data);
    }

}
