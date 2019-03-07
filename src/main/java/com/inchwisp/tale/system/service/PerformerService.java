package com.inchwisp.tale.system.service;

import com.inchwisp.tale.system.model.Performer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

/**
 * @ClassName: PerformerService
 * @Description: 演员模块服务层接口类
 * @Author: MSI
 * @Date: 2019/1/31 10:47
 * @Vresion: 1.0.0
 **/
public interface PerformerService {

    List<Performer> findByIdIn(List<Long> id);

    Performer findByNameAndBirthday(String name,Date birthday);

    Page<Performer> pagePerformer(Pageable pageable);

    void save(Performer performer);

    void delete(Performer performer);

    Performer findById(Long id);

    List findAll();
}
