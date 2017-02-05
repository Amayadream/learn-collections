package com.amayadream.ehcache.serviceImpl;

import com.amayadream.ehcache.model.User;
import com.amayadream.ehcache.service.IUserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author :  Amayadream
 * @date :  2016.07.30 10:38
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 根据用户名查询
     * 这里使用Cacheable注解
     * 当调用这个方法时,首先会从缓存库中查找,如果找到则返回缓存库中的,如果没有找到就执行查询,适合用于查询方法
     * @param userId
     * @return
     */
    @Override
    @Cacheable(value = "userCache", key = "#userId", condition = "#userId != 'admin'")
    public User selectByUserId(String userId) {
        System.out.println("[selectByUserId] get from db...");
        User user = mongoTemplate.findById(userId, User.class);
        return user != null ? user : null;
    }

    /**
     * CachePut注解,每次都会真实的执行请求,并更新缓存,适合用在更新和添加方法上
     * @param user
     * @return
     */
    @Override
    @CachePut(value = "userCache", key = "#user.userId")
    public User insert(User user) {
        System.out.println("[insert] get from db...");
        mongoTemplate.insert(user);
        return mongoTemplate.findById(user.getUserId(), User.class);
    }

    /**
     * CachePut注解,每次都会真实的执行请求,并更新缓存,适合用在更新和添加方法上
     * @param user
     * @return
     */
    @Override
    @CachePut(value = "userCache", key = "#user.userId")
    public User update(User user) {
        System.out.println("[update] get from db...");
        User user1 = mongoTemplate.findById(user.getUserId(), User.class);
        if (user1 != null) {
            user1.setPassword(user.getPassword());
            mongoTemplate.save(user1);
        }
        return mongoTemplate.findById(user.getUserId(), User.class);
    }

    /**
     * CacheEvict注解,删除缓存,allEntries为true时删除所有缓存
     * @param userId
     */
    @Override
    @CacheEvict(value = "userCache", key = "#userId", allEntries = false)
    public void delete(String userId) {
        System.out.println("[delete] get from db...");
        mongoTemplate.remove(Query.query(Criteria.where("userId").is(userId)), User.class);
    }

}
