package com.inchwisp.tale.system.dao;

import com.inchwisp.tale.system.model.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

/**
 * @ClassName: DirectorRepository
 * @Description: TODO
 * @Author: MSI
 * @Date: 2019/1/18 18:29
 * @Vresion: 1.0.0
 **/
@Repository
public interface DirectorRepository extends JpaRepository<Director,Long> {

    Director findByNameAndBirthday(String name,Date birthday);

    /**
     * @Author MSI
     * @Description 分页查询
     * @Content: 条件：姓名（模糊）
     * @Date 2019/1/25 11:03
     * @Param [name, pageable] 姓名，分页参数
     * @return Page<Director> 导演集合+分页信息
     **/
    Page<Director> findAllByNameContaining(@Param("name") String name, Pageable pageable);
}
