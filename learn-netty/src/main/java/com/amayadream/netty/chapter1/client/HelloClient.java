package com.amayadream.netty.chapter1.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author :  Amayadream
 * @date :  2017.02.06 20:32
 */
public class HelloClient {

    public static String host = "127.0.0.1";
    public static int port = 7878;

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HelloClientInitializer());

            //连接服务端
            Channel ch = b.connect(host, port).sync().channel();

            //控制条输入
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for (;;) {
                String line = br.readLine();
                if (line == null)
                    continue;
                //想服务端发送的控制台输入文本, 并用\r\n结尾
                //之所以用\r\n结尾 是因为我们在handler中添加了 DelimiterBasedFrameDecoder 帧解码。
                //这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
                ch.writeAndFlush(line + "\r\n");
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
