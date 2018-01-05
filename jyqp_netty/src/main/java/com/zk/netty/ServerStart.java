package com.zk.netty;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring 注解方式 启动
 * @author syf
 */
public class ServerStart {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("spring-mvc.xml");
	}

}
