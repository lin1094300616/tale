package com.inchwisp.tale.system.dao;

import com.inchwisp.tale.system.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: MovieRepository
 * @Description: 电影模块持久层接口
 * @Author: MSI
 * @Date: 2019/1/18 18:31
 * @Vresion: 1.0.0
 **/
@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    /**
     * @Author MSI
     * @Description 根据名称查找电影资源
     * @Content: TODO
     * @Date 2019/1/31 17:07
     * @Param [keywords, pageable]
     * @return org.springframework.data.domain.Page<com.inchwisp.tale.system.model.Movie>
     **/
    @Query(value = "SELECT movie FROM Movie AS movie " +
                    "WHERE movie.name LIKE CONCAT('%',:keywords,'%')  " +
                    "OR movie.alias LIKE CONCAT('%',:keywords,'%')"
    )
    Page<Movie> searchByNameORAlias(@Param("keywords") String keywords, Pageable pageable);

    /**
     * @Author MSI
     * @Description 删除导演时将其关联的电影的导演ID置空
     * @Content: TODO
     * @Date 2019/1/25 10:23
     * @Param [directorId] 导演ID
     * @return void
     **/
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    //update Movie set director_id = 0 where director_id = 3000026
    @Query("update Movie movie set movie.director = 0 where movie.director = ?1")
    void updateOnDirector(Long directorId);

    Movie findMovieByName(String name);
}
