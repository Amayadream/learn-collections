package com.amayadream.rpc.client;

import com.amayadream.rpc.client.util.WatchSayHello;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.io.IOException;

/**
 * @author :  Amayadream
 * @date :  2016.11.01 17:39
 */
public class Application implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        WatchSayHello watch = new WatchSayHello();
        try {
            watch.watch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
