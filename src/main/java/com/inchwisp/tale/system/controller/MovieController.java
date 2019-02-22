package com.inchwisp.tale.system.controller;

import com.inchwisp.tale.framework.entity.Response;
import com.inchwisp.tale.framework.entity.StatusEnum;
import com.inchwisp.tale.framework.util.CommUtil;
import com.inchwisp.tale.system.model.Movie;
import com.inchwisp.tale.system.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: MovieController
 * @Description: 电影模块控制器
 * @Author: MSI
 * @Date: 2019/1/31 10:43
 * @Vresion: 1.0.0
 **/
@RestController
public class MovieController {

    @Autowired
    MovieService movieService;

    /**
     * @Author MSI
     * @Description 查询电影详情
     * @Content: TODO
     * @Date 2019/1/31 17:24
     * @Param [id]
     * @return com.inchwisp.tale.framework.entity.Response
     **/
    @GetMapping("/movie/{id}")
    public Response detail(@PathVariable("id") Long id) {
        if (CommUtil.isNullValue(id)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return Response.factoryResponse(StatusEnum.MOVIE_ERROR_2002.getCode(),StatusEnum.MOVIE_ERROR_2002.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),movie);
    }

}
