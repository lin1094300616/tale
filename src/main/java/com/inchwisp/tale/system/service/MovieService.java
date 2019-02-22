package com.inchwisp.tale.system.service;

import com.inchwisp.tale.system.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName: MovieService
 * @Description: 电影模块服务层接口
 * @Author: MSI
 * @Date: 2019/1/24 14:06
 * @Vresion: 1.0.0
 **/
public interface MovieService {



    Page<Movie> pageMovie(String name, Pageable pageable);

    void save(Movie movie);

    void delete(Movie movie);

    Movie findById(Long id);

    List<Movie> findAll();
}
