package com.inchwisp.tale.system.service;

import com.inchwisp.tale.system.model.Movie;

import java.util.List;

/**
 * @ClassName: MovieService
 * @Description: TODO
 * @Author: MSI
 * @Date: 2019/1/24 14:06
 * @Vresion: 1.0.0
 **/
public interface MovieService {

    void save(Movie movie);

    void delete(Movie movie);

    Movie findById(Long id);

    List<Movie> findAll();
}
