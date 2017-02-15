package com.amayadream.netty.demo.heartbeat.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * @author :  Amayadream
 * @date :  2017.02.15 21:40
 */
public class KeepAliveServerHandler extends SimpleChannelInboundHandler<String>{

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
            .unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
                    CharsetUtil.UTF_8));  // 1

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String str) throws Exception {
        System.out.println("READ0: " + str);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String type = "";

            if (event.state().equals(IdleState.READER_IDLE)) {   //一段时间内没有数据接收
                type = "read idle";
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {    //一段时间内没有数据发送
                type = "write idle";
            } else if (event.state().equals(IdleState.ALL_IDLE)) {  //一段时间内没有数据接收或者发送
                type = "all idle";
            }

            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(
                    ChannelFutureListener.CLOSE_ON_FAILURE);

            System.out.println( ctx.channel().remoteAddress()+"超时类型：" + type);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
