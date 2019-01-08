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
     * @Description 登陆
     * @Date 2019/1/3 15:58
     * @Param [name]
     * @return com.inchwisp.tale.system.model.User 
     **/       
    User findByAccountOrPhone(String account,String phone);

    Page<User> search(String name, Pageable pageable);

    void saveUser(User user);

    void deleteUser(User user);

    User findById(long id);

    List<User> findAll();
}
