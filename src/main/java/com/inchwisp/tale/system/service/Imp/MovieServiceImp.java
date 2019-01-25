package com.inchwisp.tale.system.service.Imp;

import com.inchwisp.tale.system.dao.MovieRepository;
import com.inchwisp.tale.system.model.Movie;
import com.inchwisp.tale.system.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName: MovieServiceImpl
 * @Description: 电影模块服务层实现类
 * @Author: MSI
 * @Date: 2019/1/24 14:07
 * @Vresion: 1.0.0
 **/
@Service
public class MovieServiceImp implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }

    @Override
    public Movie findById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        return movieOptional.isPresent() ? movieOptional.get() : null;
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

}
