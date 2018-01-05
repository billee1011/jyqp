package com.zk.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务端启动
 * @author syf
 */
@Service("nettyServer")
public class NettyServer {
	
    private static final int portNumber = 1688;
    
    @Autowired
    private NettyServerInitializer nettyServerInitializer;

	@PostConstruct
    public void serverStart(){
    	 EventLoopGroup bossGroup = new NioEventLoopGroup();
         EventLoopGroup workerGroup = new NioEventLoopGroup();
         try {
             ServerBootstrap b = new ServerBootstrap();
             b.group(bossGroup, workerGroup);
             b.channel(NioServerSocketChannel.class);
             b.childHandler(nettyServerInitializer);
             //绑定端口   监听服务器关闭
             b.bind(portNumber).sync().channel().closeFuture().sync();;
         }catch(Exception e){
        	 e.printStackTrace();
         }finally {
             bossGroup.shutdownGracefully();
             workerGroup.shutdownGracefully();
         }
    }
    
}
