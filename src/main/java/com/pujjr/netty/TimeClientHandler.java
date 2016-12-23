package com.pujjr.netty;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author tom
 *
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {
	private ByteBuf buf;
	@Override
    public void handlerAdded(ChannelHandlerContext ctx) {
		System.out.println("handlerAdded，建立链接");
        buf = ctx.alloc().buffer(4); // (1)
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
    	System.out.println("handlerRemoved，关闭链接");
        buf.release(); // (1)
        buf = null;
    }
	
    /*@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	System.out.println("channelRead begin");
        ByteBuf m = (ByteBuf) msg; // (1)
        try {
            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        } finally {
            m.release();
        }
        System.out.println("channelRead end");
    }*/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg;
        buf.writeBytes(m); // (2)
        m.release();
        byte [] head = new byte[5];
        buf.readBytes(head, 0, head.length);
        try {
			System.out.println("报文头："+new String(head,"gbk"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        if (buf.readableBytes() >= 80) { // (3)
            /*long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));*/
        	//输出字符串
        	byte[] dst = new byte[buf.readableBytes()];
        	buf.readBytes(dst);
        	try {
				System.out.println(new String(dst, "gbk"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
