package com.amayadream.ehcache.controller;

import com.alibaba.fastjson.JSON;
import com.amayadream.ehcache.model.User;
import com.amayadream.ehcache.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author :  Amayadream
 * @date :  2016.07.30 10:23
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/{userId}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String select(@PathVariable String userId){
        User user = userService.selectByUserId(userId);
        return JSON.toJSONString(user);
    }

    @RequestMapping(value = "add/{userId}/{password}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String insert(@PathVariable String userId, @PathVariable String password){
        userService.insert(new User(userId, password));
        return "success";
    }

    @RequestMapping(value = "update/{userId}/{password}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String update(@PathVariable String userId, @PathVariable String password){
        userService.update(new User(userId, password));
        return "success";
    }

    @RequestMapping(value = "remove/{userId}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String delete(@PathVariable String userId){
        userService.delete(userId);
        return "success";
    }


}
