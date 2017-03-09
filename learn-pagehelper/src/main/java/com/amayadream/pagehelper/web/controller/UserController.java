package com.amayadream.pagehelper.web.controller;

import com.alibaba.fastjson.JSON;
import com.amayadream.pagehelper.core.model.User;
import com.amayadream.pagehelper.core.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :  Amayadream
 * @date :  2017.02.27 22:26
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String list(@RequestParam int offset, @RequestParam int limit){
        PageHelper.startPage(offset, limit);
        List<User> list = userService.list();
        PageInfo<User> page = new PageInfo<User>(list);
        System.out.println("总条数: " + page.getTotal());
        System.out.println("总页数: " + page.getPages());
        return JSON.toJSONString(list);
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String add(String userId, String realName){
        if (userService.save(new User(userId, realName)) == 1)
            return "success";
        return "error";
    }

}
