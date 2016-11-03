package com.amayadream.elasticsearch.controller;

import com.amayadream.elasticsearch.model.User;
import com.amayadream.elasticsearch.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author :  Amayadream
 * @date :  2016.11.03 16:13
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(User user) {
        try {
            userService.insert(user);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(User user) {
        try {
            userService.update(user);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(String userId) {
        try {
            userService.delete(userId);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
