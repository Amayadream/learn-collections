package com.amayadream.pagehelper.core.service;

import com.amayadream.pagehelper.core.model.User;

import java.util.List;

/**
 * @author :  Amayadream
 * @date :  2017.02.27 22:25
 */
public interface IUserService {

    List<User> list();

    int save(User user);

}
