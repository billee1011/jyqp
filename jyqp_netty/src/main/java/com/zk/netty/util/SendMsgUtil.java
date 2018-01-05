package com.zk.netty.util;

import java.util.Random;


public class SendMsgUtil {

	
	public static int getRandomCode(){

		return (int) Math.abs(new Random().nextInt(899999) + 100000);
	}

}
