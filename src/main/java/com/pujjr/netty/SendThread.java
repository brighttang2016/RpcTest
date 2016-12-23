package com.pujjr.netty;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author tom
 *
 */
public class SendThread implements Runnable{
	public ChannelHandlerContext ctx;
	
	public SendThread(ChannelHandlerContext ctx){
		System.out.println("SendThread ctx:"+ctx);
		ctx = ctx;
	}
	
	public void run() {
		System.out.println("SendThread ctx"+ctx);
		int i = 0;
		while(i < 5){
			try {
				Thread.currentThread().sleep(1000);
				System.out.println("发送线程ctx"+ctx);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		
		NettyServerHandler.sendToClient(ctx);
	}
}
