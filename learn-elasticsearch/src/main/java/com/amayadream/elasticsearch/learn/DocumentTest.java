package com.amayadream.elasticsearch.learn;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 1. 基础文档操作
 *
 * @author :  Amayadream
 * @date :  2016.11.03 20:16
 */
public class DocumentTest extends AbstractTest {

    /**
     * 1.1 建立索引
     */
    @Test
    public void indexApiTest() throws IOException {
        //构建数据, 这里的数据格式只能为map或json字符串
        IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
                .get();
        System.out.println(response.getIndex());    //索引名称, 即prepareIndex中第一个参数
        System.out.println(response.getType());     //索引类型, 即prepareIndex中第二个参数
        System.out.println(response.getId());       //索引id,   即prepareIndex中第三个参数
        System.out.println(response.getVersion());  //版本,     如果是第一次添加索引为1
        System.out.println(response.status().getStatus());  //  状态, 200
        System.out.println(response.getResult().getLowercase());    //如果是created即为新建, updated即为更新
    }

    /**
     * 1.2 获取
     */
    @Test
    public void getApiTest() {
        GetResponse response = client.prepareGet("twitter", "tweet", "1").get();
        System.out.println(response.isExists());    //是否存在
        System.out.println(response.getSourceAsString());   //获取内容
        System.out.println(response.getVersion());  //获取版本号
    }

    /**
     * 1.3 删除索引
     */
    @Test
    public void deleteApiTest() {
        DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").get();
        System.out.println(response.getResult().getLowercase());    //deleted为删除, not_found为未找到
    }

    /**
     * 1.4 更新
     *
     * @throws IOException
     */
    @Test
    public void updateApiTest() throws IOException {
        UpdateResponse response = client.prepareUpdate("ttl", "doc", "1")
                .setDoc(jsonBuilder()
                        .startObject()
                        .field("gender", "male")
                        .endObject())
                .get();
        System.out.println(response.getVersion());
        System.out.println(response.getResult().getLowercase());
        System.out.println(response.getGetResult().isExists());
    }

    /**
     * 1.5 更新或者插入
     */
    @Test
    public void upsertApiTest() throws ExecutionException, InterruptedException, IOException {
        IndexRequest indexRequest = new IndexRequest("index", "type", "1")
                .source(jsonBuilder()
                        .startObject()
                        .field("name", "Joe Smith")
                        .field("gender", "male")
                        .endObject());
        UpdateRequest updateRequest = new UpdateRequest("index", "type", "1")
                .doc(jsonBuilder()
                        .startObject()
                        .field("gender", "males")
                        .endObject())
                .upsert(indexRequest);
        UpdateResponse response = client.update(updateRequest).get();
        System.out.println(response.getResult().getLowercase());    //created:创建, updated: 更新, noop: 空操作
    }

    /**
     * 1.6 复合获取
     */
    @Test
    public void multiGetApiTest() {
        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                .add("twitter", "tweet", "1")
                .add("index", "type", "1")
//                .add("twitter", "tweet", "2", "3", "4")
                .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response = itemResponse.getResponse();
            System.out.println(response.isExists());
            if (response.isExists()) {
                System.out.println(response.getSourceAsString());
            }
        }
    }

    /**
     * 1.7 复合操作, 支持建立和删除
     */
    @Test
    public void bulkApiTest() throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        // either use client#prepare, or use Requests# to directly build index/delete requests
        bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
        );
        bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post")
                        .endObject()
                )
        );
        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            System.out.println("有失败");
        }
    }

}
