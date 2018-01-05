package com.zk.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.DefaultChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Tuple;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zk.netty.enums.GameRoom;
import com.zk.netty.game.majiang.MajiangGame;
import com.zk.netty.game.shuangjian.ShuangjianGame;
import com.zk.netty.redis.RedisRoomService;
import com.zk.netty.redis.RedisUserService;
import com.zk.netty.user.service.UserService;
import com.zk.netty.user.service.impl.UserServiceImpl;
import com.zk.netty.util.DateTools;

/**
 * 消息处理器
 * @author syf
 */
@Service("nettyServerHandler")
@Scope("prototype")
@Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
	
	private Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	private UserService userService;
	
	public NettyServerHandler(){
		userService = new UserServiceImpl();
	}
	
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  
    	logger.info(ctx.channel().remoteAddress() +"建立连接");
        channels.add(ctx.channel());
    } 
    
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { 
    	logger.info(ctx.channel().remoteAddress() +"断开连接");
        channels.remove(ctx.channel());
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
    	Channel chnnel = ctx.channel();
    	DefaultChannelId channelId = (DefaultChannelId)chnnel.id();
    	logger.info("收到{},ID:{},消息:{}",chnnel.remoteAddress(),channelId,msg);
    	logger.info("---------------验证消息-------------------");
    	Map<String,Object> mapMsg = null;
    	try {
    		mapMsg = JSONObject.parseObject(msg.toString(), Map.class);
    		if(MapUtils.isNotEmpty(mapMsg)){
    			String uid = mapMsg.get("uid")==null?"":mapMsg.get("uid").toString();
        		String token = mapMsg.get("token")==null?"":mapMsg.get("token").toString();
        		if(StringUtils.isBlank(uid)||StringUtils.isBlank(token)){
        			ctx.writeAndFlush("{\"code\":\"1\",\"msg\":\"token invalid\"}\n");
        			return;
        		}
        		String value = RedisUserService.getToken(uid);
        		if(StringUtils.isBlank(value)||!token.equals(value)){
        			ctx.writeAndFlush("{\"code\":\"1\",\"msg\":\"token invalid\"}\n");
        			return;
        		}
        		RedisUserService.setUserChannelId(uid, channelId);
        		String msgType = mapMsg.get("msgtype")==null?"":mapMsg.get("msgtype").toString();
        		String returnMsg = "";
        		String data = mapMsg.get("data").toString();
    			GameRoom gameRoom = GameRoom.getInstance(mapMsg.get("game_room_type").toString());
    			String nowChannelId = channelId.asLongText();
        		switch (msgType) {
				case "40001"://加入或更换房间
	    			if(gameRoom!=null){
	    				//检查用户是否已经在另外一个房间。
	    				String userInRoom = RedisUserService.getUserInRoom(uid);
	    				if(StringUtils.isNotBlank(userInRoom)){
	    					RedisUserService.clearUserByRoom(uid, userInRoom);
	    					sendRoomNewUserInfo(channelId.asLongText(), gameRoom, userInRoom);
	    				}
	    				//加入房间
	    				String roomId = joinRoom(uid, gameRoom,userInRoom);
						if(StringUtils.isNotBlank(roomId)){
							ctx.writeAndFlush(sendRoomNewUserInfo(nowChannelId,gameRoom, roomId));
							return;
						}
	    			}
					returnMsg = "{\"code\":\"-1\",\"msg\":\"加入房间失败\"}\n";
					break;
				case "40002"://离开房间
					if(gameRoom!=null){
						//检查用户是否已经在另外一个房间。
	    				String userInRoom = RedisUserService.getUserInRoom(uid);
	    				if(StringUtils.isNotBlank(userInRoom)){
	    					RedisUserService.clearUserByRoom(uid, userInRoom);
	    					sendRoomNewUserInfo(nowChannelId, gameRoom, userInRoom);
	    				}
					}
					returnMsg = "{\"code\":\"0\",\"msg\":\"ok\"}\n";
					break;
				case "40003"://准备
					String roomId = mapMsg.get("roomid").toString();
					int readyStatus = Integer.parseInt(mapMsg.get("ready_status").toString());
					int resultNum = RedisRoomService.setRoomReady(roomId, uid, readyStatus);
					if(gameRoom.getNumberPeople()==resultNum){
						
						if(gameRoom.getGameType()==1){
							String[] cards = ShuangjianGame.dealCard();
							sendCards(nowChannelId, gameRoom, roomId, cards);
						}else if(gameRoom.getGameType()==2){
//							String[] cards = MajiangGame.dealCard();
//							sendCards(nowChannelId, gameRoom, roomId, cards);
						}
					}else{
						sendRoomReadyStatus(nowChannelId, gameRoom, roomId);
					}
					returnMsg = "{\"code\":\"0\",\"msg\":\"ok\"}\n";
					break;
				case "40004":
					break;
				case "40005":
					break;
				case "40006":
					break;
				default:
					
					break;
				}
        		ctx.writeAndFlush(returnMsg);
    		}
    	} catch (Exception e) {
			ctx.writeAndFlush("{\"code\":\"50010\",\"msg\":\"无法解析的JSON消息\"}\n");
		}
    }
    //{"code":"0","msg":"ok","uid":"46","token":"77ba3682e22f65b2080f4a6b4e006f26","msgtype":"40001","data":"sj1"}
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("{\"code\":\"0\",\"msg\":\"ok\"}\n");
        super.channelActive(ctx);
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		
	}

	/**
	 * 加入房间
	 * @param channelId
	 * @param uid
	 * @param mapMsg
	 * @return
	 */
	public String joinRoom(String uid,GameRoom gameRoom,String userInRoom){
		try {
			int uidInt = Integer.parseInt(uid);
    		Map<String, Object> mapUser = userService.getUserShortInfoById(uidInt);
    		if(MapUtils.isNotEmpty(mapUser)){
    			mapUser.put("uid", uid);
    			mapUser.put("joinroom_time", DateTools.getTime());
    			mapUser.remove("id");
				if(gameRoom.getNumberPeople()==4){
					return RedisRoomService.joinFourRoom(gameRoom,uidInt,mapUser,userInRoom);
				}else{
					return RedisRoomService.joinThreeRoom(gameRoom,uidInt,mapUser,userInRoom);
				}
    		}
    		logger.info("{}用户不存在",uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String sendRoomNewUserInfo(String nowChannelId,GameRoom gameRoom,String roomKey){
		Map<String,Object> mapRoom = new HashMap<String,Object>();
		mapRoom.put("roomid", roomKey);
		System.out.println("房间ID："+roomKey);
		Set<Tuple> room = RedisRoomService.getRoomInfo(roomKey);
		List<String> listUser = new ArrayList<String>();
		List<ChannelId> listChannel = new ArrayList<ChannelId>();
		if(!room.isEmpty()){
			for(Tuple t : room){
//				Map<String,Object> roomUser = new HashMap<String,Object>();
				String userInfo = t.getElement();
//				Map<String,Object> mapUserInfo = JSONObject.parseObject(userInfo,new TypeReference<Map<String, Object>>(){});
//				System.out.println(mapUserInfo);
//				roomUser.put(((int)t.getScore())+"", mapUserInfo);
				listUser.add(userInfo);
				ChannelId channelId = RedisUserService.getUserChannelId(((int)t.getScore())+"");
				listChannel.add(channelId);
			}
			mapRoom.put("users", listUser);
			mapRoom.put("code", "0");
			mapRoom.put("msg", "ok");
			String returnMsg = JSONObject.toJSONString(mapRoom)+"\n";
			for(ChannelId channelId : listChannel){
				if(!nowChannelId.equals(channelId.asLongText())){
					System.out.println("发送给其他用户:"+channelId);
					channels.find(channelId).writeAndFlush(returnMsg);
				}
			}
			return returnMsg;
		}
		return "";
	}
	
	/**
	 * 发送准备消息
	 * @param nowChannelId
	 * @param gameRoom
	 * @param roomKey
	 */
	private static void sendRoomReadyStatus(String nowChannelId,GameRoom gameRoom,String roomKey){
		Map<String,Object> mapRoom = new HashMap<String,Object>();
		mapRoom.put("roomid", roomKey);
		System.out.println("房间ID："+roomKey);
		Map<String,String> room = RedisRoomService.getRoomReady(roomKey);
		if(!room.isEmpty()){
			mapRoom.put("users", room);
			mapRoom.put("code", "0");
			mapRoom.put("msg", "ok");
			String returnMsg = JSONObject.toJSONString(mapRoom)+"\n";
			for(String str : room.keySet()){
				ChannelId channelId = RedisUserService.getUserChannelId(str);
				if(!nowChannelId.equals(channelId.asLongText())){
					System.out.println("发送准备状态消息给其他用户:"+channelId);
					channels.find(channelId).writeAndFlush(returnMsg);
				}
			}
		}
	}
	
	
	/**
	 * 发送准备消息
	 * @param nowChannelId
	 * @param gameRoom
	 * @param roomKey
	 */
	private static void sendCards(String nowChannelId,GameRoom gameRoom,String roomKey,String[] cards){
		Map<String,Object> mapRoom = new HashMap<String,Object>();
		mapRoom.put("roomid", roomKey);
		System.out.println("房间ID："+roomKey);
		Map<String,String> room = RedisRoomService.getRoomReady(roomKey);
		if(!room.isEmpty()){
			mapRoom.put("code", "0");
			mapRoom.put("msg", "ok");
			int index = 0;
			for(Map.Entry<String, String> entry : room.entrySet()){
				ChannelId channelId = RedisUserService.getUserChannelId(entry.getKey());
				mapRoom.put("cards", cards[index]);
				String returnMsg = JSONObject.toJSONString(mapRoom)+"\n";
				System.out.println("发送牌给指定用户:"+channelId);
				channels.find(channelId).writeAndFlush(returnMsg);
			}
		}
	}
}