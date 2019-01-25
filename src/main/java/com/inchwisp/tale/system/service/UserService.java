package com.inchwisp.tale.system.service;

import com.inchwisp.tale.system.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName: UserService
 * @Description: 用户服务层
 * @Author: MSI
 * @Date: 2019/1/2 14:21
 * @Vresion: 1.0.0
 **/
public interface UserService {

    /**
     * @Author MSI
     * @Description 根据用户名或者电话号码查询是否存在此用户
     * @Date 2019/1/9 8：59
     * @Param [name]
     * @return com.inchwisp.tale.system.model.User 
     **/       
    User findByAccountOrPhone(String account,String phone);

    Page<User> pageUser(String name, Pageable pageable);

    void saveUser(User user);

    void deleteUser(User user);

    User findById(long id);

    List<User> findAll();
}
