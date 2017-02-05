package com.amayadream.ehcache.service;

import com.amayadream.ehcache.model.User;

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
