package com.zk.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.zk.netty.client.NettyClientInitializer;

/**
 * netty 客户端
 * @author syf
 */
public class ClientStart {
    
    public static String host = "127.0.0.1";
    public static int port = 1688; 

    public static void main(String[] args){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new NettyClientInitializer());
            // 连接服务端
            Channel ch = b.connect(host, port).sync().channel();
            // 控制台输入
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (;;) {
                String line = in.readLine();
                if (line == null) {
                    continue;
                }
                if(line.equals("end")){
	            	break;
	            }
                ch.writeAndFlush(line + "\n");
            }
            ch.closeFuture().sync();
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}