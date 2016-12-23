package com.pujjr.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author tom
 *
 */
public class ServerHandler extends ChannelHandlerAdapter{
	public void channelRead(ChannelHandlerContext ctx,Object msg){
		System.out.println("channelRead");
//		ctx.write(msg);
//		ctx.flush();
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
		System.out.println("exceptionCaught");
		cause.printStackTrace();
		ctx.close();
	}

}
