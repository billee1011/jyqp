package com.zk.netty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * socket 客户端
 * @author syf
 */
public class SocketClient {

	public static void main(String[] args){
		SocketChannel socketChannel = null;
		try {
			// 打开监听信道并设置为非阻塞模式
			socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 1688));
			socketChannel.configureBlocking(false);
			// 打开并注册选择器到信道
			Selector selector = Selector.open();
			socketChannel.register(selector, SelectionKey.OP_READ);
			// 启动读取线程
			new SocketClientReadThread(selector);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			for (;;) {
	            String line = br.readLine();
	            if (line == null) {
	                continue;
	            }
	            if(line.equals("end")){
	            	break;
	            }
	            ByteBuffer bufferClient = ByteBuffer.wrap((line+"\n").getBytes("utf-8"));
				socketChannel.write(bufferClient);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(socketChannel!=null){
				try {
					socketChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}