package com.amayadream.mongodb.main;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Projections.*;

/**
 * @author :  Amayadream
 * @date :  2016.11.04 15:10
 */
public class CurdTest extends AbstractTest {

    /**
     * 插入操作
     */
    @Test
    public void insertTest(){
        //1. 单条插入
        Document document = new Document()
                .append("name", "Amayadream")
                .append("like", "coding")
//                .append("_id", "123")
                ;
        mongoCollection.insertOne(document);
        System.out.println(mongoCollection.count());

        //2. 多条插入
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < 100; i++) {
            documents.add(new Document("i", i));
        }
        mongoCollection.insertMany(documents);
        System.out.println(mongoCollection.count());
    }

    @Test
    public void queryTest(){
        //1. 查询全部
        FindIterable<Document> documents = mongoCollection.find();
        documents.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
        //2. 条件查询
        FindIterable<Document> document = mongoCollection.find(eq("name", "Amayadream"));
        document.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
        //3. 单条查询
        Document doc = mongoCollection.find(eq("name", "Amayadreams")).first();
        //4. 组合查询
        FindIterable<Document> documents1 = mongoCollection.find(and(gt("i", 50), lte("i", 60)));
        documents1.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
        //5. 排序
        FindIterable<Document> docs = mongoCollection.find(exists("i")).sort(descending("i"));
        docs.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
        //6. 选择字段

    }




}
