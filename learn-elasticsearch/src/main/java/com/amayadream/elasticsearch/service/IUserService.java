package com.amayadream.elasticsearch.service;

import com.amayadream.elasticsearch.model.User;

import java.util.List;

/**
 * @author :  Amayadream
 * @date :  2016.11.03 16:12
 */
public interface IUserService {

    List<User> list();

    User selectByPrimaryKey(String userId);

    void insert(User user) throws Exception;

    void update(User user) throws Exception;

    void delete(String userId) throws Exception;

}
