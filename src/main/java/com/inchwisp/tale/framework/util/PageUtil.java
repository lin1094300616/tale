package com.inchwisp.tale.framework.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;

/**
 * @ClassName: PageUtil
 * @Description: 分页工具类
 * @Author: MSI
 * @Date: 2019/1/7 15:31
 * @Vresion: 1.0.0
 **/
public class PageUtil {

    /**
     * @Author MSI
     * @Description 封装分页信息
     * @Date 2019/1/7 15:51
     * @Param [page]
     * @return com.alibaba.fastjson.JSONObject 
     **/       
    public static JSONObject pageInfo(Page page) {
        JSONObject result = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalElements",page.getTotalElements()); //总条目数
        jsonObject.put("totalPages",page.getTotalPages()); //总页数
        jsonObject.put("size",page.getSize()); //本页条数
        jsonObject.put("page",page.getNumber()); //页码
        jsonObject.put("isLast",page.isLast()); //是否最后一页
        jsonObject.put("isFirst",page.isFirst()); //是否第一页

        result.put("pageInfo",jsonObject); //写入分页信息
        result.put("data",page.getContent()); //写入内容
        return result;
    }
}
