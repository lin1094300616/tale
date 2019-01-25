package com.inchwisp.tale.system.service.Imp;

import com.inchwisp.tale.system.dao.UserRepository;
import com.inchwisp.tale.system.model.User;
import com.inchwisp.tale.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName: UserServiceImp
 * @Description: 用户模块服务层实现类
 * @Author: MSI
 * @Date: 2019/1/2 14:21
 * @Vresion: 1.0.0
 **/
@Service //表示这是一个service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * @Author MSI
     * @Description 根据用户名或者电话号码查询是否存在此用户
     * @Date 2019/1/3 15:58
     * @Param [name]
     * @return com.inchwisp.tale.system.model.User 
     **/       
    @Override
    public User findByAccountOrPhone(String account,String phone) {
        return userRepository.findByAccountOrPhone(account,phone);
    }

    @Override
    public Page<User> pageUser(String name, Pageable pageable) {
        return userRepository.findAllByNameContaining(name,pageable);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findById(long id) {
        Optional<User> optionalUser = userRepository.findById(id); //findById方法会固定返回一个Optional对象，需要验证里面是否存在
        if (optionalUser.isPresent()) {
            return optionalUser.get();  //用户数据存在，返回查找到的数据
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
