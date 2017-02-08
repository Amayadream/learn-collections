package com.amayadream.netty.demo.webpush;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

/**
 * @author :  Amayadream
 * @date :  2017.02.08 20:04
 */
public class PushEventListener implements DataListener<MessageObject> {

    SocketIOServer server;

    @Override
    public void onData(SocketIOClient client, MessageObject data, AckRequest req) throws Exception {
        this.server.getBroadcastOperations().sendEvent("pushEvent", data);
    }

    public void setServer(SocketIOServer server) {
        this.server = server;
    }
}
