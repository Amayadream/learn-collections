package com.amayadream.mongodb.sync;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.descending;

/**
 * @author :  Amayadream
 * @date :  2016.11.04 15:10
 */
public class CurdTest extends AbstractTest {

    /**
     * 单条插入
     */
    @Test
    public void singleInsert(){
        Document doc = new Document("userId", "hello")
                .append("like", "coding");
        System.out.println("单条插入前, 集合记录数为: " + collection.count());
        collection.insertOne(doc);
        System.out.println("单条插入后, 集合记录数为: " + collection.count());
    }

    /**
     * 多条插入
     */
    @Test
    public void mutiInsert(){
        List<Document> docs = new ArrayList<Document>();
        for (int i = 0; i < 100; i ++) {
            docs.add(new Document("i", i));
        }
        System.out.println("多条插入前, 集合记录数为: " + collection.count());
        collection.insertMany(docs);
        System.out.println("多条插入后, 集合记录数为: " + collection.count());
    }

    /**
     * 更新操作
     */
    @Test
    public void update() {
        UpdateResult r = collection.updateOne(Filters.eq("userId", "hello"), Updates.set("userId", "Amayadream"));
        System.out.println(r.getModifiedCount());
        UpdateResult rs = collection.updateOne(Filters.eq("userId", "Amayadream"), Updates.combine(Arrays.asList(Updates.set("userId", "asd"), Updates.set("like", "sleep"))));
        System.out.println(rs.getModifiedCount());
        UpdateResult rr = collection.updateMany(Filters.gt("i", 50), Updates.set("cooking", true));
        System.out.println(rr.getModifiedCount());
    }

    /**
     * 删除操作
     */
    @Test
    public void singleDelete(){
        DeleteResult result = collection.deleteOne(Filters.lt("i", 20));
        System.out.println("单条删除, 删除数量: " + result.getDeletedCount());
        DeleteResult r = collection.deleteMany(Filters.lt("i", 20));
        System.out.println("多条删除, 删除数量: " + r.getDeletedCount());
    }

    /**
     * 查询并替换
     */
    @Test
    public void findAndReplace(){
        Document doc = new Document("userId", "Amayadream");
        collection.insertOne(doc);
        doc.append("userId", "asd").append("password", "123");
        collection.replaceOne(Filters.eq("_id", doc.get("_id")), doc);
        List<Document> docs = collection.find().into(new ArrayList<Document>());
        for (Document document : docs) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void pagination(){
        long i = collection.count();
        System.out.println("总数: " + i);
        collection.find().limit(20).skip(10).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
    }

    @Test
    public void queryTest(){
        //1. 查询全部
        FindIterable<Document> documents = collection.find();
        documents.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
        //2. 条件查询
        FindIterable<Document> document = collection.find(eq("name", "Amayadream"));
        document.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
        //3. 单条查询
        Document doc = collection.find(eq("name", "Amayadreams")).first();
        //4. 组合查询
        FindIterable<Document> documents1 = collection.find(and(gt("i", 50), lte("i", 60)));
        documents1.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
        //5. 排序
        FindIterable<Document> docs = collection.find(exists("i")).sort(descending("i"));
        docs.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
    }




}
