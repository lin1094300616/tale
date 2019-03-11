package com.inchwisp.tale.system.controller;

import com.inchwisp.tale.framework.configurer.Permission;
import com.inchwisp.tale.framework.entity.PermissionConstants;
import com.inchwisp.tale.framework.entity.Response;
import com.inchwisp.tale.framework.entity.StatusEnum;
import com.inchwisp.tale.framework.util.CommUtil;
import com.inchwisp.tale.system.model.Director;
import com.inchwisp.tale.system.model.Movie;
import com.inchwisp.tale.system.model.Performer;
import com.inchwisp.tale.system.service.DirectorService;
import com.inchwisp.tale.system.service.MovieService;
import com.inchwisp.tale.system.service.PerformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
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
    DirectorService directorService;

    @Autowired
    PerformerService performerService;

    @PostMapping("/movie")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
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
                         @RequestParam("directorId") String directorId,
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
        Director director = directorService.findById(Long.valueOf(directorId));
        if (director == null) {
            return Response.factoryResponse(StatusEnum.DIRECTOR_ERROR_3002.getCode(),StatusEnum.DIRECTOR_ERROR_3002.getData());
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
        movie.setLasts(CommUtil.stringToDate(lasts,"HH:mm:ss"));
        movie.setReleaseDate(CommUtil.stringToDate(releaseDate,"yyyy-MM-dd"));
        movie.setDirector(director);
        movie.setPerformers(performerList);
        //3 保存电影
        try {
            movieService.save(movie);
            return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
        }catch (Exception e) {
            return Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
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
