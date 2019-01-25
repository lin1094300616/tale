package com.inchwisp.tale.system.dao;

import com.inchwisp.tale.system.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: MovieRepository
 * @Description: 电影模块持久层接口
 * @Author: MSI
 * @Date: 2019/1/18 18:31
 * @Vresion: 1.0.0
 **/
public interface MovieRepository extends JpaRepository<Movie,Long> {

    /**
     * @Author MSI
     * @Description 修改电影的导演ID为空，删除导演时用
     * @Content: TODO
     * @Date 2019/1/25 10:23
     * @Param [directorId] 导演ID
     * @return void
     **/
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("update Movie movie set movie.director = null where movie.director = ?1")
    void updateOnDirector(Long directorId);
}
