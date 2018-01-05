package com.zk.netty.redis.db;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisManager {
	
	private static JedisPool pool = null;  
	
	static {  
	    init();
	}  
	
	private static void init(){
	    pool = new JedisPool(JedisConfig.getString("host"),JedisConfig.getInt("port"));		
	}
	
	public static Jedis getJedis(){
		return pool.getResource();
	}
	
	public static void closeConn(Jedis jedis){
		if(jedis!=null){
			try {
				jedis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
