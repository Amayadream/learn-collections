package com.amayadream.elasticsearch.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.amayadream.elasticsearch.model.User;
import com.amayadream.elasticsearch.service.IUserService;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.amayadream.elasticsearch.Application.client;

/**
 * @author :  Amayadream
 * @date :  2016.11.03 16:12
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private MongoTemplate template;

    @Override
    public List<User> list() {
        return template.findAll(User.class);
    }

    @Override
    public User selectByPrimaryKey(String userId) {
        return template.findById(userId, User.class);
    }

    @Override
    public void insert(User user) throws Exception {
        template.insert(user);
        IndexResponse response = client.prepareIndex(user.getUserId().toLowerCase(), User.USER)
                .setSource(JSON.toJSONString(user))
                .get();
    }

    @Override
    public void update(User user) throws Exception {
        template.save(user);
        IndexRequest indexRequest = new IndexRequest(user.getUserId().toLowerCase(), User.USER, user.getUserId())
                .source(user);
        UpdateRequest updateRequest = new UpdateRequest(user.getUserId().toLowerCase(), User.USER, user.getUserId())
                .doc(user)
                .upsert(indexRequest);
        UpdateResponse response = client.update(updateRequest).get();
    }

    @Override
    public void delete(String userId) throws Exception {
        template.remove(selectByPrimaryKey(userId));
        DeleteResponse response = client.prepareDelete().setIndex(userId.toLowerCase()).setType(User.USER).get();
    }
}
