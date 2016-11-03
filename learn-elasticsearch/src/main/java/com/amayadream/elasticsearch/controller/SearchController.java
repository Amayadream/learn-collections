package com.amayadream.elasticsearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author :  Amayadream
 * @date :  2016.11.03 16:28
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController {

    @RequestMapping(value = "/list")
    public String list(){
        return null;
    }


}
