package com.xlf.demo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by IntelliJ IDEA.
 * Author: xiaoliufu
 * Date: 1/9/17
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf= (ByteBuf) msg;
        byte[]result=new byte[buf.readableBytes()];
        buf.readBytes(result);
        String content=new String(result,"UTF-8");
        System.out.println("server receive : "+content);
        ByteBuf respBuf= Unpooled.copiedBuffer(content.getBytes());
        ctx.writeAndFlush(respBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
