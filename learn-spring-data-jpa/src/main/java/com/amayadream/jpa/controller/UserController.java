package com.amayadream.jpa.controller;

import com.amayadream.jpa.dao.UserDao;
import com.amayadream.jpa.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author :  Amayadream
 * @date :  2017.03.14 20:30
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserDao userDao;

    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(int userId, String profile){
        User u = new User(userId, profile);
        userDao.save(u);
        return "success";
    }

}
