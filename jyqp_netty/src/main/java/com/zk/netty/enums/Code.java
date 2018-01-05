package com.zk.netty.enums;

/**
 * 响应消息
 * @author syf
 *
 */
public enum Code {
	
	success("0","ok"), 
	fail("-1","failed"),
	token_invalid("1","token invalid"),
	c_50009("50009","您暂无权限");
	
	private String code;
	private String msg;
	
	Code(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	
	public static Code getInstance(String code){
		for(Code codeMsg : Code.values()){
			if(codeMsg.getCode().equals(code)){
				return codeMsg;
			}
		}
		return null;
	}
}
