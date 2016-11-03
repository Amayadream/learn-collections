package com.amayadream.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.get.GetResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.amayadream.elasticsearch.utils.ElasticsearchService.client;

/**
 * @author :  Amayadream
 * @date :  2016.11.03 16:28
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController {

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(){
        GetResponse response = client.prepareGet().get();
        return JSON.toJSONString(response.getSource());
    }



}
