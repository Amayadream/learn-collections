package com.amayadream.mongodb.main;

import com.mongodb.client.*;
import org.bson.Document;
import org.junit.Test;

import java.util.function.Consumer;

/**
 * 基础的
 * @author :  Amayadream
 * @date :  2016.11.14 21:22
 */
public class BasicTest extends AbstractTest {

    /**
     * 查询数据库
     */
    @Test
    public void mongoClientTest(){
        //1. 查询所有database
        ListDatabasesIterable<Document> databases = client.listDatabases();
        databases.forEach(new Consumer<Document>() {
            @Override
            public void accept(Document document) {
                System.out.println(document.toJson());
            }
        });
        //2. 查询特定database
        MongoDatabase database = client.getDatabase("learn-mongodb");
        System.out.println(database.getName());
        //3. 查询所有database名称
        MongoIterable<String> databaseNames = client.listDatabaseNames();
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
        db = client.getDatabase("clouddriver");
        //1. 查询所有collection
        ListCollectionsIterable<Document> collections = db.listCollections();
        collections.forEach(new Consumer<Document>() {
            @Override
            public void accept(Document document) {
                System.out.println(document.toJson());
            }
        });
        //2. 查询特定collection
        MongoCollection<Document> collection = db.getCollection("test");
        System.out.println(collection.count());
        //3. 查询所有collection名称
        MongoIterable<String> collectionNames = db.listCollectionNames();
        collectionNames.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
    }

}
