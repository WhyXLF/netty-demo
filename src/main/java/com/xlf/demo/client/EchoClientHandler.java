package com.xlf.demo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * Author: xiaoliufu
 * Date: 1/9/17
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        new Thread(new OutputHandler(ctx)).start();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] resultBytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(resultBytes);
        String content = new String(resultBytes, "UTF-8");
        System.out.println("echo : " + content);
    }

    private class OutputHandler implements Runnable {

        private ChannelHandlerContext ctx;

        public OutputHandler(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            System.out.println("Please input what you want:");
            Scanner sc = new Scanner(System.in);
            while (true) {
                String content;
                if (sc.hasNextLine() && (content = sc.next()) != null && content.length() > 0) {

                    byte[] req = content.getBytes();
                    ByteBuf byteBuf = Unpooled.buffer(req.length);
                    byteBuf.writeBytes(req);
                    ctx.writeAndFlush(byteBuf);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Please input what you want:");
                }
            }
        }
    }
}
