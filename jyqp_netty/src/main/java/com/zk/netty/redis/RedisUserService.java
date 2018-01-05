package com.zk.netty.redis;

import io.netty.channel.DefaultChannelId;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.zk.netty.enums.GameRoom;
import com.zk.netty.redis.db.JedisManager;

import redis.clients.jedis.Jedis;

public class RedisUserService {
	
	/**
	 * 获取token
	 * @param uid
	 * @return
	 */
	public static String getToken(String uid){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			return jedis.get(RedisCommonKey.TOKEN_KEY+uid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return "";
	}
	
	/**
	 * 获取用户信息
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getUserinfo(String uid){
		
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			String str = jedis.get(RedisCommonKey.USERINFO_KEY+uid);
			if(StringUtils.isNotBlank(str)){
				return JSONObject.parseObject(str, Map.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
		return null;
	}
	
	/**
	 * 设置用户channelId
	 * @param uid
	 * @param defaultChannelId
	 */
	public static void setUserChannelId(String uid,DefaultChannelId defaultChannelId){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			jedis.set((RedisCommonKey.USER_CHANNELID+uid).getBytes(), SerializeUtil.serialize(defaultChannelId));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
	}
	/**
	 * 获取用户channelId
	 * @param uid
	 * @param defaultChannelId
	 */
	public static DefaultChannelId getUserChannelId(String uid){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			byte[] bb = jedis.get((RedisCommonKey.USER_CHANNELID+uid).getBytes());
			return (DefaultChannelId)SerializeUtil.unserialize(bb);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
		return null;
	}
	/**
	 * 删除用户channelId
	 * @param uid
	 * @param defaultChannelId
	 */
	public static void delUserChannelId(String uid){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			jedis.del((RedisCommonKey.USER_CHANNELID+uid).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
	}
	
	/**
	 * 设置 用户所在房间
	 * @param uid
	 * @param roomId
	 */
	public static void setUserInRoom(String uid,String roomId){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			jedis.set(RedisCommonKey.USER_IN_ROOM+uid, roomId);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
	}
	
	/**
	 * 获取 用户所在房间
	 * @param uid
	 */
	public static String getUserInRoom(String uid){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			return jedis.get(RedisCommonKey.USER_IN_ROOM+uid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
		return null;
	}
	
	/**
	 * 删除 用户所在房间
	 * @param uid
	 */
	public static void delUserInRoom(String uid){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			jedis.del(RedisCommonKey.USER_IN_ROOM+uid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
	}
	
	public static void main(String[] args) {
		String roomId = "sjroom_1_1";
		System.out.println(roomId.substring(0, roomId.lastIndexOf("_")+1));
	}
	/**
	 * 将用户从房间清除
	 * @param uid
	 */
	public static void clearUserByRoom(String uid,String roomId){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			//房间人数减1
			jedis.zincrby(GameRoom.getInstanceBy(roomId.substring(0, roomId.lastIndexOf("_")+1)).getRoomListCacheCode(), -1, roomId);
			//移除用户在房间里的信息
			jedis.zremrangeByScore(roomId, uid, uid);
			//房间准备人数  是否减1
			int result = jedis.hdel(RedisCommonKey.ROOM_READY_HASH_FLAG+roomId, uid).intValue();
			if(result>0){//已准备
				jedis.incrBy(RedisCommonKey.ROOM_READY_FLAG+roomId,-1).intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
	}
	
}
