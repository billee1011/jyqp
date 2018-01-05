package com.zk.netty.redis.db;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class ShardJedisManager {
	
	private static ShardedJedisPool shardedPool = null;  
	
	static {  
	    init();
	}  
	
	private static void init(){
		List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
	    JedisShardInfo JedisShardInfo1 = new JedisShardInfo(JedisConfig.getString("host1"),JedisConfig.getInt("port1"));
	    JedisShardInfo JedisShardInfo2 = new JedisShardInfo(JedisConfig.getString("host2"),JedisConfig.getInt("port2"));
	    JedisShardInfo JedisShardInfo3 = new JedisShardInfo(JedisConfig.getString("host3"),JedisConfig.getInt("port3"));
	    list.add(JedisShardInfo1);
	    list.add(JedisShardInfo2);
	    list.add(JedisShardInfo3);
	    shardedPool = new ShardedJedisPool(new JedisPoolConfig(), list);
	}
	
	public static ShardedJedis getShardedJedis()throws Exception{
		return shardedPool.getResource();
	}
	
	public static void closeConnection(ShardedJedis shardedJedis)throws Exception{
		if(shardedJedis!=null){
			shardedJedis.close();
		}
	}
	
}
