package com.amayadream.pagehelper.core.dao;

import com.amayadream.pagehelper.core.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :  Amayadream
 * @date :  2017.02.27 22:13
 */
@Service
public interface IUserDao {

    List<User> list();

    int save(User user);

}
