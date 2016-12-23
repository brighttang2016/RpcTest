package com.pujjr.netty;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author tom
 *
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
    	System.out.println("channelRead");
        // Discard the received data silently.
//        ((ByteBuf) msg).release(); // (3)
    	/*try {
            // Do something with msg
        } finally {
            ReferenceCountUtil.release(msg);
        }*/
    	
    	/*ByteBuf in = (ByteBuf) msg;
    	StringBuffer sb = new StringBuffer();
        try {
            while (in.isReadable()) { // (1)
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        	
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }*/
    	//接受中文字符
    	ByteBuf buf = (ByteBuf)msg;
    	byte[] req = new byte[buf.readableBytes()];
    	buf.readBytes(req);
    	try {
			String body = new String(req,"gbk");
			System.out.println("DiscardServerHandler body:"+body);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

    	/*ctx.write(msg);
    	ctx.flush();*/
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
