package com.amayadream.httpclient.main;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * @author :  Amayadream
 * @date :  2016.11.29 22:31
 */
public class HttpGetTest {

    @Test
    public void httpGetTest() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://www.baidu.com");
        CloseableHttpResponse response = client.execute(get);
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                long len = entity.getContentLength();
                if (len != -1 && len < 2048) {
                    System.out.println(EntityUtils.toString(entity));
                }else{
                    System.out.println("too long...");
                }
            }
            System.out.println(response.getStatusLine().getStatusCode());
        }finally {
            response.close();
        }
    }

}
