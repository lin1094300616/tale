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
 * @Description: TODO
 * @Author: MSI
 * @Date: 2019/1/2 14:21
 * @Vresion: 1.0.0
 **/
@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * @Author MSI
     * @Description 登陆
     * @Date 2019/1/3 15:58
     * @Param [name]
     * @return com.inchwisp.tale.system.model.User 
     **/       
    @Override
    public User findByAccountOrPhone(String account,String phone) {
        return userRepository.findByAccountOrPhone(account,phone);
    }

    @Override
    public Page<User> search(String name, Pageable pageable) {
        Page<User> userPage = userRepository.findByNameLike(name,pageable);
        return userPage;
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
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


}
