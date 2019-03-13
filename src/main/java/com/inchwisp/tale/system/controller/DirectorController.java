package com.inchwisp.tale.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.inchwisp.tale.framework.configurer.Permission;
import com.inchwisp.tale.framework.entity.ConstantsEnum;
import com.inchwisp.tale.framework.entity.PermissionConstants;
import com.inchwisp.tale.framework.entity.Response;
import com.inchwisp.tale.framework.entity.StatusEnum;
import com.inchwisp.tale.framework.util.CommUtil;
import com.inchwisp.tale.framework.util.FileUtil;
import com.inchwisp.tale.framework.util.PageUtil;
import com.inchwisp.tale.system.model.Director;
import com.inchwisp.tale.system.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

/**
 * @ClassName: DirectorController
 * @Description: 导演模块控制器
 * @Author: MSI
 * @Date: 2019/1/19 15:17
 * @Vresion: 1.0.0
 **/
@RestController
public class DirectorController {

    @Autowired
    DirectorService directorService;

    /**
     * @Author MSI
     * @Description 新增导演
     * @Content: TODO
     * @Date 2019/1/19 16:14
     * @Param [data, multipartFile]
     * @return com.inchwisp.tale.framework.entity.Response
     **/
    @PostMapping("/director")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response saveDirector(@RequestParam("name") String name,
                                 @RequestParam("sex") String sex,
                                 @RequestParam("birthday") String birthday,
                                 @RequestParam("nationality") String nationality,
                                 @RequestParam("introduction") String introduction,
                                 @RequestParam("image")MultipartFile multipartFile) {
        System.out.println("multipartFile = " + multipartFile.getOriginalFilename());
        //1.1 验证数据完整性，参数是否完整
        if (CommUtil.isNullString(name,sex,birthday,nationality,introduction)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //1.2 验证导演是否存在
        if(directorService.findByNameAndBirthday(name,Date.valueOf(birthday)) != null) {
            return Response.factoryResponse(StatusEnum.DIRECTOR_ERROR_3001.getCode(),StatusEnum.DIRECTOR_ERROR_3001.getData());
        }
        //2.封装数据
        Director director = new Director();
        director.setName(name);
        director.setSex(sex);
        director.setBirthday(Date.valueOf(birthday));
        director.setNationality(nationality);
        director.setIntroduction(introduction);
        //3.调用工具类，保存图片
        String fileName = FileUtil.fileUpload("director",multipartFile);
        if (CommUtil.isNullString(fileName)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9004.getCode(),StatusEnum.SYSTEM_ERROR_9004.getData());
        }
        System.out.println("fileName = " + fileName);
        director.setImage(fileName);
        //4.写入图片路径，保存导演信息
        try {
            directorService.saveDirector(director);
        }catch (Exception e) {
            return Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    /**
     * @Author MSI
     * @Description 删除导演
     * @Content: TODO
     * @Date 2019/1/21 9:33
     * @Param [id]
     * @return com.inchwisp.tale.framework.entity.Response 
     **/       
    @DeleteMapping("/director/{id}")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response deleteDirector(@PathVariable Long id) {
        //1.验证数据完整性
        if (CommUtil.isNullValue(id)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //2.查询导演是否存在
        Director director = directorService.findById(id);
        if (director == null) {
            return Response.factoryResponse(StatusEnum.DIRECTOR_ERROR_3002.getCode(),StatusEnum.DIRECTOR_ERROR_3002.getData());
        }
        //3.删除，出现异常时返回错误信息
        try {
            directorService.deleteDirector(director);
        }catch (Exception e){
            return Response.factoryResponse(StatusEnum.RET_DELETE_FAIL.getCode(),StatusEnum.RET_DELETE_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    /**
     * @Author MSI
     * @Description 修改导演
     * @Content: TODO
     * @Date 2019/1/21 9:33
     * @Param [data]
     * @return com.inchwisp.tale.framework.entity.Response 
     **/       
    @PutMapping("/director")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response updateDirector(@RequestBody JSONObject data) {
        //1.接收数据
        Long id = data.getLong("id");
        String name = data.getString("name");
        String sex = data.getString("sex");
        String birthday = data.getString("birthday");
        String nationality = data.getString("nationality");
        String introduction = data.getString("introduction");
        //2.1 验证数据完整性
        if (CommUtil.isNullValue(id) || CommUtil.isNullString(name,sex,nationality)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //2.2 验证导演是否存在
        Director director = directorService.findById(id);
        if (director == null) {
            return Response.factoryResponse(StatusEnum.DIRECTOR_ERROR_3002.getCode(),StatusEnum.DIRECTOR_ERROR_3002.getData());
        }
        //3.修改导演信息
        director.setName(name);
        director.setSex(sex);
        director.setBirthday(Date.valueOf(birthday));
        director.setNationality(nationality);
        director.setIntroduction(introduction);
        try {
            directorService.saveDirector(director);
        }catch (Exception e) {
            return Response.factoryResponse(StatusEnum.RET_UPDATE_FAIL.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    /**
     * @Author MSI
     * @Description 查询导演详细信息
     * @Content: TODO
     * @Date 2019/1/21 9:36
     * @Param [id]
     * @return com.inchwisp.tale.framework.entity.Response 
     **/       
    @GetMapping("/director/{id}")
    public Response detailDirector(@PathVariable("id") Long id) {
        //1.验证数据完整性
        if (CommUtil.isNullValue(id)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //2.查询导演是否存在
        Director director = directorService.findById(id);
        if (director == null) {
            return Response.factoryResponse(StatusEnum.DIRECTOR_ERROR_3002.getCode(),StatusEnum.DIRECTOR_ERROR_3002.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),director);
    }

    /**
     * @Author MSI
     * @Description 查询导演列表
     * @Content: TODO
     * @Date 2019/1/23 11:35
     * @Param []
     * @return com.inchwisp.tale.framework.entity.Response 
     **/       
    @GetMapping("/director")
    public Response listDirector() {
        List<Director> directorList = directorService.findAll();
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),directorList);
    }

    /**
     * @Author MSI
     * @Description 按姓名搜索导演
     * @Content: TODO
     * @Date 2019/1/23 13:45
     * @Param [page, size, name]
     * @return com.inchwisp.tale.framework.entity.Response
     **/
    @GetMapping("/director/list")
    public Response searchDirector(@RequestParam(value = "page") Integer page,
                                   @RequestParam(value = "size") Integer size,
                                   @RequestParam(value = "name") String name) {
        page = CommUtil.isNullValue(page) ? ConstantsEnum.PAGE_DEFT : page;
        size = CommUtil.isNullValue(size) ? ConstantsEnum.SIZE_DEFT : size;
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Director> directorPage = directorService.pageDirector(name,pageable);
        JSONObject data = PageUtil.pageInfo(directorPage);
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),data);
    }
}
