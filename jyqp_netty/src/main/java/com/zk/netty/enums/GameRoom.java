package com.zk.netty.enums;

public enum GameRoom {
	sj1(1,"sj1",4,"sjroom_list_1","sjroom_1_","sjroom_list_1_size","sjroom_1_channelid_","sjroom_1_ready_"),
	sj2(1,"sj2",4,"sjroom_list_2","sjroom_2_","sjroom_list_2_size","sjroom_2_channelid_","sjroom_2_ready_"),
	sj3(1,"sj3",4,"sjroom_list_3","sjroom_3_","sjroom_list_3_size","sjroom_3_channelid_","sjroom_3_ready_"),
	sj4(1,"sj4",4,"sjroom_list_4","sjroom_4_","sjroom_list_4_size","sjroom_4_channelid_","sjroom_4_ready_"),
	sj5(1,"sj5",4,"sjroom_list_5","sjroom_5_","sjroom_list_5_size","sjroom_5_channelid_","sjroom_5_ready_"),
	sj6(1,"sj6",4,"sjroom_list_6","sjroom_6_","sjroom_list_6_size","sjroom_6_channelid_","sjroom_6_ready_"),
	
	mj1(2,"mj1",3,"mjroom_list_1","mjroom_1_","mjroom_list_1_size","mjroom_1_channelid_","mjroom_1_ready_"),
	mj2(2,"mj2",3,"mjroom_list_2","mjroom_2_","mjroom_list_2_size","mjroom_2_channelid_","mjroom_2_ready_"),
	mj3(2,"mj3",3,"mjroom_list_3","mjroom_3_","mjroom_list_3_size","mjroom_3_channelid_","mjroom_3_ready_"),
	mj4(2,"mj4",3,"mjroom_list_4","mjroom_4_","mjroom_list_4_size","mjroom_4_channelid_","mjroom_4_ready_"),
	mj5(2,"mj5",3,"mjroom_list_5","mjroom_5_","mjroom_list_5_size","mjroom_5_channelid_","mjroom_5_ready_"),
	mj6(2,"mj6",3,"mjroom_list_6","mjroom_6_","mjroom_list_6_size","mjroom_6_channelid_","mjroom_6_ready_"),
	
	ddz1(3,"ddz1",3,"ddzroom_list_1","ddzroom_1_","ddzroom_list_1_size","ddzroom_1_channelid_","ddzroom_1_ready_"),
	ddz2(3,"ddz2",3,"ddzroom_list_2","ddzroom_2_","ddzroom_list_2_size","ddzroom_2_channelid_","ddzroom_2_ready_"),
	ddz3(3,"ddz3",3,"ddzroom_list_3","ddzroom_3_","ddzroom_list_3_size","ddzroom_3_channelid_","ddzroom_3_ready_"),
	ddz4(3,"ddz4",3,"ddzroom_list_4","ddzroom_4_","ddzroom_list_4_size","ddzroom_4_channelid_","ddzroom_4_ready_"),
	ddz5(3,"ddz5",3,"ddzroom_list_5","ddzroom_5_","ddzroom_list_5_size","ddzroom_5_channelid_","ddzroom_5_ready_"),
	ddz6(3,"ddz6",3,"ddzroom_list_6","ddzroom_6_","ddzroom_list_6_size","ddzroom_6_channelid_","ddzroom_6_ready_"),
	
	tt1(4,"tt1",4,"ttroom_list_1","ttroom_1_","ttroom_list_1_size","ttroom_1_channelid_","ttroom_1_ready_"),
	tt2(4,"tt2",4,"ttroom_list_2","ttroom_2_","ttroom_list_2_size","ttroom_2_channelid_","ttroom_2_ready_"),
	tt3(4,"tt3",4,"ttroom_list_3","ttroom_3_","ttroom_list_3_size","ttroom_3_channelid_","ttroom_3_ready_"),
	tt4(4,"tt4",4,"ttroom_list_4","ttroom_4_","ttroom_list_4_size","ttroom_4_channelid_","ttroom_4_ready_"),
	tt5(4,"tt5",4,"ttroom_list_5","ttroom_5_","ttroom_list_5_size","ttroom_5_channelid_","ttroom_5_ready_"),
	tt6(4,"tt6",4,"ttroom_list_6","ttroom_6_","ttroom_list_6_size","ttroom_6_channelid_","ttroom_6_ready_"),
	
	nn1(5,"nn1",4,"nnroom_list_1","nnroom_1_","nnroom_list_1_size","nnroom_1_channelid_","nnroom_1_ready_"),
	nn2(5,"nn2",4,"nnroom_list_2","nnroom_2_","nnroom_list_2_size","nnroom_2_channelid_","nnroom_2_ready_"),
	nn3(5,"nn3",4,"nnroom_list_3","nnroom_3_","nnroom_list_3_size","nnroom_3_channelid_","nnroom_3_ready_"),
	nn4(5,"nn4",4,"nnroom_list_4","nnroom_4_","nnroom_list_4_size","nnroom_4_channelid_","nnroom_4_ready_"),
	nn5(5,"nn5",4,"nnroom_list_5","nnroom_5_","nnroom_list_5_size","nnroom_5_channelid_","nnroom_5_ready_"),
	nn6(5,"nn6",4,"nnroom_list_6","nnroom_6_","nnroom_list_6_size","nnroom_6_channelid_","nnroom_6_ready_");
	
	
	private int gameType;
	private String roomCode;
	private int numberPeople;
	private String roomListCacheCode;
	private String roomCacheCode;
	private String roomListSize;
	private String roomChannelId;
	private String roomReadyNum;
	
	public int getGameType() {
		return gameType;
	}

	public String getRoomCode() {
		return roomCode;
	}
	
	public int getNumberPeople() {
		return numberPeople;
	}


	public String getRoomListCacheCode() {
		return roomListCacheCode;
	}

	public String getRoomCacheCode() {
		return roomCacheCode;
	}
	
	public String getRoomListSize() {
		return roomListSize;
	}
	
	public String getRoomChannelId() {
		return roomChannelId;
	}
	
	public String getRoomReadyNum() {
		return roomReadyNum;
	}

	GameRoom(int gameType,String roomCode,int numberPeople,String roomListCacheCode,String roomCacheCode,String roomListSize,String roomChannelId,String roomReadyNum){
		this.gameType = gameType;
		this.roomCode = roomCode;
		this.numberPeople = numberPeople;
		this.roomListCacheCode = roomListCacheCode;
		this.roomCacheCode = roomCacheCode;
		this.roomListSize = roomListSize;
		this.roomChannelId = roomChannelId;
		this.roomReadyNum = roomReadyNum;
	}
	
	public static GameRoom getInstance(String roomCode){
		for(GameRoom gameRoom:GameRoom.values()){
			if(gameRoom.getRoomCode().equals(roomCode)){
				return gameRoom;
			}
		}
		return null;
	}
	
	public static GameRoom getInstanceBy(String roomCacheCode){
		for(GameRoom gameRoom:GameRoom.values()){
			if(gameRoom.getRoomCacheCode().equals(roomCacheCode)){
				return gameRoom;
			}
		}
		return null;
	}
}
