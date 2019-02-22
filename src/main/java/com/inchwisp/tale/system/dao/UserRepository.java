package com.inchwisp.tale.system.dao;

import com.inchwisp.tale.system.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserDao
 * @Description: 用户DAO接口，继承JPA的JpaRepository接口，JpaRepository<实体名,主键数据类型>
 *                可以直接使用其接口的基本方法（增删改查）
 *                需要新方法可以根据关键字编写接口，JPA会自动完成查询语句。
 * @Author: MSI
 * @Date: 2019/1/2 14:15
 * @Vresion: 1.0.0
 **/
@Repository //Repository注解，表示这个类是一个持久层类（DAO），用于与数据库交互
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * @Author MSI
     * @Description 根据用户姓名模糊分页查询
     * @Content: TODO
     * @Date 2019/2/7 20:10
     * @Param [name, pageable]
     * @return org.springframework.data.domain.Page<com.inchwisp.tale.system.model.User> 
     **/       
    Page<User> findAllByNameContaining(@Param("name") String name, Pageable pageable);

    /**
     * @Author MSI
     * @Description 查询用户账号和手机号码是否存在
     * @Content: TODO
     * @Date 2019/2/7 20:10
     * @Param [account, phone]
     * @return com.inchwisp.tale.system.model.User 
     **/       
    //@Query(value = "SELECT user FROM User AS user WHERE user.account = keyword OR  user.phone = keyword")
    User findByAccountOrPhone(String account,String phone);
}
