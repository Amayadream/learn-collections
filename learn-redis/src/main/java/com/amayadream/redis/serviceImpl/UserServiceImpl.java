package com.amayadream.redis.serviceImpl;

import com.amayadream.redis.model.User;
import com.amayadream.redis.service.IUserService;
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

    @Override
    @Cacheable(key = "#userId", value = "userCache")
    public User selectByUserId(String userId) {
        System.out.println("[selectByUserId] get from db...");
        User user = mongoTemplate.findById(userId, User.class);
        return user != null ? user : null;
    }

    @Override
    @CachePut(key = "#user.userId", value = "userCache")
    public User insert(User user) {
        System.out.println("[insert] get from db...");
        mongoTemplate.insert(user);
        return mongoTemplate.findById(user.getUserId(), User.class);
    }

    @Override
    @CachePut(key = "#user.userId", value = "userCache")
    public User update(User user) {
        System.out.println("[update] get from db...");
        User user1 = mongoTemplate.findById(user.getUserId(), User.class);
        if (user1 != null) {
            user1.setPassword(user.getPassword());
            mongoTemplate.save(user1);
        }
        return mongoTemplate.findById(user.getUserId(), User.class);
    }

    @Override
    @CachePut(key = "#userId", value = "userCache")
    public void delete(String userId) {
        System.out.println("[delete] get from db...");
        mongoTemplate.remove(Query.query(Criteria.where("userId").is(userId)), User.class);
    }

}
