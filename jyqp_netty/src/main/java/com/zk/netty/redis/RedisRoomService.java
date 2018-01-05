package com.zk.netty.redis;

import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.sortedset.ZAddParams;

import com.alibaba.fastjson.JSONObject;
import com.zk.netty.enums.GameRoom;
import com.zk.netty.redis.db.JedisManager;

/**
 * 房间列表  加入房间控制
 * @author syf
 */
public class RedisRoomService {
	
	public static void main(String[] args) {
		Jedis jedis = null;
		jedis = JedisManager.getJedis();
		Set<String> aa = jedis.keys("sjroom_*");
		for(String a : aa){
			System.out.println(a);
			jedis.del(a);
		}
		Set<String> aaa = jedis.keys("user_in_room_*");
		for(String a : aaa){
			System.out.println(a);
			jedis.del(a);
		}
		JedisManager.closeConn(jedis);
	}
	
	/**
	 * 获取房间信息
	 * @param roomKey
	 * @return
	 */
	public static Set<Tuple> getRoomInfo(String roomKey){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			return jedis.zrangeWithScores(roomKey, 0, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
		return null;		
	}
	
	/**
	 * 获取房间用户channelId
	 * @param roomKey
	 * @return
	 */
	public static Set<Tuple> getRoomChannelId(String roomChannelIdKey){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			return jedis.zrangeByScoreWithScores(roomChannelIdKey, "-inf", "+inf");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
		return null;
	}
	
	/**
	 * 四人房间列表 加入房间
	 * @param gameRoomType
	 * @param user
	 * @return
	 */
	public static String joinFourRoom(GameRoom gameRoom,int uid,Map<String,Object> user,String userInRoom){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			String roomListKey = gameRoom.getRoomListCacheCode();
			String returnRoomKey = "";
			Set<String> threePeopleRoomKeys = jedis.zrangeByScore(roomListKey, 3, 3);
			if(threePeopleRoomKeys.isEmpty()){
				Set<String> twoPeopleRoomKeys = jedis.zrangeByScore(roomListKey, 2, 2);
				if(twoPeopleRoomKeys.isEmpty()){
					Set<String> onePeopleRoomKeys = jedis.zrangeByScore(roomListKey, 1, 1);
					if(onePeopleRoomKeys.isEmpty()){
						//游戏房间数+1
						long size = jedis.incr(gameRoom.getRoomListSize());
						//生成新游戏房间
						String roomKey = gameRoom.getRoomCacheCode()+size;
						//新房间 加入房间列表
						jedis.zadd(roomListKey, 1, roomKey);
						//将用户加入游戏房间
						jedis.zadd(roomKey, uid,JSONObject.toJSONString(user));
						//标记用户在当前房间 座位号
						RedisUserService.setUserInRoom(uid+"", roomKey);
						return roomKey;
					}else{
						for(String roomKey : onePeopleRoomKeys){
							if(roomKey.equals(userInRoom)){
								continue;
							}
							double result = jedis.zincrby(roomListKey, 1, roomKey);
							if(result<=4){
								jedis.zadd(roomKey, uid, JSONObject.toJSONString(user));
								RedisUserService.setUserInRoom(uid+"", roomKey);
								returnRoomKey = roomKey;
								break;
							}else{
								jedis.zadd(roomListKey, 4, roomKey,ZAddParams.zAddParams().xx());
								continue;
							}
						}
					}
				}else{
					for(String roomKey : twoPeopleRoomKeys){
						if(roomKey.equals(userInRoom)){
							continue;
						}
						double result = jedis.zincrby(roomListKey, 1, roomKey);
						if(result<=4){
							jedis.zadd(roomKey, uid, JSONObject.toJSONString(user));
							RedisUserService.setUserInRoom(uid+"", roomKey);
							returnRoomKey = roomKey;
							break;
						}else{
							jedis.zadd(roomListKey, 4, roomKey,ZAddParams.zAddParams().xx());
							continue;
						}
					}
				}
			}else{
				for(String roomKey : threePeopleRoomKeys){
					if(roomKey.equals(userInRoom)){
						continue;
					}
					double result = jedis.zincrby(roomListKey, 1, roomKey);
					if(result==4){
						jedis.zadd(roomKey, uid, JSONObject.toJSONString(user));
						RedisUserService.setUserInRoom(uid+"", roomKey);
						returnRoomKey = roomKey;
						break;
					}else if(result>4){
						jedis.zadd(roomListKey, 4, roomKey,ZAddParams.zAddParams().xx());
						continue;
					}
				}
			}
			if(returnRoomKey.equals("")){
				//游戏房间数+1
				long size = jedis.incr(gameRoom.getRoomListSize());
				//生成新游戏房间
				String roomKey = gameRoom.getRoomCacheCode()+size;
				//新房间 加入房间列表
				jedis.zadd(roomListKey, 1, roomKey);
				//将用户加入游戏房间
				jedis.zadd(roomKey, uid,JSONObject.toJSONString(user));
				RedisUserService.setUserInRoom(uid+"", roomKey);
				return roomKey;
			}
			return returnRoomKey;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
		return null;
	}
	
	public static String getRoomNum(String roomKey){
		return roomKey.substring(roomKey.lastIndexOf("_")+1, roomKey.length());
	}
	
	/**
	 * 三人房间列表 加入房间
	 * @param gameRoomType
	 * @param user
	 * @return
	 */
	public static String joinThreeRoom(GameRoom gameRoom,int uid,Map<String,Object> user,String userInRoom){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			String roomListKey = gameRoom.getRoomListCacheCode();
			String returnRoomKey = "";
			Set<String> twoPeopleRoomKeys = jedis.zrangeByScore(roomListKey, 2, 2);
			if(twoPeopleRoomKeys.isEmpty()){
				Set<String> onePeopleRoomKeys = jedis.zrangeByScore(roomListKey, 1, 1);
				if(onePeopleRoomKeys.isEmpty()){
					//游戏房间数+1
					long size = jedis.incr(gameRoom.getRoomListSize());
					//生成新游戏房间
					String roomKey = gameRoom.getRoomCacheCode()+size;
					//新房间 加入房间列表
					jedis.zadd(roomListKey, 1, roomKey);
					//将用户加入游戏房间
					jedis.zadd(roomKey, uid,JSONObject.toJSONString(user));
					RedisUserService.setUserInRoom(uid+"", roomKey);
					return roomKey;
				}else{
					for(String roomKey : onePeopleRoomKeys){
						if(roomKey.equals(userInRoom)){
							continue;
						}
						double result = jedis.zincrby(roomListKey, 1, roomKey);
						if(result<=3){
							jedis.zadd(roomKey, uid, JSONObject.toJSONString(user));
							RedisUserService.setUserInRoom(uid+"", roomKey);
							returnRoomKey = roomKey;
							break;
						}else{
							jedis.zadd(roomListKey, 3, roomKey,ZAddParams.zAddParams().xx());
							continue;
						}
					}
				}
			}else{
				for(String roomKey : twoPeopleRoomKeys){
					if(roomKey.equals(userInRoom)){
						continue;
					}
					double result = jedis.zincrby(roomListKey, 1, roomKey);
					if(result==3){
						jedis.zadd(roomKey, uid, JSONObject.toJSONString(user));
						RedisUserService.setUserInRoom(uid+"", roomKey);
						returnRoomKey = roomKey;
						break;
					}else{
						jedis.zadd(roomListKey, 3, roomKey,ZAddParams.zAddParams().xx());
						continue;
					}
				}
			}
			if(returnRoomKey.equals("")){
				//游戏房间数+1
				long size = jedis.incr(gameRoom.getRoomListSize());
				//生成新游戏房间
				String roomKey = gameRoom.getRoomCacheCode()+size;
				//新房间 加入房间列表
				jedis.zadd(roomListKey, 1, roomKey);
				//将用户加入游戏房间
				jedis.zadd(roomKey, uid,JSONObject.toJSONString(user));
				RedisUserService.setUserInRoom(uid+"", roomKey);
				return roomKey;
			}
			return returnRoomKey;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
		return null;
	}
	
	/**
	 * 房间准备数
	 * @param roomId
	 * @return
	 */
	public static int setRoomReady(String roomId,String uid,int num){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			if(num==0){
				int result = jedis.hdel(RedisCommonKey.ROOM_READY_HASH_FLAG+roomId, uid).intValue();
				if(result>0){//已准备
					return jedis.incrBy(RedisCommonKey.ROOM_READY_FLAG+roomId,-1).intValue();
				}
			}else{
				int result = jedis.hset(RedisCommonKey.ROOM_READY_HASH_FLAG+roomId, uid,"1").intValue();
				if(result>0){
					return jedis.incrBy(RedisCommonKey.ROOM_READY_FLAG+roomId,1).intValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
		return -1;
	}
	
	/**
	 * 删除 房间准备数
	 * @param roomId
	 * @return
	 */
	public static void  delRoomReady(String roomId){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			jedis.del(RedisCommonKey.ROOM_READY_FLAG+roomId);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
	}
	/**
	 * 获取房间已准备 用户
	 * @param roomId
	 * @return
	 */
	public static Map<String,String> getRoomReady(String roomId){
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedis();
			return jedis.hgetAll(RedisCommonKey.ROOM_READY_HASH_FLAG+roomId);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisManager.closeConn(jedis);
		}
		return null;
	}
	
}
