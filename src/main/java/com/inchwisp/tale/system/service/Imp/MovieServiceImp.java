package com.inchwisp.tale.system.service.Imp;

import com.inchwisp.tale.framework.util.FileUtil;
import com.inchwisp.tale.system.dao.MovieRepository;
import com.inchwisp.tale.system.model.Movie;
import com.inchwisp.tale.system.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
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

    /**
     * @Author MSI
     * @Description 查询电影是否存在
     * @Content: TODO
     * @Date 2019/3/6 21:17
     * @Param [name]
     * @return com.inchwisp.tale.system.model.Movie 
     **/       
    @Override
    public Movie findMovieByName(String name) {
        return movieRepository.findMovieByName(name);
    }

    @Override
    public Page<Movie> pageMovie(String name, Pageable pageable) {
        return movieRepository.searchByNameORAlias(name,pageable);
    }

    @Override
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Movie movie) {
        FileUtil.deleteDir(new File(movie.getImage()));
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
