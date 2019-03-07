package com.inchwisp.tale.system.controller;

import com.inchwisp.tale.framework.entity.Response;
import com.inchwisp.tale.framework.entity.StatusEnum;
import com.inchwisp.tale.framework.util.CommUtil;
import com.inchwisp.tale.system.model.Movie;
import com.inchwisp.tale.system.model.Performer;
import com.inchwisp.tale.system.service.MovieService;
import com.inchwisp.tale.system.service.PerformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

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

    @Autowired
    PerformerService performerService;

    public Response add(@RequestParam("name") String name,
                         @RequestParam(value = "alias",defaultValue = "无") String alias,
                         @RequestParam(value = "region") String region,
                         @RequestParam("language") String language,
                         @RequestParam(value = "tag",defaultValue = "无") String tag,
                         @RequestParam(value = "introduction",defaultValue = "无") String introduction,
                         @RequestParam("resource") String resource,
                         @RequestParam("image") MultipartFile multipartFile,
                         @RequestParam(value = "lasts",defaultValue = "00:00:00") String lasts,
                         @RequestParam("releaseDate") String releaseDate,
                         @RequestParam("performerId") String performerId) {
        //1.1 验证完整性
        if (CommUtil.isNullString(name,region,language,resource,releaseDate)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //1.2 验证电影是否存在
        if (movieService.findMovieByName(name) != null) {
            return Response.factoryResponse(StatusEnum.MOVIE_ERROR_2001.getCode(),StatusEnum.MOVIE_ERROR_2001.getData());
        }
        //1.3验证演员是否存在
        List<Performer> performerList = performerService.findByIdIn(CommUtil.splitString(performerId));
        if (performerList == null) {
            return Response.factoryResponse(StatusEnum.PERFORMER_ERROR_4003.getCode(),StatusEnum.PERFORMER_ERROR_4003.getData());
        }
        //2 封装数据
        Movie movie = new Movie();
        movie.setName(name);
        movie.setAlias(alias);
        movie.setRegion(region);
        movie.setLanguage(language);
        movie.setTag(tag);
        movie.setIntroduction(introduction);
        movie.setResource(resource);
        movie.setLasts(Date.valueOf(lasts));
        movie.setReleaseDate(Date.valueOf(releaseDate));
        movie.setPerformers(performerList);
        //3 保存电影
        try {
            movieService.save(movie);
        }catch (Exception e) {
            return Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),movie);

    }

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
