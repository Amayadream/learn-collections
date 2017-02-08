package com.amayadream.netty.learn.chapter1.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;

/**
 * @author :  Amayadream
 * @date :  2017.02.06 20:26
 */
public class HelloServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        //收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress() + " Say : " + message);

        //返回客户端消息
        ctx.writeAndFlush("Received you message !\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("RemoteAddress : " + ctx.channel().remoteAddress() + " active");

        ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service !\n");

        super.channelActive(ctx);
    }
}
