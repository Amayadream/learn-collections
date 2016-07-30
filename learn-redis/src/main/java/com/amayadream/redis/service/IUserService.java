package com.amayadream.redis.service;

import com.amayadream.redis.model.User;

import java.util.List;

/**
 * @author :  Amayadream
 * @date :  2016.07.30 10:37
 */
public interface IUserService {

    User selectByUserId(String userId);

    User insert(User user);

    User update(User user);

    void delete(String userId);

}
