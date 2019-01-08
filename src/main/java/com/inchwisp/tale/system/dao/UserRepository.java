package com.inchwisp.tale.system.dao;

import com.inchwisp.tale.system.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * @ClassName: UserDao
 * @Description: TODO
 * @Author: MSI
 * @Date: 2019/1/2 14:15
 * @Vresion: 1.0.0
 **/
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select user from User as user where user.name like CONCAT('%',:name,'%')")
    Page<User> findByNameLike(@Param("name") String name, Pageable pageable);

   // @Query(value = "SELECT user FROM User AS user WHERE user.account = keyword OR  user.phone = keyword")
    User findByAccountOrPhone(String account,String phone);
}
