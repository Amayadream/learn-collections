package com.amayadream.redis.controller;

import com.alibaba.fastjson.JSON;
import com.amayadream.redis.model.User;
import com.amayadream.redis.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author :  Amayadream
 * @date :  2016.07.30 10:23
 */
@Controller
@RequestMapping("simple")
public class SimpleController {

    @Resource
    private IUserService userService;

    @RequestMapping(value = "select/{userId}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String select(@PathVariable String userId){
        User user = userService.selectByUserId(userId);
        return JSON.toJSONString(user);
    }

    @RequestMapping(value = "insert/{userId}/{password}", produces = "application/json;charset=utf-8")
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

    @RequestMapping(value = "delete/{userId}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String delete(@PathVariable String userId){
        userService.delete(userId);
        return "success";
    }


}
