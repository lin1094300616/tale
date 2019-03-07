package com.inchwisp.tale.system.dao;

import com.inchwisp.tale.system.model.Performer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * @ClassName: PerformerRepository
 * @Description: TODO
 * @Author: MSI
 * @Date: 2019/1/31 10:58
 * @Vresion: 1.0.0
 **/
@Repository
public interface PerformerRepository extends JpaRepository<Performer,Long> {

    List<Performer> findAllByIdIn(List<Long> id);

    Performer findByNameAndBirthday(String name, Date birthday);
}
