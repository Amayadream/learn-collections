package com.amayadream.netty.chapter2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * @author :  Amayadream
 * @date :  2017.02.06 21:53
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //1. 默默地丢弃收到的数据
        //((ByteBuf) msg).release();

//        ByteBuf in = (ByteBuf) msg;
//        try {
//            System.out.println(in.toString(CharsetUtil.UTF_8)); //2. 打印收到的消息
//
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }

        ctx.write(msg); //3. 返回消息
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
