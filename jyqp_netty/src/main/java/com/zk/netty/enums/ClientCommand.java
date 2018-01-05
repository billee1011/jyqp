package com.zk.netty.enums;

/**
 * 客户端指令
 * @author syf
 */
public enum ClientCommand {
	
	join_room("40001","加入房间"),
	leave("40002","离开房间"),
	be_ready("40003","玩家准备"),
	play("40004","出牌"),
	trusteeship("40005","托管"),
	un_trusteeship("40006","取消托管"),
	normal_msg("40100","普通消息");
	
	private String msgType;
	private String msg;
	
	ClientCommand(String msgType,String msg){
		this.msgType = msgType;
		this.msg = msg;
	}
	public String getMsgType() {
		return msgType;
	}
	public String getMsg() {
		return msg;
	}
	
	public static ClientCommand getInstance(String msgType){
		for(ClientCommand clientCommand : ClientCommand.values()){
			if(clientCommand.getMsgType().equals(msgType)){
				return clientCommand;
			}
		}
		return null;
	}
}
