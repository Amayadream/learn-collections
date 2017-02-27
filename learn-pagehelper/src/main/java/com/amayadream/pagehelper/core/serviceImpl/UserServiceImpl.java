package com.amayadream.pagehelper.core.serviceImpl;

import com.amayadream.pagehelper.core.dao.IUserDao;
import com.amayadream.pagehelper.core.model.User;
import com.amayadream.pagehelper.core.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :  Amayadream
 * @date :  2017.02.27 22:25
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    @Override
    public List<User> list() {
        return userDao.list();
    }

    @Override
    public int save(User user) {
        return userDao.save(user);
    }

}
