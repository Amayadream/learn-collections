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

    protected static MongoClient client;
    protected static MongoDatabase db;
    protected static MongoCollection<Document> collection;

    @Before
    public void before(){
        client = new MongoClient(Collections.singletonList(new ServerAddress("localhost", 27017)));
        db = client.getDatabase("learn-mongodb");
        collection = db.getCollection("test");
    }

    @After
    public void after() {
        client.close();
    }


}
