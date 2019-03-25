package com.inchwisp.tale.framework.util;

import com.inchwisp.tale.framework.entity.ConstantsEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName: FileUtil
 * @Description: 文件保存工具类
 * @Author: MSI
 * @Date: 2019/1/21 17:03
 * @Vresion: 1.0.0
 **/
public class FileUtil {

    /**
     * @Author MSI
     * @Description 文件存储方法，根据主路径+资源类型存储文件
     * @Content: TODO
     * @Date 2019/1/23 11:10
     * @Param [type, multipartFile]
     * @return java.lang.String
     **/
    public static String fileUpload(String type,MultipartFile multipartFile) {
        //1.判断文件是否存在
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        //2.获得存放路径：主路径+类型（导演、演员、电影）
        String path = ConstantsEnum.UPLOAD + type + "\\"; //如 D:\IDEAWord\director\
        System.out.println("path = " + path);
        //3.判断文件是否为jpg、jpeg、png、bmp
        String fileName = multipartFile.getOriginalFilename();
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        System.out.println("fileName = " + fileName);
        if (!isImageFile(fileName)) {
            return null;
        }
        //4.生成新文件名，UUID+文件名
        String newFileName = UUID.randomUUID().toString().replace("-","") + fileName;
        file = new File(path + newFileName);

        System.out.println("newFileName = " + newFileName);
        //5.try 保存文件
        try {
            //6.保存成功，返回文件路径
            multipartFile.transferTo(file);
            return file.toString();
        } catch (IOException e) {
            System.out.println("文件生成失败");
            e.printStackTrace();
        }
        //7.保存失败，返回null
        return null;
    }

    /**
     * @Author MSI
     * @Description 验证文件是否是图片
     * @Content: TODO
     * @Date 2019/1/23 14:01
     * @Param [fileName]
     * @return java.lang.Boolean 
     **/       
    public static Boolean isImageFile(String fileName) {
        String[] img_type = new String[]{".jpg", ".jpeg", ".png", ".bmp"};
        if (fileName == null) {
            return false;
        }
        fileName = fileName.toLowerCase();
        for (String type : img_type) {
            if (fileName.endsWith(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Author MSI 递归删除目录下的所有文件及子目录下所有文件
     * @Description
     * @Content: TODO
     * @Date 2019/1/25 9:16
     * @Param [dir]  dir 将要删除的文件目录
     * @return boolean  是否删除成功
     **/
    public static boolean deleteDir(File dir) {
        /**判断是否是目录，如果不是目录，则直接删除**/
        if (dir.isDirectory()) {
            /**获得文件夹下面的子目录**/
            String[] children = dir.list();
            //递归删除目录中的子目录
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

}
