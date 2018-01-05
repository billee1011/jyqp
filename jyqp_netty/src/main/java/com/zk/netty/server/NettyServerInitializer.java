package com.zk.netty.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 服务端通道 初始化  解码器、处理器
 * @author syf
 */
@Service("nettyServerInitializer")
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

	@Autowired
	private NettyServerHandler helloServerHandler;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast("framer", new LineBasedFrameDecoder(2048))
				.addLast("decoder", new StringDecoder())
				.addLast("encoder", new StringEncoder())
				.addLast("handler", new NettyServerHandler());
	}
}
