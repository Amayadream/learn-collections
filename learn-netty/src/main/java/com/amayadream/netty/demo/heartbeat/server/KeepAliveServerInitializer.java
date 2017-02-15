package com.amayadream.netty.demo.heartbeat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


/**
 * @author :  Amayadream
 * @date :  2017.02.15 21:40
 */
public class KeepAliveServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //以("\n")为结尾分割的解码器
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        /**
         * 该类的第一个参数是指定读操作空闲秒数，第二个参数是指定写操作的空闲秒数，第三个参数是指定读写空闲秒数
         * 当有操作操作超出指定空闲秒数时，便会触发UserEventTriggered事件
         * 所以我们只需要在自己的handler中截获该事件，然后发起相应的操作即可（比如说发起心跳操作）。
         */
        pipeline.addLast("ping", new IdleStateHandler(5, 5, 10, TimeUnit.SECONDS));

        //自己的逻辑Handler
        pipeline.addLast("handler", new KeepAliveServerHandler());
    }

}
