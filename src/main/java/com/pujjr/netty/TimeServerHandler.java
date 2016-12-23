package com.pujjr.netty;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author tom
 *
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
    	System.out.println("channelActive");
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
      //后台5秒后断开前段socket链接
       /* try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        //发送日期
        /*System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis() / 1000L + 2208988800L);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));*/
       //发送中文字符
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
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
