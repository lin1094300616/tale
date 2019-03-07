package com.inchwisp.tale.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.inchwisp.tale.framework.configurer.Permission;
import com.inchwisp.tale.framework.entity.PermissionConstants;
import com.inchwisp.tale.framework.entity.Response;
import com.inchwisp.tale.framework.entity.StatusEnum;
import com.inchwisp.tale.framework.util.CommUtil;
import com.inchwisp.tale.framework.util.FileUtil;
import com.inchwisp.tale.system.model.Performer;
import com.inchwisp.tale.system.service.PerformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

/**
 * @ClassName: PerformerController
 * @Description: 演员模块控制器
 * @Author: MSI
 * @Date: 2019/1/31 10:44
 * @Vresion: 1.0.0
 **/
@RestController
public class PerformerController {

    @Autowired
    PerformerService performerService;

    /**
     * @Author MSI
     * @Description 添加演员
     * @Content: TODO
     * @Date 2019/1/31 16:05
     * @Param [name, sex, birthday, nationality, introduction, multipartFile]
     * @return com.inchwisp.tale.framework.entity.Response 
     **/       
    @PostMapping("/performer")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response add(@RequestParam("name") String name,
                        @RequestParam("sex") String sex,
                        @RequestParam("birthday") String birthday,
                        @RequestParam("nationality") String nationality,
                        @RequestParam("introduction") String introduction,
                        @RequestParam("image") MultipartFile multipartFile) {
        System.out.println("multipartFile = " + multipartFile.getOriginalFilename());
        //1.1 验证数据完整性，参数是否完整
        if (CommUtil.isNullString(name,sex,birthday,nationality,introduction)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //1.2 验证演员是否存在
        if(performerService.findByNameAndBirthday(name, Date.valueOf(birthday)) != null) {
            return Response.factoryResponse(StatusEnum.PERFORMER_ERROR_4001.getCode(),StatusEnum.PERFORMER_ERROR_4001.getData());
        }
        //2.封装数据
        Performer performer = new Performer();
        performer.setName(name);
        performer.setSex(sex);
        performer.setBirthday(Date.valueOf(birthday));
        performer.setNationality(nationality);
        performer.setIntroduction(introduction);
        //3.调用工具类，保存图片
        String fileName = FileUtil.fileUpload("performer",multipartFile);
        if (fileName == null) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9004.getCode(),StatusEnum.SYSTEM_ERROR_9004.getData());
        }
        //4.写入图片路径，保存演员信息
        performer.setImage(fileName);
        try {
            performerService.save(performer);
        }catch (Exception e) {
            return Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    /**
     * @Author MSI
     * @Description 删除演员
     * @Content: TODO
     * @Date 2019/1/31 16:49
     * @Param [id]
     * @return com.inchwisp.tale.framework.entity.Response
     **/
    @DeleteMapping("/performer/{id}")
    public Response delete(@PathVariable Long id) {
        //1.验证数据完整性
        if (CommUtil.isNullValue(id)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //2.查找演员
        Performer performer = performerService.findById(id);
        if (performer == null) {
            return Response.factoryResponse(StatusEnum.PERFORMER_ERROR_4002.getCode(),StatusEnum.PERFORMER_ERROR_4002.getData());
        }
        //3.删除失败的话报错
        try {
            performerService.delete(performer);
        }catch (Exception e){
            return Response.factoryResponse(StatusEnum.RET_DELETE_FAIL.getCode(),StatusEnum.RET_DELETE_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    /**
     * @Author MSI
     * @Description 修改演员
     * @Content: TODO
     * @Date 2019/1/31 16:49
     * @Param [data]
     * @return com.inchwisp.tale.framework.entity.Response
     **/
    @PutMapping("/performer")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response update(@RequestBody JSONObject data) {
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
        //2.2 验证演员是否存在
        Performer performer = performerService.findById(id);
        if (performer == null) {
            return Response.factoryResponse(StatusEnum.PERFORMER_ERROR_4002.getCode(),StatusEnum.PERFORMER_ERROR_4002.getData());
        }
        //3.修改演员信息
        performer.setName(name);
        performer.setSex(sex);
        performer.setBirthday(Date.valueOf(birthday));
        performer.setNationality(nationality);
        performer.setIntroduction(introduction);
        try {
            performerService.save(performer);
        }catch (Exception e) {
            return Response.factoryResponse(StatusEnum.RET_UPDATE_FAIL.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    /**
     * @Author MSI
     * @Description 查询演员详细详细
     * @Content: TODO
     * @Date 2019/1/31 16:50
     * @Param [id]
     * @return com.inchwisp.tale.framework.entity.Response
     **/
    @GetMapping("/performer/{id}")
    public Response detail(@PathVariable("id") Long id) {
        //1.验证数据完整性
        if (CommUtil.isNullValue(id)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //2.查询导演是否存在
        Performer performer = performerService.findById(id);
        if (performer == null) {
            return Response.factoryResponse(StatusEnum.PERFORMER_ERROR_4002.getCode(),StatusEnum.PERFORMER_ERROR_4002.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),performer);
    }

    /**
     * @Author MSI
     * @Description 查询演员列表
     * @Content: TODO
     * @Date 2019/1/31 17:03
     * @Param []
     * @return com.inchwisp.tale.framework.entity.Response
     **/
    @GetMapping("/performer")
    public Response list() {
        List<Performer> performerList = performerService.findAll();
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),performerList);
    }
}
