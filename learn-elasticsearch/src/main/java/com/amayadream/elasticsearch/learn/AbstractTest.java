package com.amayadream.elasticsearch.learn;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.junit.After;
import org.junit.Before;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author :  Amayadream
 * @date :  2016.11.03 20:15
 */
public abstract class AbstractTest {

    protected static TransportClient client;

    @Before
    public void before() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "es")
                .put("xpack.security.user", "elastic:changeme")
                .build();
        client = new PreBuiltXPackTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        System.out.println("connect success");
    }

    @After
    public void after(){
        client.close();
    }

}
