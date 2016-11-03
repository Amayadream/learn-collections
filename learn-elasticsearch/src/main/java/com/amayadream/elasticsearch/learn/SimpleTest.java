package com.amayadream.elasticsearch.learn;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author :  Amayadream
 * @date :  2016.11.02 20:29
 */
public class SimpleTest {




    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder()
                .put("cluster.name", "es")
                .put("xpack.security.user", "elastic:changeme")
                .build();

        TransportClient client = new PreBuiltXPackTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        System.out.println("connect success");

        //1.新建索引
//        IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
//                .setSource(jsonBuilder()
//                        .startObject()
//                        .field("user", "kimchy")
//                        .field("postDate", new Date())
//                        .field("message", "trying out Elasticsearch")
//                        .endObject()
//                )
//                .get();
//        System.out.println(response.getIndex());    //索引名称, twitter
//        System.out.println(response.getId());       //索引id  , 1
//        System.out.println(response.getType());     //索引类型, tweet
//        System.out.println(response.getVersion());  //版本, 2
//        System.out.println(response.status().getStatus());  //状态, 200

        //2.获取
        try {
            GetResponse response = client.prepareGet("twitter", "tweet", "1").get();
            System.out.println(response.getSource());
            System.out.println(response.getSourceAsString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        //3.删除
//        DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").setVersion(2).get();
//        System.out.println(response.getResult().getLowercase());


        // 4.更新
//        IndexRequest indexRequest = new IndexRequest("twitter", "tweet", "1")
//                .source(jsonBuilder()
//                        .startObject()
//                        .field("name", "Joe Smith")
//                        .field("gender", "male")
//                        .endObject());
//        UpdateRequest updateRequest = new UpdateRequest("twitter", "tweet", "1")
//                .doc(jsonBuilder()
//                        .startObject()
//                        .field("gender", "male")
//                        .endObject())
//                .upsert(indexRequest);
//        UpdateResponse response = client.update(updateRequest).get();
//        System.out.println(response.getResult().getLowercase());



    }


}
