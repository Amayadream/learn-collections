package com.amayadream.rpc.server;

import com.amayadream.rpc.server.register.RegisterHelloRpc;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.io.IOException;

/**
 * @author :  Amayadream
 * @date :  2016.11.01 17:07
 */
public class StartHelloService implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        startSayHelloRpc();
        registerHelloRPC();
    }

    private void registerHelloRPC() {
        RegisterHelloRpc reg = new RegisterHelloRpc();
        try {
            reg.register();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startSayHelloRpc(){
        try {
            new Thread(new SayHello()).start();;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
