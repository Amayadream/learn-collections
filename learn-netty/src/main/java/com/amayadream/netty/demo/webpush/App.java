package com.amayadream.netty.demo.webpush;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

/**
 * @author :  Amayadream
 * @date :  2017.02.08 20:09
 */
public class App {

    public static void main(String[] args) throws InterruptedException {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        SocketIOServer server = new SocketIOServer(config);
        PushEventListener listener = new PushEventListener();
        listener.setServer(server);
        //pushEvent为事件名称
        server.addEventListener("pushEvent", MessageObject.class, listener);
        //启动服务
        server.start();
        Thread.sleep(Integer.MAX_VALUE);
        server.stop();
    }

}
