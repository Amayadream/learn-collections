package com.amayadream.mongodb.main;

import com.mongodb.Block;
import com.mongodb.client.*;
import org.bson.Document;
import org.junit.Test;

import java.util.function.Consumer;

/**
 * @author :  Amayadream
 * @date :  2016.11.04 14:38
 */
public class ClientCollectionTest extends AbstractTest {

    /**
     * 查询数据库
     */
    @Test
    public void mongoClientTest(){
        //1. 查询所有database
        ListDatabasesIterable<Document> databases = mongoClient.listDatabases();
        databases.forEach(new Consumer<Document>() {
            @Override
            public void accept(Document document) {
                System.out.println(document.toJson());
            }
        });
        //2. 查询特定database
        MongoDatabase database = mongoClient.getDatabase("learn-mongodb");
        System.out.println(database.getName());
        //3. 查询所有database名称
        MongoIterable<String> databaseNames = mongoClient.listDatabaseNames();
        databaseNames.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
    }

    /**
     * 查询集合
     */
    @Test
    public void mongoCollectionTest(){
        mongoDb = mongoClient.getDatabase("clouddriver");
        //1. 查询所有collection
        ListCollectionsIterable<Document> collections = mongoDb.listCollections();
        collections.forEach(new Consumer<Document>() {
            @Override
            public void accept(Document document) {
                System.out.println(document.toJson());
            }
        });
        //2. 查询特定collection
        MongoCollection<Document> collection = mongoDb.getCollection("test");
        System.out.println(collection.count());
        //3. 查询所有collection名称
        MongoIterable<String> collectionNames = mongoDb.listCollectionNames();
        collectionNames.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
    }

}
