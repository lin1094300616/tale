package com.inchwisp.tale.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.inchwisp.tale.framework.configurer.Permission;
import com.inchwisp.tale.framework.entity.PermissionConstants;
import com.inchwisp.tale.framework.entity.Response;
import com.inchwisp.tale.framework.entity.StatusEnum;
import com.inchwisp.tale.framework.util.CommUtil;
import com.inchwisp.tale.framework.util.FileUtil;
import com.inchwisp.tale.system.model.Director;
import com.inchwisp.tale.system.model.Movie;
import com.inchwisp.tale.system.model.Performer;
import com.inchwisp.tale.system.service.DirectorService;
import com.inchwisp.tale.system.service.MovieService;
import com.inchwisp.tale.system.service.PerformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * @Author MSI
     * @Description 新增电影
     * @Content: TODO
     * @Date 2019/3/11 16:23
     * @Param [name, alias, region, language, tag, introduction, resource, multipartFile, lasts, releaseDate, directorId, performerId]
     * @return com.inchwisp.tale.framework.entity.Response 
     **/       
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
        //1.3 验证演员是否存在
        List<Performer> performerList = performerService.findByIdIn(CommUtil.splitString(performerId));
        if (performerList == null) {
            return Response.factoryResponse(StatusEnum.PERFORMER_ERROR_4003.getCode(),StatusEnum.PERFORMER_ERROR_4003.getData());
        }
        //1.4 验证导演是否存在
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
        //3 保存图片，获得图片存储路径
        String fileName = FileUtil.fileUpload("movie",multipartFile);
        if (CommUtil.isNullString(fileName)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9004.getCode(),StatusEnum.SYSTEM_ERROR_9004.getData());
        }
        movie.setImage(fileName);
        //4 保存电影
        try {
            movieService.save(movie);
            return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
        }catch (Exception e) {
            return Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
    }

    /**
     * @Author MSI
     * @Description 删除电影
     * @Content: TODO
     * @Date 2019/3/11 16:23
     * @Param [id]
     * @return com.inchwisp.tale.framework.entity.Response 
     **/       
    @DeleteMapping("/movie/{id}")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response delete(@PathVariable("id") Long id) {
        //1.根据ID查询电影
        Movie movie = movieService.findById(id);
        if (movie == null) {
            //1.1电影不存在则返回错误信息
            return Response.factoryResponse(StatusEnum.MOVIE_ERROR_2002.getCode(),StatusEnum.MOVIE_ERROR_2002.getData());
        }
        //2.电影存在，删除电影
        try {
            movieService.delete(movie);
        }catch (Exception e) {
            return Response.factoryResponse(StatusEnum.RET_DELETE_FAIL.getCode(),StatusEnum.RET_DELETE_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    /**
     * @Author MSI
     * @Description 修改电影
     * @Content: TODO
     * @Date 2019/3/11 16:23
     * @Param [data]
     * @return com.inchwisp.tale.framework.entity.Response 
     **/       
    @PutMapping("/movie")
    public Response update(@RequestBody JSONObject data) {
        //1. 接收数据
        Long id = data.getLong("id");
        String name = data.getString("name");
        String alias = data.getString("alias");
        String region = data.getString("region");
        String language = data.getString("language");
        String tag = data.getString("tag");
        String introduction = data.getString("introduction");
        String resource = data.getString("resource");
        String lasts = data.getString("lasts");
        String releaseDate = data.getString("releaseDate");
        String directorId = data.getString("directorId");
        String performerId = data.getString("performerId");

        //2.校验数据
        if (CommUtil.isNullString(name,alias,region,language,tag,introduction,resource,lasts,releaseDate,directorId,performerId)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //2.2 验证电影是否存在
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return Response.factoryResponse(StatusEnum.MOVIE_ERROR_2002.getCode(),StatusEnum.MOVIE_ERROR_2002.getData());
        }
        //2.3 验证演员是否存在
        List<Performer> performerList = performerService.findByIdIn(CommUtil.splitString(performerId));
        if (performerList == null) {
            return Response.factoryResponse(StatusEnum.PERFORMER_ERROR_4003.getCode(),StatusEnum.PERFORMER_ERROR_4003.getData());
        }
        //2.4 验证导演是否存在
        Director director = directorService.findById(Long.valueOf(directorId));
        if (director == null) {
            return Response.factoryResponse(StatusEnum.DIRECTOR_ERROR_3002.getCode(),StatusEnum.DIRECTOR_ERROR_3002.getData());
        }
        //3 修改电影信息
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
        //4 保存电影
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
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return Response.factoryResponse(StatusEnum.MOVIE_ERROR_2002.getCode(),StatusEnum.MOVIE_ERROR_2002.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),movie);
    }

    /**
     * @Author MSI
     * @Description 查询电影列表
     * @Content: TODO
     * @Date 2019/3/11 16:27
     * @Param []
     * @return com.inchwisp.tale.framework.entity.Response 
     **/       
    @GetMapping("/movie")
    public Response list() {
        List<Movie> movieList = movieService.findAll();
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),movieList);
    }
    
}
