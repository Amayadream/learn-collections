package com.amayadream.mongodb.main;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;

import java.util.Collections;

/**
 * @author :  Amayadream
 * @date :  2016.11.04 14:44
 */
public abstract class AbstractTest {

    protected static MongoClient mongoClient;
    protected static MongoDatabase mongoDb;
    protected static MongoCollection<Document> mongoCollection;

    @Before
    public void before(){
        mongoClient = new MongoClient(Collections.singletonList(new ServerAddress("localhost", 27017)));
        mongoDb = mongoClient.getDatabase("learn-mongodb");
        mongoCollection = mongoDb.getCollection("test");
    }

    @After
    public void after() {
        mongoClient.close();
    }


}
