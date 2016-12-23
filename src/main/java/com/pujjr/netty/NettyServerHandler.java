package com.pujjr.netty;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author tom
 *
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter { // (1)
	
	public static ChannelHandlerContext staticCtx = null;
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelRegistered 客户端接入"); 
	}
	
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive 客户端active"); 
	}
	
	public static void sendToClient(ChannelHandlerContext ctx1){
		final ChannelHandlerContext ctx = NettyServerHandler.staticCtx;
		System.out.println("ttt:"+ctx1);
		final ByteBuf time = ctx.alloc().buffer(4); // (2)
		String sendStr = "1234567重庆永川区大安";
        byte[] send = null;
        try {
			send = sendStr.getBytes("gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        time.writeBytes(send);
        while(time.readableBytes() < 100){
        	try {
    			Thread.currentThread().sleep(1000);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	 System.out.println("time.readableBytes():"+time.readableBytes());
        	 time.writeBytes(send);
        } 
        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
                System.out.println("服务端已主动断开链接");
            }
        }); // (4)
	}
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException { // (2)
    	System.out.println("channelRead");
    	NettyServerHandler.staticCtx = ctx;
    	//接受中文字符
    	ByteBuf buf = (ByteBuf)msg;
    	byte[] req = new byte[buf.readableBytes()];
    	buf.readBytes(req);
    	try {
			String body = new String(req,"gbk");
			System.out.println("NettyServerHandler 接受客户端报文:"+body);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	//开始处理接收后的业务逻辑处理
    	//......
    	//模拟中间业务与第三方交易
    	int i = 0;
    	while(i < 6){
    		Thread.currentThread().sleep(1000);
    		i++;
    	}
    	//中间业务逻辑处理完成，返回客户端
    	this.sendToClient(ctx);
//    	System.out.println("ctx:"+ctx);
//    	new Thread(new SendThread(ctx)).start();
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
