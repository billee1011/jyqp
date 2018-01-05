package com.zk.netty.room.shuangjian;

import com.zk.netty.redis.RedisCommonKey;
import com.zk.netty.redis.db.JedisManager;

import redis.clients.jedis.Jedis;

public class SJRoomRedis{
	 
	public void addPlayer(int roomid,String player){
		Jedis jedis = JedisManager.getJedis();
		jedis.hset(RedisCommonKey.SJROOM_FALG+roomid, player, "");
	}
}	
