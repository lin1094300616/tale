package com.inchwisp.tale.system.service;

import com.inchwisp.tale.system.model.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: Director
 * @Description: 导演模块服务层接口
 * @Author: MSI
 * @Date: 2019/1/19 14:34
 * @Vresion: 1.0.0
 **/
public interface DirectorService {

    Director findByNameAndBirthday(String name, String birthday);

    Page<Director> pageDirector(String name, Pageable pageable);

    void saveDirector(Director director);

    void deleteDirector(Director director);

    Director findById(long id);

    List<Director> findAll();
}
