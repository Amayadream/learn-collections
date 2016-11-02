package com.amayadream.rpc.server;

import com.amayadream.rpc.server.service.HelloServiceStart;
import com.amayadream.rpc.server.zookeeper.ZookeeperService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.io.IOException;

/**
 * @author :  Amayadream
 * @date :  2016.11.01 17:07
 */
public class Application implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        System.out.println("=================== onApplicationEvent ========================");
        startSayHelloRpc();
        registerHelloRPC();
    }

    private void registerHelloRPC() {
        ZookeeperService reg = new ZookeeperService();
        try {
            reg.register();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startSayHelloRpc(){
        try {
            new Thread(new HelloServiceStart()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
