package com.amayadream.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 系统启动时初始化client
 * @author :  Amayadream
 * @date :  2016.11.03 16:05
 */
public class Application implements ApplicationListener {

    public static TransportClient client;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Settings settings = Settings.builder()
            .put("cluster.name", "es")
            .put("xpack.security.user", "elastic:changeme")
            .build();
        try {
            if(client == null){
                client = new PreBuiltXPackTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
