package com.zk.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("framer", new LineBasedFrameDecoder(2048))
        .addLast("decoder", new StringDecoder())
        .addLast("encoder", new StringEncoder())
        .addLast("handler", new NettyClientHandler());
    }
}